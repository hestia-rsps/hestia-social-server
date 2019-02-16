package world.gregs.hestia.social.network.social.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Private message sent to another player
 * @param name The friends display name
 * @param message The message sent
 */
data class MessagePrivate(val name: String, val message: String) : Message