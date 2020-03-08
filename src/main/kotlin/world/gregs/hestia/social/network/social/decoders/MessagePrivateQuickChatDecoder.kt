package world.gregs.hestia.social.network.social.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.QUICK_PRIVATE_MESSAGE
import world.gregs.hestia.social.network.social.decoders.messages.MessagePrivateQuickChat

class MessagePrivateQuickChatDecoder : MessageDecoder<MessagePrivateQuickChat>(Packet.Type.VAR_BYTE, QUICK_PRIVATE_MESSAGE) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): MessagePrivateQuickChat? {
        val username = packet.readString()
        val file = packet.readUnsignedShort()
        val data = if(packet.readableBytes() > 0) ByteArray(packet.readableBytes()) else null
        if(data != null) {
            packet.readBytes(data)
        }
        return MessagePrivateQuickChat(username, file, data)
    }

}