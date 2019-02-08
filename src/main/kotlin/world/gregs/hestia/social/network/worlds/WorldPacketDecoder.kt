package world.gregs.hestia.social.network.worlds

import org.slf4j.LoggerFactory
import world.gregs.hestia.core.network.codec.decode.PacketDecoder
import world.gregs.hestia.core.network.packets.InboundPacket
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.services.load.PacketMap
import world.gregs.hestia.core.world.WorldDetails

class WorldPacketDecoder : PacketDecoder() {

    override val logger = LoggerFactory.getLogger(WorldPacketDecoder::class.java)!!

    override fun getSize(opcode: Int): Int? {
        return packets.getSize(opcode)
    }

    companion object {
        lateinit var packets: PacketMap<InboundPacket>

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