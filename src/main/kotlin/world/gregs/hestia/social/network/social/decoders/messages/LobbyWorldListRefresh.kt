package world.gregs.hestia.social.network.social.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Client has requested the lobby world list be refreshed
 * @param response The response value required for latency calculation
 */
data class LobbyWorldListRefresh(val response: Int) : Message