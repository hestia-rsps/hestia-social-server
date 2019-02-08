package world.gregs.hestia.social.network.social.out

import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.social.core.social.FriendsChatChannel.Companion.writeRand
import world.gregs.hestia.social.core.social.FriendsChatChannel.Companion.writeUsername

class QuickPrivateMessageReceive(name: String, rights: Int, fileId: Int, data: ByteArray?) : Packet.Builder(42, Packet.Type.VAR_BYTE) {
    init {
        writeUsername(this, name)
        writeRand(this)
        writeByte(rights)
        writeShort(fileId)
        if(data != null) {
            writeBytes(data)
        }
    }
}