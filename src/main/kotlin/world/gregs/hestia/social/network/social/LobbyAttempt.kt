package world.gregs.hestia.social.network.social

import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.codec.decode.PacketDecoder
import world.gregs.hestia.core.network.codec.inbound.ConnectionListener
import world.gregs.hestia.core.network.codec.inbound.packet.PacketInboundHandler
import world.gregs.hestia.core.network.login.LoginHandshake
import world.gregs.hestia.core.network.login.LoginRequestListener
import world.gregs.hestia.core.network.packets.InboundPacket
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.social.network.social.`in`.LoginLobby
import kotlin.reflect.KClass

class LobbyAttempt(private val decoder: KClass<out PacketDecoder>, private val lobbyHandler: PacketInboundHandler<InboundPacket>, private val connectionListener: ConnectionListener) : LoginRequestListener {
    override fun login(session: Session, handler: InboundPacket, packet: Packet, password: String, serverSeed: Long, clientSeed: Long) {
        handler.read(session, packet)
        if (handler is LoginLobby) {
            session.channel?.pipeline()?.replace(LoginHandshake::class.java, "handler", lobbyHandler)
            session.channel?.pipeline()?.addBefore("handler", "decoder", this.decoder.java.getDeclaredConstructor().newInstance())
            session.channel?.pipeline()?.addAfter("decoder", "connection", connectionListener)
        }
    }
}