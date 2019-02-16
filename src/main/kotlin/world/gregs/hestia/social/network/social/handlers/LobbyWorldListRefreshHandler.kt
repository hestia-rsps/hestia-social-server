package world.gregs.hestia.social.network.social.handlers

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.social.api.Player
import world.gregs.hestia.social.core.Server
import world.gregs.hestia.social.network.PlayerMessageHandler
import world.gregs.hestia.social.network.social.decoders.messages.LobbyWorldListRefresh
import world.gregs.hestia.social.network.social.encoders.messages.WorldListAll

class LobbyWorldListRefreshHandler : PlayerMessageHandler<LobbyWorldListRefresh>() {

    override fun handle(ctx: ChannelHandlerContext, player: Player, message: LobbyWorldListRefresh) {
        //TODO if no changes then the full config doesn't need to be sent again.
        player.send(WorldListAll(Server.worlds, configuration = true, status = true))// WorldListUpdate(Server.config))
    }

}