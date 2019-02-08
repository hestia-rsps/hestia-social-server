package world.gregs.hestia.social.network.social.out

import world.gregs.hestia.core.network.packets.Packet

class FriendsChatMessage(data: ByteArray) : Packet.Builder(40, Packet.Type.VAR_BYTE) {
    init {
        writeBytes(data)
    }
}