package world.gregs.hestia.social.network.world.handlers

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageHandler
import world.gregs.hestia.core.network.getSession
import world.gregs.hestia.core.network.protocol.encoders.messages.WidgetComponentText
import world.gregs.hestia.core.network.protocol.messages.FriendsChatName
import world.gregs.hestia.social.core.World

class FriendsChatNameHandler : MessageHandler<FriendsChatName> {

    override fun handle(ctx: ChannelHandlerContext, message: FriendsChatName) {
        val (entity, name) = message
        val world = ctx.getSession().id
        val player = World.players.get(entity, world) ?: return
        val channelName = when {
            name.isEmpty() -> {
                World.channels?.disable(player)
                null
            }
            else -> World.channels?.name(player, name)
        } ?: "Chat disabled"
        player.send(WidgetComponentText(1108, 22, channelName))
    }

}