package world.gregs.hestia.social.network.world.handlers

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageHandler
import world.gregs.hestia.core.network.getSession
import world.gregs.hestia.core.network.protocol.messages.PlayerLogin
import world.gregs.hestia.social.core.World

class PlayerLoginHandler : MessageHandler<PlayerLogin> {

    override fun handle(ctx: ChannelHandlerContext, message: PlayerLogin) {
        val (username, entity, client) = message
        val world = ctx.getSession().id
        val player = World.players.get(username) ?: return
        player.linkGame(entity, client)
        player.worldId = world
        World.login(player)
    }

}