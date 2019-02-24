package world.gregs.hestia.social.network.social.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Player wants to remove a player from their friend list
 * @param name The display name of the player to remove
 */
data class FriendListRemove(val name: String) : Message