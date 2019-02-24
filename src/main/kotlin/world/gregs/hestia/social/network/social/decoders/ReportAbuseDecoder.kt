package world.gregs.hestia.social.network.social.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.REPORT_ABUSE
import world.gregs.hestia.social.network.social.decoders.messages.ReportAbuse

class ReportAbuseDecoder : MessageDecoder<ReportAbuse>(Packet.Type.VAR_BYTE, REPORT_ABUSE) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): ReportAbuse? {
        val reported = packet.readString()
        val type = packet.readByte()
        val unknown1 = packet.readByte()
        val unknown2 = packet.readString()
        return ReportAbuse(reported, type, unknown1, unknown2)
    }

}