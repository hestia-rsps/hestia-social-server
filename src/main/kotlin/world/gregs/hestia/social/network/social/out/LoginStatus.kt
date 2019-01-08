package world.gregs.hestia.social.network.social.out

import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.services.int

class LoginStatus(username: String, private: Int = 0, world: Int = 0, online: Boolean = true, lobby: Boolean = true) : Packet.Builder(2, Packet.Type.VAR_BYTE) {
    init {
        writeString(username)
        writeByte(private)
        writeByte(world)
        writeByte(online.int)
        writeByte(lobby.int)
    }
}