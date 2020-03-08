package world.gregs.hestia.social.network.social.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Client has requested the lobby world list be refreshed
 * @param crc World list version crc
 */
data class LobbyWorldListRefresh(val crc: Int) : Message