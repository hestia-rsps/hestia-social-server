package world.gregs.hestia.social.network.social.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.ADD_IGNORE
import world.gregs.hestia.social.network.social.decoders.messages.IgnoreListAdd

class IgnoreListAddDecoder : MessageDecoder<IgnoreListAdd>(Packet.Type.VAR_BYTE, ADD_IGNORE) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): IgnoreListAdd? {
        val username = packet.readString()
        val temporary = packet.readBoolean()
        return IgnoreListAdd(username, temporary)
    }

}