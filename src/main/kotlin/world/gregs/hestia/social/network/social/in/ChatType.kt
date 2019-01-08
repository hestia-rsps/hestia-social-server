package world.gregs.hestia.social.network.social.`in`

import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.network.packets.PacketInfo
import world.gregs.hestia.social.api.Player
import world.gregs.hestia.social.network.Packets
import world.gregs.hestia.social.network.PlayerPacket

@PacketInfo(1, Packets.CHAT_TYPE)
class ChatType : PlayerPacket {

    override fun read(player: Player, packet: Packet) {
        player.messenger.messageType = packet.readUnsignedByte()
    }

}