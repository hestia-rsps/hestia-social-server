package world.gregs.hestia.social.network.social.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Private message sent to another player
 * @param name The display name of the player the message was sent to
 * @param text The chat message text
 */
data class ChatPrivateTo(val name: String, val text: String) : Message