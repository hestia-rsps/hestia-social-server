package world.gregs.hestia.social.network.social.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.IGNORE_LIST
import world.gregs.hestia.social.network.social.encoders.messages.IgnoreListUnlock

class IgnoreListUnlockEncoder : MessageEncoder<IgnoreListUnlock>() {

    override fun encode(builder: PacketBuilder, message: IgnoreListUnlock) {
        builder.apply {
            writeOpcode(IGNORE_LIST, Packet.Type.VAR_SHORT)
            writeByte(0)
        }
    }

}