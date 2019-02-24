package world.gregs.hestia.social.network.social.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Player wants to add another player to their friend list
 * @param name The display name of the player to add
 */
data class FriendListAdd(val name: String) : Message