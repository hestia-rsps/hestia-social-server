package world.gregs.hestia.social.network.social.out

import world.gregs.hestia.core.network.packets.Packet

class QuickFriendsChatMessage(data: ByteArray) : Packet.Builder(20, Packet.Type.VAR_BYTE) {
    init {
        writeBytes(data)
    }
}