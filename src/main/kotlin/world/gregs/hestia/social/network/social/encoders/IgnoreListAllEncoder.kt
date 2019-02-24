package world.gregs.hestia.social.network.social.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.IGNORE_LIST
import world.gregs.hestia.core.services.formatName
import world.gregs.hestia.social.network.social.encoders.messages.IgnoreListAll

class IgnoreListAllEncoder : MessageEncoder<IgnoreListAll>() {

    override fun encode(builder: PacketBuilder, message: IgnoreListAll) {
        val (names) = message
        builder.apply {
           writeOpcode(IGNORE_LIST, Packet.Type.VAR_SHORT)
            writeByte(names.size)
            names.forEach {
                writeIgnore(it.name, it.name.formatName(), it.previousName, it.previousName.formatName())
            }
        }
    }

    private fun PacketBuilder.writeIgnore(name: String, nameFormatted: String, previous: String, previousFormatted: String) {
        writeString(name)
        writeString(if(name == nameFormatted) "" else nameFormatted)//Current formatted
        writeString(previous)//Previous name
        writeString(if(previous == previousFormatted) "" else previousFormatted)//Previous formatted
    }

}