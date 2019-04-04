package world.gregs.hestia.social.core.social.channel

import com.nhaarman.mockitokotlin2.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import world.gregs.hestia.core.services.int
import world.gregs.hestia.social.api.FriendsChat
import world.gregs.hestia.social.api.Name
import world.gregs.hestia.social.api.Player
import world.gregs.hestia.social.api.Relations
import world.gregs.hestia.social.core.social.FriendsChatChannel
import world.gregs.hestia.social.network.social.encoders.messages.FriendsChatListAppend
import world.gregs.hestia.social.network.social.encoders.messages.FriendsChatMessage
import world.gregs.hestia.social.network.social.encoders.messages.FriendsChatQuickChat
import world.gregs.hestia.social.network.social.encoders.messages.FriendsChatUpdate

abstract class FriendsChatHelper {

    internal lateinit var player: Player
    internal lateinit var friend: Player
    internal lateinit var target: Player
    internal lateinit var channel: FriendsChat
    private lateinit var relations: Relations

    @BeforeEach
    fun setup() {
        player = mock()
        friend = mock()
        target = mock()
        relations = mock()
        username("Player", player)
        username("Friend", friend)
        username("Target", target)
        this.channel = FriendsChatChannel(mock(), relations)
        whenever(player.relations).thenReturn(relations)
        whenever(player.channel).thenReturn(null)
    }

    /* Actions */

    internal fun message(player: Player = this.player) {
        channel.message(player, "Message")
    }

    internal fun quickMessage(player: Player = this.player) {
        channel.quickMessage(player, 147, null)
    }

    internal fun kick(target: Player = this.target, player: Player = this.player) {
        channel.kick(player, target.names, false)
    }

    internal fun ban(name: Name = target.names, player: Player = this.player) {
        channel.kick(player, name, true)
    }

    internal fun ban(duration: Long, player: Player = this.player) {
        (channel as FriendsChatChannel).bans[player.names] = System.nanoTime() + duration
    }

    internal fun join(player: Player = this.player) {
        channel.join(player)
    }

    internal fun leave(ban: Boolean = false, player: Player = this.player) {
        channel.leave(player, ban)
    }

    internal fun rename(name: String = "Rename", player: Player = this.player) {
        channel.rename(player, name)
    }

    internal fun update() {
        channel.update()
    }

    internal fun disable(player: Player = this.player) {
        channel.disable(player)
    }

    /* Assertions */

    internal fun assertJoined(joined: Boolean, player: Player = this.player) {
        verify(player, times(joined.int)).channel = channel
    }

    internal fun assertMessage(times: Int, player: Player = this.player) {
        verify(player, times(times)).send(any<FriendsChatMessage>())
    }

    internal fun assertQuickMessage(times: Int, player: Player = this.player) {
        verify(player, times(times)).send(any<FriendsChatQuickChat>())
    }

    internal fun assertDetails(times: Int, player: Player = this.player) {
        verify(player, times(times)).send(any<FriendsChatUpdate>())
    }

    internal fun assertDetailChange(times: Int, player: Player = this.player) {
        verify(player, times(times)).send(any<FriendsChatListAppend>())
    }

    internal fun assertBanned(banned: Boolean, player: Player = this.player) {
        assertThat(channel.banned(player.names)).isEqualTo(banned)
    }

    internal fun assertIsBanned(banned: Boolean, target: Player = this.target) {
        assertThat(channel.bans.containsKey(target.names)).isEqualTo(banned)
    }

    internal fun assertBannedTimer(time: Long, target: Player = this.target) {
        assertThat(channel.bans[target.names]).isGreaterThanOrEqualTo(time)
    }

    internal fun assertKicked(kicked: Boolean, target: Player = this.target) {
        verify(target, times(kicked.int)).channel = null
    }

    internal fun assertHasRank(has: Boolean, rank: FriendsChat.Ranks, inclusive: Boolean = false, player: Player = this.player) {
        assertThat(channel.hasRank(player, rank, inclusive)).isEqualTo(has)
    }

    internal fun assertFriend(friend: Boolean, player: Player = this.player) {
        verify(relations, times(friend.int)).addFriend(player.names)
    }

    internal fun assertRank(rank: FriendsChat.Ranks, player: Player = this.player) {
        assertThat(channel.getRank(player)).isEqualTo(rank)
    }

    internal fun assertOwner(owner: Boolean, player: Player = this.player) {
        assertThat(channel.isOwner(player)).isEqualTo(owner)
    }

    internal fun assertOwnerName(name: String = "Nobody") {
        assertThat(channel.owner?.name).isEqualTo(name)
    }

    internal fun assertMember(member: Boolean, player: Player = this.player) {
        assertThat(channel.isMember(player)).isEqualTo(member)
    }

    internal fun assertIsMember(member: Boolean, target: Player = this.target) {
        assertThat(channel.members.contains(target)).isEqualTo(member)
    }

    internal fun assertRefresh(refresh: Boolean, player: Player = this.player) {
        verify(player, times(refresh.int)).sendFriends(any())
    }

    internal fun assertRenamed(renamed: Boolean) {
        assertName(if(renamed) "Rename" else "Channel Name")
    }

    internal fun assertName(name: String = "Rename") {
        assertThat(channel.channelName).isEqualTo(name)
    }

    internal fun assertSetJoin(rank: FriendsChat.Ranks, expected: FriendsChat.Ranks?, player: Player = this.player) {
        assertThat(channel.setJoin(player, rank)).isEqualTo(expected)
    }

    internal fun assertSetTalk(rank: FriendsChat.Ranks, expected: FriendsChat.Ranks?, player: Player = this.player) {
        assertThat(channel.setTalk(player, rank)).isEqualTo(expected)
    }

    internal fun assertSetKick(rank: FriendsChat.Ranks, expected: FriendsChat.Ranks?, player: Player = this.player) {
        assertThat(channel.setKick(player, rank)).isEqualTo(expected)
    }

    internal fun assertSetLoot(rank: FriendsChat.Ranks, expected: FriendsChat.Ranks?, player: Player = this.player) {
        assertThat(channel.setLoot(player, rank)).isEqualTo(expected)
    }

    /* Settings */

    internal fun lootRank(rank: FriendsChat.Ranks) {
        (channel as FriendsChatChannel).lootRank = rank
    }

    internal fun joinRank(rank: FriendsChat.Ranks) {
        (channel as FriendsChatChannel).joinRank = rank
    }

    internal fun kickRank(rank: FriendsChat.Ranks) {
        (channel as FriendsChatChannel).kickRank = rank
    }

    internal fun talkRank(rank: FriendsChat.Ranks) {
        (channel as FriendsChatChannel).talkRank = rank
    }

    internal fun admin(admin: Boolean, player: Player = this.player) {
        whenever(player.admin).thenReturn(admin)
    }

    internal fun rank(rank: FriendsChat.Ranks, player: Player = this.player) {
        whenever(relations.isFriend(player.names)).thenReturn(true)
        whenever(relations.getFriend(player.names)).thenReturn(rank)
    }

    /* Misc */

    internal fun setupChannel(player: Boolean = false, friend: Boolean = false, target: Boolean = false) {
        channel.setup(this.player, "Channel Name")
        val owner = mock<Name>()
        whenever(owner.name).thenReturn("Nobody")
        (channel as FriendsChatChannel).owner = owner
        if(player) {
            addMember(this.player)
        }
        if(friend) {
            addMember(this.friend)
        }
        if(target) {
            addMember(this.target)
        }
        update()
    }

    internal fun username(name: String, player: Player = this.player) {
        whenever(player.names).thenReturn(mock())
        whenever(player.names.name).thenReturn(name)
        whenever(player.names.username).thenReturn(name)
    }

    internal fun friend(player: Player = this.player) {
        whenever(relations.isFriend(player.names)).thenReturn(true)
        whenever(relations.getFriend(player.names)).thenReturn(FriendsChat.Ranks.FRIEND)
    }

    internal fun ignore(player: Player = this.player) {
        whenever(relations.isIgnored(player.names)).thenReturn(true)
    }

    internal fun addMember(member: Player) {
        whenever(member.channel).thenReturn(channel)
        (channel as FriendsChatChannel).members.add(member)
    }
}