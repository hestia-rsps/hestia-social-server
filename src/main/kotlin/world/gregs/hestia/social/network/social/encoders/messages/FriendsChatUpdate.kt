package world.gregs.hestia.social.network.social.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message
import world.gregs.hestia.social.api.FriendsChat

/**
 * Updates the details of the friends chat
 * @param channel The friends chat channel to display
 */
data class FriendsChatUpdate(val channel: FriendsChat) : Message