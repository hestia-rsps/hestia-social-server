package world.gregs.hestia.social.network.social.out

import world.gregs.hestia.core.network.packets.Packet

class WorldListSend(byteArray: ByteArray) : Packet.Builder(88, Packet.Type.VAR_SHORT) {

    init {
        writeBytes(byteArray)
    }
}