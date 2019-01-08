package world.gregs.hestia.social.network.social.`in`

import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.network.packets.PacketInfo
import world.gregs.hestia.social.api.OnlineStatus.Companion.FRIENDS
import world.gregs.hestia.social.api.OnlineStatus.Companion.PRIVATE
import world.gregs.hestia.social.api.Player
import world.gregs.hestia.social.core.World
import world.gregs.hestia.social.network.Huffman
import world.gregs.hestia.social.network.Packets.PRIVATE_MESSAGE
import world.gregs.hestia.social.network.PlayerPacket
import world.gregs.hestia.social.network.social.out.PrivateMessageReceive
import world.gregs.hestia.social.network.social.out.PrivateMessageSend

@PacketInfo(-2, PRIVATE_MESSAGE)
class PrivateMessage : PlayerPacket {
    override fun read(player: Player, packet: Packet) {
        val username = packet.readString()
        val decoded = Huffman.decompress(packet, packet.readSmart()) ?: return
        val message = decoded.capitalize()
        val name = World.names.getName(username)
        if(name == null) {
            player.message("Could not find player with the username '$username'.")
            return
        }
        val friend = World.players.get(name) ?: return
        if(player.status == PRIVATE) {
            World.contacts.setStatus(player, FRIENDS)
        }
        player.send(PrivateMessageSend(name.name, message))
        friend.send(PrivateMessageReceive(player.names.name, player.rights, message))
    }
}