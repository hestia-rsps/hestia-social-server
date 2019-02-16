package world.gregs.hestia.social.network.world.handlers

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageHandler
import world.gregs.hestia.core.network.getSession
import world.gregs.hestia.core.network.protocol.messages.SocialDetails
import world.gregs.hestia.core.network.protocol.messages.WorldInfo
import world.gregs.hestia.social.core.Server

class WorldInfoHandler : MessageHandler<WorldInfo> {

    override fun handle(ctx: ChannelHandlerContext, message: WorldInfo) {
        val (details) = message
        var id = details.id
        val session = ctx.getSession()
        if(id > 0) {
            Server.set(id, details)
        } else {
            id = Server.add(details)
        }
        Server.sessions[id] = session
        session.id = id
        session.write(SocialDetails(id))
    }

}