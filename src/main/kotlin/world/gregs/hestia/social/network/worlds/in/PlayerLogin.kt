package world.gregs.hestia.social.network.worlds.`in`

import org.slf4j.LoggerFactory
import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.packets.InboundPacket
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.network.packets.PacketInfo
import world.gregs.hestia.social.core.World
import world.gregs.hestia.social.network.worlds.WorldPackets.PLAYER_LOGIN_LOBBY

@PacketInfo(-1, PLAYER_LOGIN_LOBBY)
class PlayerLogin : InboundPacket {

    private val logger = LoggerFactory.getLogger(PlayerLogin::class.java)

    override fun read(session: Session, packet: Packet) {
        val username = packet.readString()
        val entityId = packet.readInt()
        val clientIndex = packet.readShort()
        val world = session.id
        val player = World.players.get(username) ?: return
        player.linkGame(entityId, clientIndex)
        player.worldId = world
        World.login(player)
        return
    }

}