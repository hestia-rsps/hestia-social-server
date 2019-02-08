package world.gregs.hestia.social.network.worlds.`in`

import org.slf4j.LoggerFactory
import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.codec.inbound.packet.PacketProcessor
import world.gregs.hestia.core.network.packets.InboundPacket
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.network.packets.PacketInfo
import world.gregs.hestia.social.core.World
import world.gregs.hestia.social.network.PlayerPacket
import world.gregs.hestia.social.network.social.SocialPacketDecoder
import world.gregs.hestia.social.network.worlds.WorldPackets.CLIENT_PACKET

@PacketInfo(-2, CLIENT_PACKET)
class ClientPacket : InboundPacket, PacketProcessor<InboundPacket> {
    override fun getHandler(opcode: Int): InboundPacket? {
        return SocialPacketDecoder.packets.getPacket(opcode)
    }

    override val logger = LoggerFactory.getLogger(ClientPacket::class.java)!!
    private var entityId = -1

    override fun process(session: Session, handler: InboundPacket?, packet: Packet) {
        if(handler is PlayerPacket) {
            val player = World.players.get(entityId, session.id) ?: return
            handler.read(player, packet)
        }
    }

    override fun read(session: Session, packet: Packet) {
        entityId = packet.readInt()
        try {
            //Read packet opcode
            val opcode = packet.readUnsignedByte()
            //Handle data
            process(session, Packet(opcode = opcode, buffer = packet.buffer.readBytes(packet.buffer.readableBytes())))
        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
        }
    }
}