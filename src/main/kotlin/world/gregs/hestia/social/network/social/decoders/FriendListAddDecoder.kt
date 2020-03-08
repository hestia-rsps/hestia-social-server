package world.gregs.hestia.social.network.social.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.ADD_FRIEND
import world.gregs.hestia.social.network.social.decoders.messages.FriendListAdd

class FriendListAddDecoder : MessageDecoder<FriendListAdd>(Packet.Type.VAR_BYTE, ADD_FRIEND) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): FriendListAdd? {
        val username = packet.readString()
        return FriendListAdd(username)
    }

}