package world.gregs.hestia.social.network.social.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes
import world.gregs.hestia.social.network.social.decoders.messages.Hyperlink

class HyperlinkDecoder : MessageDecoder<Hyperlink>(Packet.Type.VAR_BYTE, ClientOpcodes.HYPERLINK_TEXT) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): Hyperlink? {
        return Hyperlink(packet.readString(), packet.readString(), packet.readByte())
    }

}