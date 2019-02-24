package world.gregs.hestia.social.network.world.handlers

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageHandler
import world.gregs.hestia.core.network.getSession
import world.gregs.hestia.core.network.protocol.messages.PlayerReconnect
import world.gregs.hestia.social.core.World

class PlayerReconnectHandler : MessageHandler<PlayerReconnect> {

    override fun handle(ctx: ChannelHandlerContext, message: PlayerReconnect) {
        val (username, entity, client) = message
        val world = ctx.getSession().id
        val player = World.loadPlayer(username)
        player.linkGame(entity, client)
        World.reconnect(player, world)
    }

}