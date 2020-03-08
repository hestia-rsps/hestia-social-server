package world.gregs.hestia.social.network.world.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.protocol.WorldOpcodes.FRIENDS_CHAT_NAME
import world.gregs.hestia.core.network.protocol.messages.FriendsChatName

class FriendsChatNameDecoder : MessageDecoder<FriendsChatName>(Packet.Type.VAR_BYTE, FRIENDS_CHAT_NAME) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): FriendsChatName? {
        return FriendsChatName(packet.readInt(), packet.readString())
    }

}