package world.gregs.hestia.social.network.social.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Public chat message
 * @param message The message sent
 * @param effects The colour and move effect combined
 */
data class MessagePublic(val message: String, val effects: Int) : Message