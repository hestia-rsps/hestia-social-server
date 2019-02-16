package world.gregs.hestia.social.network.world.handlers

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageHandler
import world.gregs.hestia.core.network.getSession
import world.gregs.hestia.core.network.protocol.messages.FriendsChatSetupRequest
import world.gregs.hestia.social.core.World

class FriendsChatSetupRequestHandler : MessageHandler<FriendsChatSetupRequest> {

    override fun handle(ctx: ChannelHandlerContext, message: FriendsChatSetupRequest) {
        val world = ctx.getSession().id
        val player = World.players.get(message.entity, world) ?: return
        if(World.channels?.canSetup(player) == true) {
            //Open the ui
            ctx.channel().write(message)
        }
    }

}