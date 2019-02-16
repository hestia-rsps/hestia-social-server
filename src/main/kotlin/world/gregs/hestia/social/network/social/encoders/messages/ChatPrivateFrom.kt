package world.gregs.hestia.social.network.social.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Private message from another player
 * @param name The display name of the player who sent the message
 * @param rights The players rights (0 = normal, 1 = player mod, 2 = admin)
 * @param text The chat message text
 */
data class ChatPrivateFrom(val name: String, val rights: Int, val text: String) : Message