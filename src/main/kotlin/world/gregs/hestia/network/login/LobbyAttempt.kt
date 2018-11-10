package world.gregs.hestia.network.login

import world.gregs.hestia.network.Session
import world.gregs.hestia.network.codec.inbound.InboundHandler
import world.gregs.hestia.network.login.`in`.LoginLobby
import world.gregs.hestia.network.packets.InboundPacket
import world.gregs.hestia.network.packets.Packet

class LobbyAttempt(private val lobby: InboundHandler) : LoginRequestListener {
    override fun login(session: Session, handler: InboundPacket, packet: Packet, password: String, serverSeed: Long, clientSeed: Long) {
        handler.read(session, packet, packet.readableBytes())
        if (handler is LoginLobby) {
            session.channel?.pipeline()?.replace(LoginHandshake::class.java, "decoder", lobby)
        }
    }
}