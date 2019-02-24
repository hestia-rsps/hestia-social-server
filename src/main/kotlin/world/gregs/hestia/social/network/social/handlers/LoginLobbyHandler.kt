package world.gregs.hestia.social.network.social.handlers

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageHandler
import world.gregs.hestia.core.network.getSession
import world.gregs.hestia.social.core.World
import world.gregs.hestia.social.network.social.SocialConnections
import world.gregs.hestia.social.network.social.SocialHandshake
import world.gregs.hestia.social.network.social.decoders.messages.LoginLobby

class LoginLobbyHandler : MessageHandler<LoginLobby> {

    override fun handle(ctx: ChannelHandlerContext, message: LoginLobby) {
        val (username, password, hd, resize, settings, value, serverSeed, clientSeed) = message
        val session = ctx.getSession()
        val name = World.names.getUserName(username)
        var player = World.players.get(name)
        if(player == null) {
            player = World.loadPlayer(username)
            player.session = session
            World.connect(player)
        } else {
            player.linkLobby(session)
            World.logout(player)
        }
        ctx.pipeline().get(SocialHandshake::class.java).shake(ctx)
        ctx.channel().pipeline().apply {
            addBefore("handler", "connection", SocialConnections())
        }
    }

}