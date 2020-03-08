package world.gregs.hestia.social.network.world.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.protocol.Details
import world.gregs.hestia.core.network.protocol.WorldOpcodes.WORLD_CONNECTION
import world.gregs.hestia.core.network.protocol.messages.WorldInfo

class WorldInfoDecoder : MessageDecoder<WorldInfo>(Packet.Type.VAR_BYTE, WORLD_CONNECTION) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): WorldInfo? {
        return WorldInfo(Details(packet.readByte(), 0, packet.readString(), packet.readString(), packet.readByte(), packet.readByte(), packet.readString(), packet.readShort()))
    }

}