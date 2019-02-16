package world.gregs.hestia.social.network.social.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message
import world.gregs.hestia.social.api.FriendsChat
import world.gregs.hestia.social.api.Player

/**
 * Updates the rank of a member in the friends chat list
 * @param member The player who's rank to update
 * @param rank Their new friend chat rank
 */
data class FriendsChatListAppend(val member: Player, val rank: FriendsChat.Ranks) : Message