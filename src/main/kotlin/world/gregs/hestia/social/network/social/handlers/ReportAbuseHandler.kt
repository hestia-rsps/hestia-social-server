package world.gregs.hestia.social.network.social.handlers

import io.netty.channel.ChannelHandlerContext
import org.slf4j.LoggerFactory
import world.gregs.hestia.social.api.Player
import world.gregs.hestia.social.model.ReportTypes
import world.gregs.hestia.social.network.PlayerMessageHandler
import world.gregs.hestia.social.network.social.decoders.messages.ReportAbuse

class ReportAbuseHandler : PlayerMessageHandler<ReportAbuse>() {

    private val logger = LoggerFactory.getLogger(ReportAbuseHandler::class.java)!!

    override fun handle(ctx: ChannelHandlerContext, player: Player, message: ReportAbuse) {
        val (username, type, integer, string) = message
        logger.info("`${player.names.username}` reported `$username` for `${ReportTypes.values().firstOrNull { it.id == type }?.string}` $integer $string")
    }

}