package world.gregs.hestia.social.network.social.encoders

import world.gregs.hestia.cache.compress.Huffman
import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.PRIVATE_CHAT_TO
import world.gregs.hestia.social.network.social.encoders.messages.ChatPrivateTo

class ChatPrivateToEncoder : MessageEncoder<ChatPrivateTo>() {

    override fun encode(builder: PacketBuilder, message: ChatPrivateTo) {
        val (name, text) = message
        builder.apply {
            writeOpcode(PRIVATE_CHAT_TO, Packet.Type.VAR_SHORT)
            writeString(name)
            Huffman.compress(text, this)
        }
    }

}