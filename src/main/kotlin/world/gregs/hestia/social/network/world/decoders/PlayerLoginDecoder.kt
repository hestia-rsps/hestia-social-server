package world.gregs.hestia.social.network.world.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.WorldOpcodes.PLAYER_LOGIN_LOBBY
import world.gregs.hestia.core.network.protocol.messages.PlayerLogin

class PlayerLoginDecoder : MessageDecoder<PlayerLogin>(Packet.Type.VAR_BYTE, PLAYER_LOGIN_LOBBY) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): PlayerLogin? {
        return PlayerLogin(packet.readString(), packet.readInt(), packet.readShort())
    }

}