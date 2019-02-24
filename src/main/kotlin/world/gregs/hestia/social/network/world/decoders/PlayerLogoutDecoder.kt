package world.gregs.hestia.social.network.world.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.WorldOpcodes.PLAYER_LOGOUT
import world.gregs.hestia.core.network.protocol.messages.PlayerLogout

class PlayerLogoutDecoder : MessageDecoder<PlayerLogout>(4, PLAYER_LOGOUT) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): PlayerLogout? {
        return PlayerLogout(packet.readInt())
    }

}