package world.gregs.hestia.social.network.social.`in`

import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.packets.InboundPacket
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.network.packets.PacketInfo
import world.gregs.hestia.social.network.social.out.LobbyOutboundPing

@PacketInfo(0, 16)
class LobbyInboundPing : InboundPacket {

    override fun read(session: Session, packet: Packet) {
        val latency = packet.readShort()
        session.write(ping)
        return
    }

    companion object {
        private val ping = LobbyOutboundPing()
    }

}