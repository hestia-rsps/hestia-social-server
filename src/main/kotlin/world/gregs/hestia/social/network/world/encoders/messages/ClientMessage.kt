package world.gregs.hestia.social.network.world.encoders.messages

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.Message

/**
 * Message to be encoded and sent to the client as a packet
 * @param entity The player's entity id
 * @param message The message to encode
 */
data class ClientMessage(val entity: Int, val message: Message): Message

/**
 * Sends a message to the client
 * @param entity The player's entity id
 * @param message The message to encode
 */
fun ChannelHandlerContext.sendClient(entity: Int, message: Message) {
    channel().write(ClientMessage(entity, message))
}