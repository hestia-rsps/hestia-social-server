package world.gregs.hestia.social.network.social.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.PRIVATE_QUICK_CHAT_TO
import world.gregs.hestia.social.network.social.encoders.messages.ChatPrivateQuickChatTo

class ChatPrivateQuickChatToEncoder : MessageEncoder<ChatPrivateQuickChatTo>() {

    override fun encode(builder: PacketBuilder, message: ChatPrivateQuickChatTo) {
        val (name, file, data) = message
        builder.apply {
            writeOpcode(PRIVATE_QUICK_CHAT_TO, Packet.Type.VAR_BYTE)
            writeString(name)
            writeShort(file)
            if(data != null) {
                writeBytes(data)
            }
        }
    }

}