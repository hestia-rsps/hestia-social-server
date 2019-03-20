package world.gregs.hestia.social.network.social.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.LOBBY_LOGIN
import world.gregs.hestia.social.network.LoginDecoder
import world.gregs.hestia.social.network.social.decoders.messages.LoginLobby

class LoginLobbyDecoder : MessageDecoder<LoginLobby>(Packet.Type.VAR_SHORT, LOBBY_LOGIN) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): LoginLobby? {
        val pair = LoginDecoder.decode(ctx, packet) ?: return null
        val password = pair.first
        val isaacKeys = pair.second

        val username = packet.readString()
        val highDefinition = packet.readBoolean()
        val resizeable = packet.readBoolean()
        packet.skip(24)//TODO
        val settings = packet.readString()
        val affiliate = packet.readInt()
        return LoginLobby(username, password, highDefinition, resizeable, settings, affiliate, isaacKeys)
    }

}