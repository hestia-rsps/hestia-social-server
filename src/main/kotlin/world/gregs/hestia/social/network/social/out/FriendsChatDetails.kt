package world.gregs.hestia.social.network.social.out

import org.slf4j.LoggerFactory
import world.gregs.hestia.core.Settings
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.services.int
import world.gregs.hestia.core.services.toRSLong
import world.gregs.hestia.social.api.FriendsChat
import world.gregs.hestia.social.api.Player

class FriendsChatDetails(channel: FriendsChat? = null) : Packet.Builder(12, Packet.Type.VAR_SHORT) {
    init {
        if(channel != null) {
            if (channel.owner == null || channel.channelName == null) {
                logger.warn("Invalid channel $channel ${channel.owner?.name} ${channel.channelName}")
            } else {
                writeString(channel.owner?.name!!)
                writeByte(0)
                writeLong(channel.channelName!!.toRSLong())
                writeByte(channel.kickRank.value)//Who can kick on chat
                writeByte(channel.members.size)//Player count
                channel.members.forEach {
                    writeFriendDetails(it, channel.getRank(it))
                }
            }
        }
    }

    companion object {

        fun Packet.Builder.writeFriendDetails(friend: Player, rank: FriendsChat.Ranks) {
            writeString(friend.names.name)
            val different = friend.names.username != friend.names.name
            writeByte(different.int)
            if (different) {
                writeString(friend.names.username)
            }
            writeShort(if (friend.lobby) 1 else friend.worldId)//World
            writeByte(rank.value)//Rank
            if(rank != FriendsChat.Ranks.ANYONE) {
                writeString(if(friend.lobby) "Lobby" else "${Settings.getString("serverName", "World")} ${friend.worldId}")//Server status
            }
        }

        private val logger = LoggerFactory.getLogger(FriendsChatDetails::class.java)!!
    }
}