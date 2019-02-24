package world.gregs.hestia.social.network.social.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Notified the type of message before a message is sent
 * @param type The type of message sent (0 = public, 1 = friends chat)
 */
data class ChatType(val type: Int) : Message