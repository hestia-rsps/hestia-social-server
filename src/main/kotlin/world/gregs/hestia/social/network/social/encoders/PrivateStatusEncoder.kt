package world.gregs.hestia.social.network.social.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.PRIVATE_STATUS
import world.gregs.hestia.social.network.social.encoders.messages.PrivateStatus

class PrivateStatusEncoder : MessageEncoder<PrivateStatus>() {

    override fun encode(builder: PacketBuilder, message: PrivateStatus) {
        builder.apply {
            writeOpcode(PRIVATE_STATUS)
            writeByte(message.status)
        }
    }

}