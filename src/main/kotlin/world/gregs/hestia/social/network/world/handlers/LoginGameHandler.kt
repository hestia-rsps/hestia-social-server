package world.gregs.hestia.social.network.world.handlers

import io.netty.channel.ChannelHandlerContext
import org.slf4j.LoggerFactory
import world.gregs.hestia.core.network.client.Response
import world.gregs.hestia.core.network.codec.message.MessageHandler
import world.gregs.hestia.core.network.protocol.messages.LoginGame
import world.gregs.hestia.core.network.protocol.messages.PlayerLoginResponse
import world.gregs.hestia.core.network.protocol.messages.PlayerLoginSuccess
import world.gregs.hestia.social.core.World

class LoginGameHandler : MessageHandler<LoginGame> {

    private val logger = LoggerFactory.getLogger(LoginGameHandler::class.java)!!

    override fun handle(ctx: ChannelHandlerContext, message: LoginGame) {
        val (session, username, password, displayMode, screenWidth, screenHeight, antialiasLevel, settings, affiliateId, os, versionType, vendorType, javaRelease, javaVersion, javaUpdate, processorCount) = message

        // invalid chars
        if (username.length <= 1 || username.length >= 15 || username.contains("?") || username.contains(":")
                || username.endsWith("<") || username.contains("\\") || username.contains("*") || username.startsWith("_")
                || username.contains("\"")) {
            ctx.respond(session, Response.INVALID_CREDENTIALS)
            return
        }

//        val salt: String = Encryption.salt
//        val password = Encryption.hashSHA("test$salt")

        val name = World.names.getUserName(username)
        if(name == null) {
            logger.info("Error finding player name '$username'")
            ctx.respond(session, Response.INVALID_LOGIN_SERVER)
            return
        }

        val player = World.players.get(name)
        if(player == null) {
            logger.info("Error finding player instance '$name'")
            ctx.respond(session, Response.COULD_NOT_COMPLETE_LOGIN)
            return
        }

        ctx.channel().write(PlayerLoginSuccess(session, name.name, displayMode, screenWidth, screenHeight))
    }

    private fun ChannelHandlerContext.respond(session: Int, response: Response) {
        channel().write(PlayerLoginResponse(session, response))
    }

}