package world.gregs.hestia.social.network.social.handlers

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.social.api.Player
import world.gregs.hestia.social.core.World
import world.gregs.hestia.social.network.PlayerMessageHandler
import world.gregs.hestia.social.network.social.decoders.messages.LobbyOnlineStatus

class LobbyOnlineStatusHandler : PlayerMessageHandler<LobbyOnlineStatus>() {

    override fun handle(ctx: ChannelHandlerContext, player: Player, message: LobbyOnlineStatus) {
        World.contacts.setStatus(player, message.status)
    }

}