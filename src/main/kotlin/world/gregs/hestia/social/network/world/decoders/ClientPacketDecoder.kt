package world.gregs.hestia.social.network.world.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.packet.PacketReader
import world.gregs.hestia.core.network.protocol.WorldOpcodes.CLIENT_PACKET
import world.gregs.hestia.core.network.protocol.messages.ClientPacket

class ClientPacketDecoder : MessageDecoder<ClientPacket>(Packet.Type.VAR_SHORT, CLIENT_PACKET) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): ClientPacket? {
        val entity = packet.readInt()
        val handshake = packet.readBoolean()
        return ClientPacket(entity, PacketReader(opcode = packet.readUnsignedByte(), buffer = packet.buffer.readBytes(packet.buffer.readableBytes())), handshake)
    }

}