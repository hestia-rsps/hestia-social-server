package world.gregs.hestia.social.network.social.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.REMOVE_IGNORE
import world.gregs.hestia.social.network.social.decoders.messages.IgnoreListRemove

class IgnoreListRemoveDecoder : MessageDecoder<IgnoreListRemove>(Packet.Type.VAR_BYTE, REMOVE_IGNORE) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): IgnoreListRemove? {
        val username = packet.readString()
        return IgnoreListRemove(username)
    }

}