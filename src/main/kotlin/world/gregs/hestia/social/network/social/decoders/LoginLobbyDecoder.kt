package world.gregs.hestia.social.network.social.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.LOBBY_LOGIN
import world.gregs.hestia.core.network.protocol.LoginDecoder
import world.gregs.hestia.social.network.social.decoders.messages.LoginLobby

class LoginLobbyDecoder : MessageDecoder<LoginLobby>(Packet.Type.VAR_SHORT, LOBBY_LOGIN) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): LoginLobby? {
        val triple = LoginDecoder.decode(ctx, packet) ?: return null
        val password = triple.first
        val serverSeed = triple.second
        val clientSeed = triple.third

        val username = packet.readString()
        val highDefinition = packet.readBoolean()
        val resizeable = packet.readBoolean()
        packet.skip(24)//TODO
        val settings = packet.readString()
        val affiliate = packet.readInt()
        /*val crcValues = IntArray(35)
        for (i in crcValues.indices) {
            crcValues[i] = packet.readInt()
        }*/
        return LoginLobby(username, password, highDefinition, resizeable, settings, affiliate, serverSeed, clientSeed)
    }

}