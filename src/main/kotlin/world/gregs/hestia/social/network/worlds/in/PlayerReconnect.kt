package world.gregs.hestia.social.network.worlds.`in`

import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.packets.InboundPacket
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.network.packets.PacketInfo
import world.gregs.hestia.social.core.World
import world.gregs.hestia.social.network.worlds.WorldPackets.PLAYER_RECONNECT

@PacketInfo(-1, PLAYER_RECONNECT)
class PlayerReconnect : InboundPacket {

    override fun read(session: Session, packet: Packet) {
        val username = packet.readString()
        val entityId = packet.readInt()
        val clientIndex = packet.readShort()
        val world = session.id
        val player = World.loadPlayer(username)
        player.linkGame(entityId, clientIndex)
        World.reconnect(player, world)
        return
    }

}