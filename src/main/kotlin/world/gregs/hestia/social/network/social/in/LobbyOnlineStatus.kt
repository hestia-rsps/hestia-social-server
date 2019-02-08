package world.gregs.hestia.social.network.social.`in`

import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.network.packets.PacketInfo
import world.gregs.hestia.social.api.Player
import world.gregs.hestia.social.core.World
import world.gregs.hestia.social.network.Packets.ONLINE_STATUS
import world.gregs.hestia.social.network.PlayerPacket

@PacketInfo(3, ONLINE_STATUS)
class LobbyOnlineStatus : PlayerPacket {
    override fun read(player: Player, packet: Packet) {
        val unknown1 = packet.readByte()
        World.contacts.setStatus(player, packet.readByte())
        val unknown2 = packet.readByte()
    }
}