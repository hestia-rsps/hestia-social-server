package world.gregs.hestia.social.network.social.`in`

import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.network.packets.PacketInfo
import world.gregs.hestia.social.api.Player
import world.gregs.hestia.social.core.World
import world.gregs.hestia.social.network.Packets.CHANGE_FRIEND_CHAT
import world.gregs.hestia.social.network.PlayerPacket

@PacketInfo(-1, CHANGE_FRIEND_CHAT)
class FriendsChatRank : PlayerPacket {

    override fun read(player: Player, packet: Packet) {
        val friend = packet.readString()
        val rank = packet.readByteC()//0 = not in friends chat ... 6 = General
        val name = World.names.getName(friend)
        if(name == null) {
            player.message("Could not find player with the username '$friend'.")
            return
        }
        World.channels?.promote(player, name, rank)
    }

}