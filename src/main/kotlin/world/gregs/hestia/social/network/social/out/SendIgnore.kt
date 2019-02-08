package world.gregs.hestia.social.network.social.out

import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.services.formatName

class SendIgnore(name: String, previous: String, renamed: Boolean, unknown: Boolean) : Packet.Builder(105, Packet.Type.VAR_BYTE) {
    init {
        var settings = 0
        if(renamed) {
            settings = settings or 0x1
        }
        if(unknown) {//Unknown
            settings = settings or 0x2
        }
        writeByte(settings)
        writeString(name)
        writeString(if(name == name.formatName()) "" else name.formatName())
        writeString(previous)
        writeString(if(previous == previous.formatName()) "" else previous.formatName())
    }
}