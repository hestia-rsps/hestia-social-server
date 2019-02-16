package world.gregs.hestia.social.network.world.encoders

import org.slf4j.LoggerFactory
import world.gregs.hestia.core.network.codec.Codec
import world.gregs.hestia.core.network.codec.message.Message
import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.WorldOpcodes.CLIENT_MESSAGE
import world.gregs.hestia.social.network.world.encoders.messages.ClientMessage

class ClientMessageEncoder(private val codec: Codec) : MessageEncoder<ClientMessage>() {

    private val logger = LoggerFactory.getLogger(ClientMessageEncoder::class.java)!!

    @Suppress("UNCHECKED_CAST")
    override fun encode(builder: PacketBuilder, message: ClientMessage) {
        val (entity, msg) = message
        builder.apply {
            writeOpcode(CLIENT_MESSAGE, Packet.Type.VAR_SHORT)
            writeInt(entity)
            val encoder = codec.get(msg::class) as? MessageEncoder<Message> ?: run {
                logger.warn("No encoder for message: $msg")
                return
            }
            encoder.write(msg, buffer)
        }

    }

}