package world.gregs.hestia.social.network.worlds.`in`

import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.packets.InboundPacket
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.network.packets.PacketInfo
import world.gregs.hestia.social.core.World
import world.gregs.hestia.social.network.social.out.InterfaceComponentText
import world.gregs.hestia.social.network.worlds.WorldPackets.FRIENDS_CHAT_NAME
import world.gregs.hestia.social.network.worlds.out.SendClientPacket

@PacketInfo(-1, FRIENDS_CHAT_NAME)
class FriendsChatName : InboundPacket {
    override fun read(session: Session, packet: Packet) {
        val entityId = packet.readInt()
        val name = packet.readString()
        val world = session.id
        val player = World.players.get(entityId, world) ?: return
        val channelName = when {
            name.isEmpty() -> {
                World.channels?.disable(player)
                null
            }
            else -> World.channels?.name(player, name)
        } ?: "Chat disabled"
        session.write(SendClientPacket(entityId, InterfaceComponentText(1108, 22, channelName)))
    }
}