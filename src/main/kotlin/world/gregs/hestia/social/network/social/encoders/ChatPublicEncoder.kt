package world.gregs.hestia.social.network.social.encoders

import world.gregs.hestia.core.cache.compress.Huffman
import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.PUBLIC_CHAT
import world.gregs.hestia.social.network.social.encoders.messages.ChatPublic

class ChatPublicEncoder : MessageEncoder<ChatPublic>() {

    override fun encode(builder: PacketBuilder, message: ChatPublic) {
        val (client, effects, rights, text) = message
        builder.apply {
            writeOpcode(PUBLIC_CHAT, Packet.Type.VAR_BYTE)
            writeShort(client)
            writeShort(effects)
            writeByte(rights)
            Huffman.compress(text, this)
        }
    }

}