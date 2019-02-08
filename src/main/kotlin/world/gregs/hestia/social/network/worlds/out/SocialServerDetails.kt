package world.gregs.hestia.social.network.worlds.out

import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.social.network.social.SocialPacketDecoder
import world.gregs.hestia.social.network.worlds.WorldPackets.SOCIAL_SERVER_DETAILS

class SocialServerDetails(worldId: Int) : Packet.Builder(SOCIAL_SERVER_DETAILS, Packet.Type.VAR_BYTE) {
    init {
        writeByte(worldId)
        val packets = SocialPacketDecoder.packets
        writeByte(packets.size)
        packets.forEach {
            writeByte(it.key)//Id
            writeByte(it.value.second)//Size
        }
    }
}