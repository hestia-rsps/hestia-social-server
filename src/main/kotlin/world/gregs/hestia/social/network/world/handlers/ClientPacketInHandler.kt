package world.gregs.hestia.social.network.world.handlers

import io.netty.channel.ChannelHandlerContext
import org.slf4j.LoggerFactory
import world.gregs.hestia.core.network.codec.Codec
import world.gregs.hestia.core.network.codec.HandshakeDispatcher
import world.gregs.hestia.core.network.codec.message.Message
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.message.MessageHandler
import world.gregs.hestia.core.network.getSession
import world.gregs.hestia.core.network.protocol.messages.ClientPacket
import world.gregs.hestia.social.core.World
import world.gregs.hestia.social.network.PlayerMessageHandler

/**
 * Decodes raw packet from client into a message then dispatches the message to the correct social handler
 */
class ClientPacketInHandler(private val codec: Codec, private val dispatcher: HandshakeDispatcher) : MessageHandler<ClientPacket> {

    private val logger = LoggerFactory.getLogger(ClientPacketInHandler::class.java)!!

    @Suppress("UNCHECKED_CAST")
    override fun handle(ctx: ChannelHandlerContext, message: ClientPacket) {
        val (entity, packet, handshake) = message
        //Decode
        val decoder = codec.get(packet.opcode) as? MessageDecoder<Message>
                ?: run {
                    logger.warn("No decoder for packet: ${packet.opcode}")
                    return
                }

        val decoded = decoder.decode(ctx, packet)
        packet.release()
        if (decoded != null) {
            //Handle
            val handler = dispatcher.get(decoded::class, handshake) as? MessageHandler<Message>
                    ?: run {
                        logger.warn("Unhandled message: $decoded")
                        return
                    }

            try {
                if (handler is PlayerMessageHandler) {
                    val player = World.players.get(entity, ctx.getSession().id) ?: return
                    handler.handle(ctx, player, decoded)
                } else {
                    handler.handle(ctx, decoded)
                }
            } catch (t: Throwable) {
                logger.warn("Error processing message: ", t)
            }
        }
    }

}