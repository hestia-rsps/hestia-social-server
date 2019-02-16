package world.gregs.hestia.social.network.social.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.PUBLIC_CHAT
import world.gregs.hestia.social.network.social.encoders.messages.ChatPublicQuickChat

class ChatPublicQuickChatEncoder : MessageEncoder<ChatPublicQuickChat>() {

    override fun encode(builder: PacketBuilder, message: ChatPublicQuickChat) {
        val (client, effects, rights, file, data) = message
        builder.apply {
            writeOpcode(PUBLIC_CHAT, Packet.Type.VAR_BYTE)
            writeShort(client)
            writeShort(effects)
            writeByte(rights)
            writeShort(file)
            if(data != null) {
                writeBytes(data)
            }
        }
    }

}