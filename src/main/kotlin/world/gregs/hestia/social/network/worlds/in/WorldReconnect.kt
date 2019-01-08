package world.gregs.hestia.social.network.worlds.`in`

import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.packets.InboundPacket
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.network.packets.PacketOpcode
import world.gregs.hestia.core.network.packets.PacketSize
import world.gregs.hestia.social.network.worlds.Worlds
import world.gregs.hestia.social.network.worlds.WorldsInboundHandler

@PacketSize(-1)
@PacketOpcode(11)
class WorldReconnect : InboundPacket {

    override fun read(session: Session, packet: Packet, length: Int): Boolean {
        var id = packet.readByte()
        val details = WorldsInboundHandler.readWorldDetails(packet)
        if(id > 0) {
            Worlds.set(id, details)
        } else {
            id = Worlds.add(details)
        }
        session.id = id
        session.write(Packet.Builder().writeByte(packet.opcode).writeByte(id))
        return true
    }

}