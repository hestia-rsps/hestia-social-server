package world.gregs.hestia.social.network.world.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.WorldOpcodes.LOGIN_SUCCESS
import world.gregs.hestia.core.network.protocol.messages.PlayerLoginSuccess

class PlayerLoginSuccessEncoder : MessageEncoder<PlayerLoginSuccess>() {

    override fun encode(builder: PacketBuilder, message: PlayerLoginSuccess) {
        val (session, name, isaac, mode, width, height) = message
        builder.apply {
            writeOpcode(LOGIN_SUCCESS, Packet.Type.VAR_BYTE)
            writeShort(session)
            writeString(name)
            writeByte(isaac.size)
            isaac.forEach {
                writeInt(it)
            }
            writeByte(mode)
            writeShort(width)
            writeShort(height)
        }
    }

}