package world.gregs.hestia.social.network.social.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Player wants to add a player to their ignore list
 * Note: temporary ignores optional after report abuse
 * @param name The display name of the player to add
 * @param temporary Whether the ignore will be removed after logout
 */
data class IgnoreListAdd(val name: String, val temporary: Boolean) : Message