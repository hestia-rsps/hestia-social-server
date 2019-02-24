package world.gregs.hestia.social.network.social.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Player has changed their online status while in the lobby
 * @param first Unknown
 * @param status The players online status
 * @param second Unknown
 */
data class LobbyOnlineStatus(val first: Int, val status: Int, val second: Int) : Message