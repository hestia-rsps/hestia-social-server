package world.gregs.hestia.social.network.social.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.CHAT_TYPE
import world.gregs.hestia.social.network.social.decoders.messages.ChatType

class ChatTypeDecoder : MessageDecoder<ChatType>(1, CHAT_TYPE) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): ChatType? {
        val type = packet.readUnsignedByte()
        return ChatType(type)
    }

}