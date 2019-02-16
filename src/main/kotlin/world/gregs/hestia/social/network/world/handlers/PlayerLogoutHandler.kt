package world.gregs.hestia.social.network.world.handlers

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageHandler
import world.gregs.hestia.core.network.getSession
import world.gregs.hestia.core.network.protocol.messages.PlayerLogout
import world.gregs.hestia.social.core.World

class PlayerLogoutHandler : MessageHandler<PlayerLogout> {

    override fun handle(ctx: ChannelHandlerContext, message: PlayerLogout) {
        val world = ctx.getSession().id
        val player = World.players.get(message.entity, world) ?: return
        World.disconnect(player)
    }

}