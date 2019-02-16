package world.gregs.hestia.social.network.social.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.JOIN_FRIEND_CHAT
import world.gregs.hestia.social.network.social.decoders.messages.FriendsChatJoin

class FriendsChatJoinDecoder : MessageDecoder<FriendsChatJoin>(Packet.Type.VAR_BYTE, JOIN_FRIEND_CHAT) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): FriendsChatJoin? {
        return FriendsChatJoin(packet.readString())
    }

}