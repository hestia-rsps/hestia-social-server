package world.gregs.hestia.social.network.social.encoders

import world.gregs.hestia.cache.compress.Huffman
import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.PRIVATE_CHAT_FROM
import world.gregs.hestia.core.services.formatUsername
import world.gregs.hestia.social.network.social.encoders.messages.ChatPrivateFrom
import java.util.*

class ChatPrivateFromEncoder : MessageEncoder<ChatPrivateFrom>() {

    override fun encode(builder: PacketBuilder, message: ChatPrivateFrom) {
        val (name: String, rights: Int, text) = message
        builder.apply {
            writeOpcode(PRIVATE_CHAT_FROM, Packet.Type.VAR_SHORT)
            writeUsername(this, name)
            writeRand(this)
            writeByte(rights)
            Huffman.compress(text, this)
        }
    }

    companion object {
        fun writeUsername(builder: PacketBuilder, username: String) {
            val formatted = username.formatUsername()
            val different = username != formatted

            builder.writeByte(different)
            builder.writeString(username)
            if (different) {
                builder.writeString(formatted)
            }
        }

        fun writeRand(builder: PacketBuilder) {
            //A very small chance (1 in a few billion) of duplicate and a message not being sent correctly
            //If the value has been sent before then subsequent messages with the same value is ignored
            //Not entirely sure of it's use
            builder.writeShort(random.nextInt())
            builder.writeMedium(random.nextInt())
        }

        private val random = Random()

    }
}