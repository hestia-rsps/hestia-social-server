package world.gregs.hestia.network.lobby.`in`

import world.gregs.hestia.network.Session
import world.gregs.hestia.network.lobby.out.LobbyOutboundPing
import world.gregs.hestia.network.packets.InboundPacket
import world.gregs.hestia.network.packets.Packet
import world.gregs.hestia.network.packets.PacketOpcode
import world.gregs.hestia.network.packets.PacketSize

@PacketSize(0)
@PacketOpcode(16)
class LobbyInboundPing : InboundPacket {

    override fun read(session: Session, packet: Packet, length: Int): Boolean {
        val latency = packet.readShort()
        session.write(LobbyOutboundPing())
        return true
    }

}