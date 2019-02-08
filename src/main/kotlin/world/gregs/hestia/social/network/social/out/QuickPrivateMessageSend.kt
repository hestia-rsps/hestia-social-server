package world.gregs.hestia.social.network.social.out

import world.gregs.hestia.core.network.packets.Packet

class QuickPrivateMessageSend(username: String, fileId: Int, data: ByteArray?) : Packet.Builder(97, Packet.Type.VAR_BYTE) {
    init {
        writeString(username)
        writeShort(fileId)
        if(data != null) {
            writeBytes(data)
        }
    }
}