package world.gregs.hestia.social.network.social.out

import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.social.network.Huffman

class PublicChatMessage(clientIndex: Int, effects: Int, rights: Int, message: String?, fileId: Int? = null, data: ByteArray? = null) : Packet.Builder(91, Packet.Type.VAR_BYTE) {
    init {
        writeShort(clientIndex)//Player index
        writeShort(effects)//Effects
        writeByte(rights)
        if(message != null) {
            Huffman.compress(message, this)
        } else if(fileId != null) {
            writeShort(fileId)
            if(data != null) {
                writeBytes(data)
            }
        }
    }
}