package world.gregs.hestia.social.network.world.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.protocol.WorldOpcodes.PLAYER_RECONNECT
import world.gregs.hestia.core.network.protocol.messages.PlayerReconnect

class PlayerReconnectDecoder : MessageDecoder<PlayerReconnect>(Packet.Type.VAR_BYTE, PLAYER_RECONNECT) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): PlayerReconnect? {
        return PlayerReconnect(packet.readString(), packet.readInt(), packet.readShort())
    }

}