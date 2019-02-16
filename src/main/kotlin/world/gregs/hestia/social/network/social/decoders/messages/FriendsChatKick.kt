package world.gregs.hestia.social.network.social.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Player wants to kick a player from their friends chat
 * @param name The display name of the player to kick
 */
data class FriendsChatKick(val name: String) : Message