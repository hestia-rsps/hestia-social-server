package world.gregs.hestia.social.network.social.`in`

import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.network.packets.PacketInfo
import world.gregs.hestia.social.api.Player
import world.gregs.hestia.social.network.Packets.HYPERLINK_TEXT
import world.gregs.hestia.social.network.PlayerPacket

@PacketInfo(-1, HYPERLINK_TEXT)
class HyperlinkText : PlayerPacket {

    override fun read(player: Player, packet: Packet) {
        val type = packet.readString()
        val scriptFileName = packet.readString()
        val unknown = packet.readByte()
    }

}