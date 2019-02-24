package world.gregs.hestia.social.network.social.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Updates the client private status
 * @param status The players online visibility status
 */
data class PrivateStatus(val status: Int) : Message