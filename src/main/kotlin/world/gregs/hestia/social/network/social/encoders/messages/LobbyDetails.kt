package world.gregs.hestia.social.network.social.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Sends lobby login success with login details and settings
 * @param name The players display name
 * @param member Whether the player is a registered member
 */
data class LobbyDetails(val name: String, val member: Boolean) : Message