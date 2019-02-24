package world.gregs.hestia.social.network.social.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.PRIVATE_QUICK_CHAT_FROM
import world.gregs.hestia.social.network.social.encoders.ChatPrivateFromEncoder.Companion.writeRand
import world.gregs.hestia.social.network.social.encoders.ChatPrivateFromEncoder.Companion.writeUsername
import world.gregs.hestia.social.network.social.encoders.messages.ChatPrivateQuickChatFrom

class ChatPrivateQuickChatFromEncoder : MessageEncoder<ChatPrivateQuickChatFrom>() {

    override fun encode(builder: PacketBuilder, message: ChatPrivateQuickChatFrom) {
        val (name: String, rights: Int, file: Int, data: ByteArray?) = message
        builder.apply {
            writeOpcode(PRIVATE_QUICK_CHAT_FROM, Packet.Type.VAR_BYTE)
            writeUsername(this, name)
            writeRand(this)
            writeByte(rights)
            writeShort(file)
            if(data != null) {
                writeBytes(data)
            }
        }
    }
}