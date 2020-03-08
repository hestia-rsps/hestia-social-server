package world.gregs.hestia.social.network.social.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.cache.compress.Huffman
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.PUBLIC_MESSAGE
import world.gregs.hestia.social.network.social.decoders.messages.MessagePublic

class MessagePublicDecoder : MessageDecoder<MessagePublic>(Packet.Type.VAR_BYTE, PUBLIC_MESSAGE) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): MessagePublic? {
        val colour = packet.readUnsignedByte()
        val move = packet.readUnsignedByte()
        val message = Huffman.decompress(packet, packet.readSmart()) ?: ""
        val effects = colour shl 8 or (move and 0xff)
        return MessagePublic(message, effects)
    }

}