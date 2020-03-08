package world.gregs.hestia.social.network.world.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.protocol.WorldOpcodes.FRIENDS_CHAT_SETTING
import world.gregs.hestia.core.network.protocol.messages.FriendsChatSettings

class FriendsChatSettingsDecoder : MessageDecoder<FriendsChatSettings>(9, FRIENDS_CHAT_SETTING) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): FriendsChatSettings? {
        return FriendsChatSettings(packet.readInt(), packet.readInt(), packet.readByte())
    }

}