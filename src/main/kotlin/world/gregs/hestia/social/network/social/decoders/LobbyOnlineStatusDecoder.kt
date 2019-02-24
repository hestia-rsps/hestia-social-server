package world.gregs.hestia.social.network.social.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.ONLINE_STATUS
import world.gregs.hestia.social.network.social.decoders.messages.LobbyOnlineStatus

class LobbyOnlineStatusDecoder : MessageDecoder<LobbyOnlineStatus>(3, ONLINE_STATUS) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): LobbyOnlineStatus? {
        return LobbyOnlineStatus(packet.readByte(), packet.readByte(), packet.readByte())
    }

}