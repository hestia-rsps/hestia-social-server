package world.gregs.hestia.social.network.social.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Public chat message
 * @param client The players client index
 * @param effects The colour and move effect combined
 * @param rights The players rights (0 = normal, 1 = player mod, 2 = admin)
 * @param message The chat message text
 */
data class ChatPublic(val client: Int, val effects: Int, val rights: Int, val message: String) : Message