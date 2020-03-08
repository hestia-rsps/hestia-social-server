package world.gregs.hestia.social.network.social.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.REMOVE_FRIEND
import world.gregs.hestia.social.network.social.decoders.messages.FriendListRemove

class FriendListRemoveDecoder : MessageDecoder<FriendListRemove>(Packet.Type.VAR_BYTE, REMOVE_FRIEND) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): FriendListRemove? {
        val username = packet.readString()
        return FriendListRemove(username)
    }

}