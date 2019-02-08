package world.gregs.hestia.social.network.worlds.`in`

import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.packets.InboundPacket
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.network.packets.PacketInfo
import world.gregs.hestia.social.core.World
import world.gregs.hestia.social.network.worlds.WorldPackets.FRIENDS_CHAT_SETUP
import world.gregs.hestia.social.network.worlds.out.SendWorldPacket
import world.gregs.hestia.social.network.worlds.out.world.OpenFriendsChatSetup

@PacketInfo(4, FRIENDS_CHAT_SETUP)
class FriendsChatSetup : InboundPacket {
    override fun read(session: Session, packet: Packet) {
        val entityId = packet.readInt()
        val player = World.players.get(entityId, session.id) ?: return
        if(World.channels?.canSetup(player) == true) {
            //Open the ui
            session.write(SendWorldPacket(entityId, OpenFriendsChatSetup()))
        }
        return
    }
}