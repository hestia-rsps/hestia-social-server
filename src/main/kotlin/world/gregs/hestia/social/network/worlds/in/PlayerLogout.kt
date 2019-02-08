package world.gregs.hestia.social.network.worlds.`in`

import org.slf4j.LoggerFactory
import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.packets.InboundPacket
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.network.packets.PacketInfo
import world.gregs.hestia.social.core.World
import world.gregs.hestia.social.network.worlds.WorldPackets.PLAYER_LOGOUT

@PacketInfo(4, PLAYER_LOGOUT)
class PlayerLogout : InboundPacket {

    private val logger = LoggerFactory.getLogger(PlayerLogout::class.java)

    override fun read(session: Session, packet: Packet) {
        val entityId = packet.readInt()
        val world = session.id
        val player = World.players.get(entityId, world) ?: return
        World.disconnect(player)
        return
    }

}