package world.gregs.hestia.social.network.social.`in`

import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.network.packets.PacketInfo
import world.gregs.hestia.social.api.Player
import world.gregs.hestia.social.core.World
import world.gregs.hestia.social.network.Packets.JOIN_FRIEND_CHAT
import world.gregs.hestia.social.network.PlayerPacket

@PacketInfo(-1, JOIN_FRIEND_CHAT)
class FriendsChatJoin : PlayerPacket {

    override fun read(player: Player, packet: Packet) {
        val friendsName = packet.readString()

        if(friendsName.isEmpty()) {
            player.channel?.leave(player, false)
        } else {
            val name = World.names.getName(friendsName)
            if(name == null) {
                player.message("Could not find player with the username '$friendsName'.")
                return
            }
            World.channels?.join(player, name)
        }
    }

}