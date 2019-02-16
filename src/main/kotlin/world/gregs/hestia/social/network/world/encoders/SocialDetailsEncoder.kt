package world.gregs.hestia.social.network.world.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.WorldOpcodes.SOCIAL_SERVER_DETAILS
import world.gregs.hestia.core.network.protocol.messages.SocialDetails
import world.gregs.hestia.social.network.social.SocialCodec
import java.nio.ByteBuffer

class SocialDetailsEncoder(codec: SocialCodec) : MessageEncoder<SocialDetails>() {

    /**
     * Calculate data to be reused as it doesn't change
     */
    private val data: ByteArray

    init {
        //Count handshakes
        val handshakes = codec.handshakes()
        var handshakeCount = 0
        handshakes.filterNotNull().forEach { handshakeCount += it.opcodes.size }

        //Count decoders
        var count = 0
        val decoders = codec.decoders()
        decoders.filterNotNull().forEach { count += it.opcodes.size }

        //4 bytes for the two shorts, two bytes for each decoder
        val buffer = ByteBuffer.allocate(4 + ((handshakeCount + count) * 2))

        //Write handshakes
        buffer.putShort(handshakeCount.toShort())
        handshakes.filterNotNull().forEach { decoder ->
            decoder.opcodes.forEach { opcode ->
                buffer.put(opcode.toByte())
                buffer.put(decoder.size.toByte())
            }
        }

        //Write regular decoders
        buffer.putShort(count.toShort())
        decoders.filterNotNull().forEach { decoder ->
            decoder.opcodes.forEach { opcode ->
                buffer.put(opcode.toByte())
                buffer.put(decoder.size.toByte())
            }
        }

        data = buffer.array()
    }

    override fun encode(builder: PacketBuilder, message: SocialDetails) {
        builder.apply {
            writeOpcode(SOCIAL_SERVER_DETAILS, Packet.Type.VAR_SHORT)
            writeByte(message.world)
            writeBytes(data)
        }
    }

}