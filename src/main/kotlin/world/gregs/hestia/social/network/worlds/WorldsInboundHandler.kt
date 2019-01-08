package world.gregs.hestia.social.network.worlds

import world.gregs.hestia.core.WorldDetails
import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.codec.inbound.PacketInboundHandler
import world.gregs.hestia.core.network.packets.InboundPacket
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.services.load.PacketMap

class WorldsInboundHandler(packets: PacketMap<InboundPacket>) : PacketInboundHandler<InboundPacket>(packets) {

    override fun disconnect(session: Session) {
        Worlds.remove(session.id)
    }

    companion object {
        fun readWorldDetails(packet: Packet): WorldDetails {
            val location = packet.readByte()
            val flag = packet.readShort()
            val activity = packet.readString()
            val address = packet.readString()
            val region = packet.readString()
            val country = packet.readByte()
            return WorldDetails(activity, address, region, location, country, flag)
        }
    }
}