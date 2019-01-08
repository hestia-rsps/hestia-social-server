package world.gregs.hestia.social.network.social.out

import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.services.formatName
import world.gregs.hestia.social.api.Name

class SendIgnoreList(ignoreList: List<Name>) : Packet.Builder(57, Packet.Type.VAR_SHORT) {
    init {
        writeByte(ignoreList.size)
        ignoreList.forEach {
            writeIgnore(it.name, it.name.formatName(), it.previousName, it.previousName.formatName())
        }
    }

    private fun writeIgnore(name: String, nameFormatted: String, previous: String, previousFormatted: String) {
        writeString(name)//Current name
        writeString(if(name == nameFormatted) "" else nameFormatted)//Current formatted
        writeString(previous)//Previous name
        writeString(if(previous == previousFormatted) "" else previousFormatted)//Previous formatted
    }
}