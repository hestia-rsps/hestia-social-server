package world.gregs.hestia.social.network.social.`in`

import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.network.packets.PacketInfo
import world.gregs.hestia.social.api.Player
import world.gregs.hestia.social.core.World
import world.gregs.hestia.social.network.Packets.KICK_FRIEND_CHAT
import world.gregs.hestia.social.network.PlayerPacket

@PacketInfo(-1, KICK_FRIEND_CHAT)
class FriendsChatKick : PlayerPacket {

    override fun read(player: Player, packet: Packet) {
        val targetName = packet.readString()
        val name = World.names.getName(targetName)
        if(name == null) {
            player.message("Could not find player with the username '$targetName'.")
            return
        }
        player.channel?.kick(player, name, true)
    }

}