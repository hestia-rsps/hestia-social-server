package world.gregs.hestia.social.network.social.out

import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.services.int
import world.gregs.hestia.social.api.Name

class SendFriend(name: Name, renamed: Boolean, lobby: Boolean, world: Int, friendChatRank: Int, referred: Boolean) : Packet.Builder(85, Packet.Type.VAR_SHORT) {
    init {
        writeByte(renamed.int)
        writeString(name.name)
        writeString(name.previousName)
        writeShort(world)
        writeByte(friendChatRank)//Friends chat rank
        writeByte(referred.int)//Refer-a-friend
        if (world > 0) {
            writeString(if(lobby) "Lobby" else "World $world")
            writeByte(0)//Bool - Script 3627
        }
    }
}