package world.gregs.hestia.ls.network.lobby.`in`

import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.packets.InboundPacket
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.network.packets.PacketOpcode
import world.gregs.hestia.core.network.packets.PacketSize
import world.gregs.hestia.ls.network.lobby.out.LobbyOutboundPing

@PacketSize(0)
@PacketOpcode(16)
class LobbyInboundPing : InboundPacket {

    override fun read(session: Session, packet: Packet, length: Int): Boolean {
        val latency = packet.readShort()
        session.write(LobbyOutboundPing())
        return true
    }

}