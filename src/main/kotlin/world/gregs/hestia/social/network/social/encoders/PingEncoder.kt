package world.gregs.hestia.social.network.social.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.CLIENT_PING
import world.gregs.hestia.core.network.protocol.decoders.messages.Ping

class PingEncoder : MessageEncoder<Ping>() {

    override fun encode(builder: PacketBuilder, message: Ping) {
        builder.writeOpcode(CLIENT_PING)
    }

}