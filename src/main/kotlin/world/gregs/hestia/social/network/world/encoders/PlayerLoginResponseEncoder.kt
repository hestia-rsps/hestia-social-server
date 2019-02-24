package world.gregs.hestia.social.network.world.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.WorldOpcodes.LOGIN_RESPONSE
import world.gregs.hestia.core.network.protocol.messages.PlayerLoginResponse

class PlayerLoginResponseEncoder : MessageEncoder<PlayerLoginResponse>() {

    override fun encode(builder: PacketBuilder, message: PlayerLoginResponse) {
        val (session, response) = message
        builder.apply {
            writeOpcode(LOGIN_RESPONSE)
            writeShort(session)
            writeByte(response)
        }
    }

}