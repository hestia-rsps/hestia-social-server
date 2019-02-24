package world.gregs.hestia.social.network.social.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.cache.compress.Huffman
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.PRIVATE_MESSAGE
import world.gregs.hestia.social.network.social.decoders.messages.MessagePrivate

class MessagePrivateDecoder : MessageDecoder<MessagePrivate>(Packet.Type.VAR_SHORT, PRIVATE_MESSAGE) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): MessagePrivate? {
        val username = packet.readString()
        val message = Huffman.decompress(packet, packet.readSmart()) ?: ""
        return MessagePrivate(username, message)
    }

}