package world.gregs.hestia.social.network.social.out

import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.services.int

class SendFriend(displayName: String, lastName: String, notify: Boolean, online: Boolean, lobby: Boolean, world: Int) : Packet.Builder(85, Packet.Type.VAR_SHORT) {
    init {
        writeByte(notify.int)
        writeString(displayName)
        writeString(lastName)
        writeShort(if(online) world else 0)
        writeByte(0)//Friends chat rank
        writeByte(0)//Script 3603
        if (online) {
            writeString(if(lobby) "Lobby" else "World $world")
            writeByte(0)//Bool - Script 3627
        }
    }
}