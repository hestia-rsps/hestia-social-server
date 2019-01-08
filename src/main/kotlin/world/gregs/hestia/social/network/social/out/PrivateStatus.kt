package world.gregs.hestia.social.network.social.out

import world.gregs.hestia.core.network.packets.Packet

class PrivateStatus(status: Int) : Packet.Builder(134) {
    init {
        writeByte(status)
    }
}