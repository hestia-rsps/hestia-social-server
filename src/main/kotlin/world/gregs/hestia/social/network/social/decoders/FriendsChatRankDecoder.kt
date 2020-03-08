package world.gregs.hestia.social.network.social.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.io.Modifier
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.CHANGE_FRIEND_CHAT
import world.gregs.hestia.social.network.social.decoders.messages.FriendsChatRank

class FriendsChatRankDecoder : MessageDecoder<FriendsChatRank>(Packet.Type.VAR_BYTE, CHANGE_FRIEND_CHAT) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): FriendsChatRank? {
        val username = packet.readString()
        val rank = packet.readByte(Modifier.INVERSE)
        return FriendsChatRank(username, rank)
    }

}