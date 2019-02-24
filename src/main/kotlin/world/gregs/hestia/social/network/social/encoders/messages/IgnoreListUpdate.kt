package world.gregs.hestia.social.network.social.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Updates one player in ignore list
 * @param name Ignored players display name
 * @param previous Ignored players previous display name
 * @param renamed Whether the update is to update their display name
 * @param unknown Unknown
 */
data class IgnoreListUpdate(val name: String, val previous: String, val renamed: Boolean, val unknown: Boolean) : Message