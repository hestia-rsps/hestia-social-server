package world.gregs.hestia.social.network.worlds.`in`

import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.packets.InboundPacket
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.network.packets.PacketInfo
import world.gregs.hestia.social.core.Server
import world.gregs.hestia.social.network.worlds.WorldPacketDecoder
import world.gregs.hestia.social.network.worlds.WorldPackets.WORLD_CONNECTION
import world.gregs.hestia.social.network.worlds.out.SocialServerDetails

@PacketInfo(-1, WORLD_CONNECTION)
class WorldReconnect : InboundPacket {

    override fun read(session: Session, packet: Packet) {
        var id = packet.readByte()
        val details = WorldPacketDecoder.readWorldDetails(packet)
        if(id > 0) {
            Server.set(id, details)
        } else {
            id = Server.add(details)
        }
        Server.sessions[id] = session
        session.id = id
        session.write(SocialServerDetails(id))
    }

}