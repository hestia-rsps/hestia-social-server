package world.gregs.hestia.social.network.social.handlers

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.cache.crypto.Isaac
import world.gregs.hestia.core.network.codec.decode.SimpleIsaacPacketHandshakeDecoder
import world.gregs.hestia.core.network.codec.message.MessageHandler
import world.gregs.hestia.core.network.codec.message.SimpleIsaacMessageEncoder
import world.gregs.hestia.core.network.getSession
import world.gregs.hestia.social.core.World
import world.gregs.hestia.social.network.social.SocialCodec
import world.gregs.hestia.social.network.social.SocialConnections
import world.gregs.hestia.social.network.social.SocialHandshake
import world.gregs.hestia.social.network.social.decoders.messages.LoginLobby
import world.gregs.hestia.social.network.social.encoders.messages.LobbyDetails

class LoginLobbyHandler(private val codec: SocialCodec) : MessageHandler<LoginLobby> {

    override fun handle(ctx: ChannelHandlerContext, message: LoginLobby) {
        val (username, password, hd, resize, settings, value, isaacKeys) = message

        val session = ctx.getSession()
        val name = World.names.getUserName(username)
        var player = World.players.get(name)
        val wasNull = player == null
        if(player == null) {
            player = World.loadPlayer(username)
            player.session = session
        } else {
            player.linkLobby(session)
        }
        player.send(LobbyDetails(player.names.name, true))

        val inRandom = Isaac(isaacKeys)
        for (i in isaacKeys.indices)
            isaacKeys[i] += 50
        val outRandom = Isaac(isaacKeys)

        ctx.pipeline().apply {
            val handler = get(SocialHandshake::class.java)
            replace("packet", "packet", SimpleIsaacPacketHandshakeDecoder(codec, handler, inRandom))
            replace("encoder", "encoder", SimpleIsaacMessageEncoder(codec, outRandom))
        }

        if(wasNull) {
            World.connect(player)
        } else {
            World.logout(player)
        }

        ctx.pipeline().get(SocialHandshake::class.java).shake(ctx)
        ctx.channel().pipeline().addBefore("handler", "connection", SocialConnections())
    }
}