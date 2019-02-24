package world.gregs.hestia.social.network.social.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message
import world.gregs.hestia.social.api.Worlds

/**
 * Sends the complete lobby world list
 * @param worlds The list of worlds
 * @param configuration Whether to re-/send the worlds configuration's
 * @param status Whether to re-/send the worlds status's
 */
data class WorldListAll(val worlds: Worlds, val configuration: Boolean = true, val status: Boolean = true) : Message