package world.gregs.hestia.social.network

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.Message
import world.gregs.hestia.core.network.codec.message.MessageHandler
import world.gregs.hestia.core.network.getSession
import world.gregs.hestia.social.api.Player
import world.gregs.hestia.social.core.World

abstract class PlayerMessageHandler<T : Message> : MessageHandler<T> {
    override fun handle(ctx: ChannelHandlerContext, message: T) {
        val player = World.players.get(ctx.getSession()) ?: return
        handle(ctx, player, message)
    }

    abstract fun handle(ctx: ChannelHandlerContext, player: Player, message: T)
}