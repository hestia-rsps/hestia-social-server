package world.gregs.hestia.social.network.social.`in`

import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.packets.InboundPacket
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.network.packets.PacketInfo
import world.gregs.hestia.social.core.Server
import world.gregs.hestia.social.network.social.out.WorldListSend

@PacketInfo(4, 34)
class LobbyWorldListRefresh : InboundPacket {

    override fun read(session: Session, packet: Packet) {
        val response = packet.readInt()
        //TODO if no change don't send the full config again.
        session.write(WorldListSend(Server.config))
    }

}