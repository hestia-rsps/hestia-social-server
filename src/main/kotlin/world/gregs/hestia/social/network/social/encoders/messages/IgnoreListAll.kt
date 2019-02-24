package world.gregs.hestia.social.network.social.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message
import world.gregs.hestia.social.api.Name

/**
 * Sends the complete ignore list
 * @param names List of ignored player names
 */
data class IgnoreListAll(val names: List<Name>) : Message