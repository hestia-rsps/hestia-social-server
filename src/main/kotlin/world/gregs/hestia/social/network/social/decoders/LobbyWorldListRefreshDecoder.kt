package world.gregs.hestia.social.network.social.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.REFRESH_WORLDS
import world.gregs.hestia.social.network.social.decoders.messages.LobbyWorldListRefresh

class LobbyWorldListRefreshDecoder : MessageDecoder<LobbyWorldListRefresh>(4, REFRESH_WORLDS) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): LobbyWorldListRefresh? {
        return LobbyWorldListRefresh(packet.readInt())
    }

}