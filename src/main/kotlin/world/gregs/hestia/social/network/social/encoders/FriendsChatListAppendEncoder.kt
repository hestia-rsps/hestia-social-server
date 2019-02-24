package world.gregs.hestia.social.network.social.encoders

import world.gregs.hestia.core.Settings
import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.FRIEND_LIST_APPEND
import world.gregs.hestia.social.api.FriendsChat
import world.gregs.hestia.social.api.Player
import world.gregs.hestia.social.network.social.encoders.messages.FriendsChatListAppend

class FriendsChatListAppendEncoder : MessageEncoder<FriendsChatListAppend>() {

    override fun encode(builder: PacketBuilder, message: FriendsChatListAppend) {
        val (member, rank) = message
        builder.apply {
            writeOpcode(FRIEND_LIST_APPEND, Packet.Type.VAR_BYTE)
            writeFriendDetails(this, member, rank)
        }
    }

    companion object {
        fun writeFriendDetails(builder: PacketBuilder, friend: Player, rank: FriendsChat.Ranks) {
            builder.apply {
                writeString(friend.names.name)
                val different = friend.names.username != friend.names.name
                writeByte(different)
                if (different) {
                    writeString(friend.names.username)
                }
                writeShort(if (friend.lobby) 1 else friend.worldId)//World
                writeByte(rank.value)//Rank
                if (rank != FriendsChat.Ranks.ANYONE) {
                    writeString(if (friend.lobby) "Lobby" else "${Settings.getString("serverName", "World")} ${friend.worldId}")//Server status
                }
            }
        }
    }

}