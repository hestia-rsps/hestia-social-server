package world.gregs.hestia.social.core.social

import org.slf4j.LoggerFactory
import world.gregs.hestia.core.cache.compress.Huffman
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.codec.packet.PacketWriter
import world.gregs.hestia.core.network.protocol.encoders.messages.FriendsChatDisconnect
import world.gregs.hestia.core.services.formatUsername
import world.gregs.hestia.core.services.toRSLong
import world.gregs.hestia.social.api.*
import world.gregs.hestia.social.core.Duration.HOUR
import world.gregs.hestia.social.core.World
import world.gregs.hestia.social.core.social.FriendsChatChannels.Companion.CHANNEL_SETTINGS_DELAY
import world.gregs.hestia.social.network.social.encoders.ChatPrivateFromEncoder
import world.gregs.hestia.social.network.social.encoders.messages.FriendsChatListAppend
import world.gregs.hestia.social.network.social.encoders.messages.FriendsChatMessage
import world.gregs.hestia.social.network.social.encoders.messages.FriendsChatQuickChat
import world.gregs.hestia.social.network.social.encoders.messages.FriendsChatUpdate
import java.util.*

class FriendsChatChannel(private val players: Players, override val relations: Relations) : FriendsChat {

    override var channelName: String? = null
    override var owner: Name? = null
    override var joinRank = FriendsChat.Ranks.ANYONE//Default FRIEND
    override var talkRank = FriendsChat.Ranks.ANYONE
    override var kickRank = FriendsChat.Ranks.OWNER
    override var lootRank = FriendsChat.Ranks.NO_ONE
    override var coinShare = false
    override val members = LinkedList<Player>()
    override val bans = HashMap<Name, Long>()
    private val queue = ArrayList<Name?>()

    override fun join(player: Player) {
        //Check not in an existing channel
        if (player.channel != null) {
            return
        }

        //Has rank to join
        if (!hasRank(player, joinRank)) {
            player.message("You do not have a high enough rank to join this friends chat channel.", 11)
            return
        }

        //Isn't ignored by the owner (unless admin)
        if (relations.isIgnored(player.names) && !player.admin) {
            player.message("You are not allowed to join this user's friends chat channel.", 11)
            return
        }

        //Isn't banned (unless admin)
        if (banned(player.names) && !player.admin) {
            player.message("You are temporarily banned from this friends chat channel.", 11)
            return
        }

        //And the channel isn't full
        if (members.size >= MAX_MEMBERS) {
            var space = false
            //If player trying to join is ranked
            if (hasRank(player, FriendsChat.Ranks.RECRUIT)) {
                //And there's a member with a lower rank
                val victim = members.sortedBy { getRank(it) }.first()
                if (hasRank(player, getRank(victim), false)) {
                    //Kick the unfortunate fellow
                    leave(victim, true)
                    space = true
                }
            }
            if (!space) {
                player.message("The channel you tried to join is currently full.", 11)
                return
            }
        }

        //Join
        player.channel = this
        player.friendsChat = owner
        members.add(player)
        //Send details
        display(player)
    }

    override fun display(player: Player) {
        player.send(FriendsChatUpdate(this))
        //Notify
        player.message("Now talking in friends chat channel $channelName", 11)
        player.message("To talk, start each line of chat with the / symbol.", 11)
        //Update all current members
        updateMember(player)
    }

    override fun message(player: Player, message: String) {
        //Check has rank to talk and is in the channel
        if (!hasRank(player, talkRank) || !isMember(player)) {
            player.message("You are not allowed to talk in this friends chat channel.", 11)
            return
        }

        //Build the reusable packet
        val builder = buildPacket(player)
        Huffman.compress(message, builder)
        val data = builder.buffer.array().copyOf(builder.buffer.readableBytes())

        //Send message to all members (who aren't ignoring this player)
        members.filterNot { it.ignores(player) }.forEach {
            it.send(FriendsChatMessage(data))
        }
    }

    override fun quickMessage(player: Player, fileId: Int, data: ByteArray?) {
        //Check has rank to talk and is in the channel
        if (!hasRank(player, talkRank) || !isMember(player)) {
            player.message("You are not allowed to talk in this friends chat channel.", 11)
            return
        }

        //Build the reusable packet
        val builder = buildPacket(player)
        builder.writeShort(fileId)
        if(data != null) {
            builder.writeBytes(data)
        }
        val array = builder.buffer.array().copyOf(builder.buffer.readableBytes())

        //Send message to all members (who aren't ignoring this player)
        members.filterNot { it.ignores(player) }.forEach {
            it.send(FriendsChatQuickChat(array))
        }
    }

    /**
     * Builds the message packet header
     * @param player The player sending the message
     * @return Half built packet builder
     */
    private fun buildPacket(player: Player): PacketBuilder {
        val builder = PacketWriter()
        ChatPrivateFromEncoder.writeUsername(builder, player.names.name)
        builder.writeLong((channelName ?: "").toRSLong())
        ChatPrivateFromEncoder.writeRand(builder)
        builder.writeByte(player.rights)
        return builder
    }

    override fun kick(player: Player, target: Name, ban: Boolean) {
        //Check player has the rank to kick
        if (!hasRank(player, kickRank) || !isMember(player)) {
            player.message("You are not allowed to kick in this friends chat channel.", 11)
            return
        }

        //Isn't trying to kick himself
        if (player.names == target) {
            player.message("You cannot kick or ban yourself.", 11)
            return
        }

        //If target is already banned refresh the ban time
        if (ban && banned(target)) {
            bans[target] = System.nanoTime() + BAN_TIME
            //Notify
            player.message("Your request to kick/ban this user was successful.", 11)
            return
        }

        //Check target is in the channel
        val member = members.firstOrNull { it.names == target }
        if (member == null) {
            player.message("Could not find member '${target.name}' in the channel.", 11)
            return
        }

        //Check players rank is higher than the target
        if (!hasRank(player, getRank(member), false) || member.admin) {
            player.message("You cannot kick this member.", 11)
            return
        }

        //Ban
        if (ban) {
            bans[target] = System.nanoTime() + BAN_TIME
        }

        leave(member, true)
        //Notify
        player.message("Your request to kick/ban this user was successful.", 11)
    }

    override fun banned(name: Name): Boolean {
        val ban = bans.getOrDefault(name, null) ?: return false
        val over = ban < System.nanoTime()
        if (over) {
            bans.remove(name)
        }
        return !over
    }

    override fun leave(player: Player, kick: Boolean) {
        if (isMember(player) || kick) {
            //Leave
            player.channel = null
            player.friendsChat = null
            members.remove(player)
            //If player was in someone else's channel then their friends ranks will need updating
            if(!isOwner(player) || player.admin) {
                player.sendFriends(players)
            }
            player.send(FriendsChatDisconnect())//Exit
            //Notify
            player.message("You have ${if(kick) "been kicked from" else "left" } the channel.", 11)
            //Refresh
            updateMember(player, FriendsChat.Ranks.ANYONE)
        }
    }

    override fun isMember(player: Player): Boolean {
        return members.contains(player)
    }

    override fun isOwner(player: Player): Boolean {
        return hasRank(player, FriendsChat.Ranks.OWNER)
    }

    override fun hasRank(player: Player, rank: FriendsChat.Ranks, inclusive: Boolean): Boolean {
        //Check channel is active
        if (channelName == null) {
            return false
        }
        //Check has rank (or is admin)
        val playerRank = getRank(player).value
        return inclusive && playerRank >= rank.value || playerRank > rank.value
    }

    override fun getRank(player: Player): FriendsChat.Ranks {
        return when {
            player.admin -> FriendsChat.Ranks.ADMIN
            else -> getRank(player.names)
        }
    }

    override fun getRank(name: Name): FriendsChat.Ranks {
        return when {
            name == owner -> FriendsChat.Ranks.OWNER
            relations.isFriend(name) -> relations.getFriend(name)!!
            members.any { it.names == name } -> FriendsChat.Ranks.NO_ONE
            else -> FriendsChat.Ranks.ANYONE
        }
    }

    override fun setup(owner: Player, name: String) {
        this.owner = owner.names
        channelName = name
    }

    override fun disable(player: Player) {
        bans.clear()
        channelName = null
        //Kick all members
        members.reversed().forEach {
            leave(it, true)
        }
        //Notify
        player.message("Your friends chat channel has now been disabled!", 11)
    }

    override fun clean() {
        val iterator = bans.iterator()
        var next: MutableMap.MutableEntry<Name, Long>
        while (iterator.hasNext()) {
            next = iterator.next()
            if (next.value < System.nanoTime()) {
                iterator.remove()
            }
        }
    }

    override fun updateMember(member: Player, rank: FriendsChat.Ranks) {
        members.forEach {
            it.send(FriendsChatListAppend(member, rank))
        }
    }

    override fun demote(player: Player, member: Name) {
        if (!isOwner(player)) {
            player.message("Only the friends chat owner can do this.", 11)
            return
        }

        if(relations.isFriend(member)) {
            relations.addFriend(member, FriendsChat.Ranks.ANYONE)
        }

        if (queue.isEmpty()) {
            player.message("Changes will take effect on your friends chat in the next $CHANNEL_SETTINGS_DELAY seconds.", 11)
        }
        queue.add(member)
    }

    override fun promote(player: Player, friend: Name, rank: Int) {
        if (!isOwner(player)) {
            player.message("Only the friends chat owner can do this.", 11)
            return
        }

        if (!relations.isFriend(friend)) {
            player.message("Could not find friend '$friend' in your friends list.", 11)
            return
        }

        val ranks = FriendsChat.Ranks.values().firstOrNull { it.value == rank }
        if (ranks == null) {
            logger.warn("Couldn't find friends chat rank $rank for ${player.names} $friend")
        } else {
            //Update rank (already a friend so shouldn't re-add)
            relations.addFriend(friend, ranks)
            //Send update to setup interface
            player.sendFriend(friend, World.players.get(friend))

            if (queue.isEmpty()) {
                player.message("Changes will take effect on your friends chat in the next $CHANNEL_SETTINGS_DELAY seconds.", 11)
            }
            queue.add(friend)
        }
    }

    override fun rename(player: Player, name: String): String? {
        if (!isOwner(player)) {
            player.message("Only the friends chat owner can do this.", 11)
            return null
        }

        //Admins can do what they like
        if (!player.admin) {
            //Limit name length
            if (name.length > 12) {
                player.message("Name too long. A channel name cannot be longer than 12 characters.", 11)
                return null
            }

            //Filter banned words
            if (name.contains("mod", true) || name.contains("staff", true) || name.contains("admin", true)) {
                player.message("Name contains a banned word. Please try another name.", 11)
                return null
            }
        }

        channelName = name.formatUsername()
        //Queue a refresh for all members
        if (queue.isEmpty()) {
            player.message("Changes will take effect on your friends chat in the next $CHANNEL_SETTINGS_DELAY seconds.", 11)
        }
        queue.add(null)

        return channelName
    }

    override fun setJoin(player: Player, rank: FriendsChat.Ranks): FriendsChat.Ranks? {
        if (!isOwner(player)) {
            player.message("Only the friends chat owner can do this.", 11)
            return null
        }

        if (rank == FriendsChat.Ranks.NO_ONE) {
            return null
        }

        joinRank = rank
        return rank
    }

    override fun setTalk(player: Player, rank: FriendsChat.Ranks): FriendsChat.Ranks? {
        if (!isOwner(player)) {
            player.message("Only the friends chat owner can do this.", 11)
            return null
        }

        if (rank == FriendsChat.Ranks.NO_ONE) {
            return null
        }

        talkRank = rank
        return rank
    }

    override fun setKick(player: Player, rank: FriendsChat.Ranks): FriendsChat.Ranks? {
        if (!isOwner(player)) {
            player.message("Only the friends chat owner can do this.", 11)
            return null
        }

        if (rank.value <= FriendsChat.Ranks.RECRUIT.value) {
            return null
        }

        kickRank = rank
        return rank
    }

    override fun setLoot(player: Player, rank: FriendsChat.Ranks): FriendsChat.Ranks? {
        if (!isOwner(player)) {
            player.message("Only the friends chat owner can do this.", 11)
            return null
        }

        if (rank == FriendsChat.Ranks.ANYONE || rank == FriendsChat.Ranks.OWNER) {
            return null
        }

        lootRank = rank
        return rank
    }

    override fun setShare(player: Player, value: Boolean): Boolean {
        if (!isOwner(player)) {
            player.message("Only the friends chat owner can do this.", 11)
            return coinShare
        }

        coinShare = value
        return value
    }

    override fun update() {
        if (queue.any { it == null }) {
            //Update all details
            members.forEach {
                it.send(FriendsChatUpdate(this))
            }
        } else {
            //Update single changes
            members.filter { queue.contains(it.names) }.forEach {
                updateMember(it)
            }
        }
        queue.clear()
    }

    companion object {
        private const val MAX_MEMBERS = 100
        private const val BAN_TIME: Long = HOUR
        private val logger = LoggerFactory.getLogger(FriendsChatChannel::class.java)!!
    }
}