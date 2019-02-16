package world.gregs.hestia.social.network.social.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.QUICK_PUBLIC_MESSAGE
import world.gregs.hestia.social.network.social.decoders.messages.MessagePublicQuickChat

class MessagePublicQuickChatDecoder : MessageDecoder<MessagePublicQuickChat>(Packet.Type.VAR_BYTE, QUICK_PUBLIC_MESSAGE) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): MessagePublicQuickChat? {
        val script = packet.readByte()
        val file = packet.readUnsignedShort()
        val data: ByteArray? = if(packet.readableBytes() > 0) ByteArray(packet.readableBytes()) else null
        if(data != null) {
            packet.readBytes(data)
        }
        return MessagePublicQuickChat(script, file, data)
    }

}