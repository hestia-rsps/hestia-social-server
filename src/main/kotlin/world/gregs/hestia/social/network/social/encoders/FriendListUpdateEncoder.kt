package world.gregs.hestia.social.network.social.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.FRIEND_LIST
import world.gregs.hestia.social.network.social.encoders.messages.FriendListUpdate

class FriendListUpdateEncoder : MessageEncoder<FriendListUpdate>() {

    override fun encode(builder: PacketBuilder, message: FriendListUpdate) {
        val (name, renamed, lobby, world, friendsChatRank, referred) = message
        builder.apply {
            writeOpcode(FRIEND_LIST, Packet.Type.VAR_SHORT)
            writeByte(renamed)
            writeString(name.name)
            writeString(name.previousName)
            writeShort(world)
            writeByte(friendsChatRank)
            writeByte(referred)
            if(world > 0) {
                writeString(if(lobby) "Lobby" else "World $world")
                writeByte(0)
            }
        }
    }

}