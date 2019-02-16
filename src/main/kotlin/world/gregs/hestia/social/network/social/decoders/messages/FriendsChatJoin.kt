package world.gregs.hestia.social.network.social.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Player wants to join a friends chat
 * @param name The display name of the friend who's chat to join
 */
data class FriendsChatJoin(val name: String) : Message