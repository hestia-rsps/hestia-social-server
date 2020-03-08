package world.gregs.hestia.social.network.social.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.IGNORE_LIST_UPDATE
import world.gregs.hestia.social.network.social.encoders.messages.IgnoreListUpdate
import world.gregs.hestia.core.services.formatName

class IgnoreListUpdateEncoder : MessageEncoder<IgnoreListUpdate>() {

    override fun encode(builder: PacketBuilder, message: IgnoreListUpdate) {
        val (name, previous, renamed, unknown) = message
        var settings = 0
        if(renamed) {
            settings = settings or 0x1
        }
        if(unknown) {//Unknown
            settings = settings or 0x2
        }
        builder.apply {
            writeOpcode(IGNORE_LIST_UPDATE, Packet.Type.VAR_BYTE)
            writeByte(settings)
            writeString(name)
            writeString(if(name == name.formatName()) "" else name.formatName())
            writeString(previous)
            writeString(if(previous == previous.formatName()) "" else previous.formatName())
        }
    }

}