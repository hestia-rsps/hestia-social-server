package world.gregs.hestia.social.network.social.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Request to open a hyperlink
 * @param name Readable name
 * @param script Windows script name
 * @param third Unknown value
 */
data class Hyperlink(val name: String, val script: String, val third: Int) : Message