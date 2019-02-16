package world.gregs.hestia.social.network.social.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.KICK_FRIEND_CHAT
import world.gregs.hestia.social.network.social.decoders.messages.FriendsChatKick

class FriendsChatKickDecoder : MessageDecoder<FriendsChatKick>(Packet.Type.VAR_BYTE, KICK_FRIEND_CHAT) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): FriendsChatKick? {
        val username = packet.readString()
        return FriendsChatKick(username)
    }

}