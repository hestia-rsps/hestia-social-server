package world.gregs.hestia.social.network.social.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.FRIENDS_CHAT_MESSAGE
import world.gregs.hestia.social.network.social.encoders.messages.FriendsChatMessage

class FriendsChatMessageEncoder : MessageEncoder<FriendsChatMessage>() {

    override fun encode(builder: PacketBuilder, message: FriendsChatMessage) {
        val (data) = message
        builder.apply {
            writeOpcode(FRIENDS_CHAT_MESSAGE, Packet.Type.VAR_BYTE)
            writeBytes(data)
        }
    }

}