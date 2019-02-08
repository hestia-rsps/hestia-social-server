package world.gregs.hestia.social.network.social.`in`

import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.packets.InboundPacket
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.network.packets.PacketInfo
import world.gregs.hestia.social.core.World

@PacketInfo(-2, 19)
class LoginLobby : InboundPacket {

    override fun read(session: Session, packet: Packet) {
        val username = packet.readString()
        val highDefinition = packet.readByte() == 1
        val resizeable = packet.readByte() == 1
        packet.skip(24)
        val settings = packet.readString()
        val unknown = packet.readInt()
        val crcValues = IntArray(35)
        for (i in crcValues.indices) {
            crcValues[i] = packet.readInt()
        }

        val name = World.names.getUserName(username)
        var player = World.players.get(name)
        if(player == null) {
            player = World.loadPlayer(username)
            player.session = session
            World.connect(player)
        } else {
            player.linkLobby(session)
            World.logout(player)
        }
    }

}