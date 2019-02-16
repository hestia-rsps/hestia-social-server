package world.gregs.hestia.social.network.social.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Client report about another player
 * @param name The display name of the accused player
 * @param type The type of offence supposedly committed
 * @param integer Unknown
 * @param string Unknown
 */
data class ReportAbuse(val name: String, val type: Int, val integer: Int, val string: String) : Message