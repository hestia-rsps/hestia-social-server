package world.gregs.hestia.social.network.social.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.client.Response
import world.gregs.hestia.core.network.clientRespond
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.LOBBY_LOGIN
import world.gregs.hestia.social.network.LoginDecoder
import world.gregs.hestia.social.network.social.decoders.messages.LoginLobby

class LoginLobbyDecoder : MessageDecoder<LoginLobby>(Packet.Type.VAR_SHORT, LOBBY_LOGIN) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): LoginLobby? {
        val triple = LoginDecoder.decode(packet)
        if(triple.first != Response.NORMAL) {
            ctx.clientRespond(triple.first)
            return null
        }
        val password = triple.second!!
        val isaacKeys = triple.third!!

        val username = packet.readString()
        val highDefinition = packet.readBoolean()
        val resizeable = packet.readBoolean()
        packet.skip(24)//TODO
        val settings = packet.readString()
        val affiliate = packet.readInt()
        return LoginLobby(username, password, highDefinition, resizeable, settings, affiliate, isaacKeys)
    }

}