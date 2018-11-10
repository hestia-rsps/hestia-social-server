package world.gregs.hestia.network.worlds

import world.gregs.hestia.WorldDetails
import world.gregs.hestia.network.Session
import world.gregs.hestia.network.codec.inbound.PacketInboundHandler
import world.gregs.hestia.network.packets.Packet
import world.gregs.hestia.network.packets.PacketMap

class WorldsInboundHandler(packets: PacketMap) : PacketInboundHandler(packets) {

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