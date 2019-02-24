package world.gregs.hestia.social.network.social.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message
import world.gregs.hestia.social.api.Name

/**
 * Updates a friend list friend
 * @param name The display name of the friend
 * @param referred Whether the update is to rename the friend
 * @param lobby Whether the friend is in the lobby
 * @param world The world the friend is on
 * @param friendsChatRank The friends rank in players friends chat
 * @param renamed Whether the friend was referred by this player
 */
data class FriendListUpdate(val name: Name, val renamed: Boolean, val lobby: Boolean, val world: Int, val friendsChatRank: Int, val referred: Boolean) : Message