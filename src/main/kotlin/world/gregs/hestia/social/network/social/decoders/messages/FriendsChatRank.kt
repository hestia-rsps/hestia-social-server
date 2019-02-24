package world.gregs.hestia.social.network.social.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Player wants to change the rank of a friend on their friend list
 * @param name The display name of the player who's rank to change
 * @param rank The rank to give their friend
 */
data class FriendsChatRank(val name: String, val rank: Int) : Message