package world.gregs.hestia.social.network.social.`in`

import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.network.packets.PacketInfo
import world.gregs.hestia.social.api.Player
import world.gregs.hestia.social.core.World
import world.gregs.hestia.social.network.Packets.ADD_IGNORE
import world.gregs.hestia.social.network.PlayerPacket

@PacketInfo(-1, ADD_IGNORE)
class AddIgnore : PlayerPacket {
    override fun read(player: Player, packet: Packet) {
        val username = packet.readString()
        val temporary = packet.readByte() == 1
        val name = World.names.getName(username)
        if(name == null) {
            player.message("Could not find player with the username '$username'.")
            return
        }
        World.contacts.addIgnore(player, name, temporary)
    }

}