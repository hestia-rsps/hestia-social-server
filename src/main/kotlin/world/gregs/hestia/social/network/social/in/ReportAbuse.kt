package world.gregs.hestia.social.network.social.`in`

import org.slf4j.LoggerFactory
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.network.packets.PacketInfo
import world.gregs.hestia.social.api.Player
import world.gregs.hestia.social.model.ReportTypes
import world.gregs.hestia.social.network.Packets.REPORT_ABUSE
import world.gregs.hestia.social.network.PlayerPacket

@PacketInfo(-1, REPORT_ABUSE)
class ReportAbuse : PlayerPacket {

    private val logger = LoggerFactory.getLogger(ReportAbuse::class.java)

    override fun read(player: Player, packet: Packet) {
        val reported = packet.readString()
        val type = packet.readByte()
        val unknown1 = packet.readByte()
        val unknown2 = packet.readString()
        logger.info("`${player.names.username}` reported `$reported` for `${ReportTypes.values().firstOrNull { it.id == type }?.string}` $unknown1 $unknown2")
    }

}