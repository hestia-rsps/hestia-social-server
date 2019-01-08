package world.gregs.hestia.social.network

import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.packets.InboundPacket
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.social.api.Player
import world.gregs.hestia.social.core.World

interface PlayerPacket : InboundPacket {
    override fun read(session: Session, packet: Packet) {
        val player = World.players.get(session) ?: return
        read(player, packet)
    }

    fun read(player: Player, packet: Packet)
}