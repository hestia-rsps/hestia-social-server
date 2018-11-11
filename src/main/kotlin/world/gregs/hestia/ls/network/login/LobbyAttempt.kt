package world.gregs.hestia.ls.network.login

import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.codec.inbound.InboundHandler
import world.gregs.hestia.core.network.login.LoginHandshake
import world.gregs.hestia.core.network.login.LoginRequestListener
import world.gregs.hestia.core.network.packets.InboundPacket
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.ls.network.login.`in`.LoginLobby

class LobbyAttempt(private val lobby: InboundHandler) : LoginRequestListener {
    override fun login(session: Session, handler: InboundPacket, packet: Packet, password: String, serverSeed: Long, clientSeed: Long) {
        handler.read(session, packet, packet.readableBytes())
        if (handler is LoginLobby) {
            session.channel?.pipeline()?.replace(LoginHandshake::class.java, "decoder", lobby)
        }
    }
}