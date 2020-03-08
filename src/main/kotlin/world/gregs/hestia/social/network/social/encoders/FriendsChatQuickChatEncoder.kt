package world.gregs.hestia.social.network.social.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.FRIENDS_QUICK_CHAT_MESSAGE
import world.gregs.hestia.social.network.social.encoders.messages.FriendsChatQuickChat

class FriendsChatQuickChatEncoder : MessageEncoder<FriendsChatQuickChat>() {

    override fun encode(builder: PacketBuilder, message: FriendsChatQuickChat) {
        builder.apply {
            writeOpcode(FRIENDS_QUICK_CHAT_MESSAGE, Packet.Type.VAR_BYTE)
            writeBytes(message.data)
        }
    }

}