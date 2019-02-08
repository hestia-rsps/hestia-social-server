package world.gregs.hestia.social.network.social.`in`

import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.network.packets.PacketInfo
import world.gregs.hestia.social.api.OnlineStatus
import world.gregs.hestia.social.api.Player
import world.gregs.hestia.social.core.World
import world.gregs.hestia.social.network.Packets.QUICK_PRIVATE_MESSAGE
import world.gregs.hestia.social.network.PlayerPacket
import world.gregs.hestia.social.network.social.out.QuickPrivateMessageReceive
import world.gregs.hestia.social.network.social.out.QuickPrivateMessageSend

@PacketInfo(-1, QUICK_PRIVATE_MESSAGE)
class QuickPrivateMessage : PlayerPacket {
    override fun read(player: Player, packet: Packet) {
        val username = packet.readString()
        val fileId = packet.readUnsignedShort()
        val data = if(packet.readableBytes() > 0) ByteArray(packet.readableBytes()) else null
        if(data != null) {
            packet.readBytes(data)
        }
        val name = World.names.getName(username)
        if(name == null) {
            player.message("Could not find player with the username '$username'.")
            return
        }
        val friend = World.players.get(name) ?: return
        if(player.status == OnlineStatus.PRIVATE) {
            World.contacts.setStatus(player, OnlineStatus.FRIENDS)
        }
        player.send(QuickPrivateMessageSend(name.name, fileId, data))
        friend.send(QuickPrivateMessageReceive(player.names.name, player.rights, fileId, data))
    }
}