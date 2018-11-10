package world.gregs.hestia.network.lobby.`in`

import world.gregs.hestia.network.Session
import world.gregs.hestia.network.packets.InboundPacket
import world.gregs.hestia.network.packets.Packet
import world.gregs.hestia.network.packets.PacketOpcode
import world.gregs.hestia.network.packets.PacketSize
import world.gregs.hestia.network.worlds.Worlds

@PacketSize(4)
@PacketOpcode(34)
class LobbyWorldListRefresh : InboundPacket {

    override fun read(session: Session, packet: Packet, length: Int): Boolean {
        val response = packet.readInt()
        session.write(Worlds.getWorldListPacket())
        return true
    }

}