package world.gregs.hestia.social.network.social.out

import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.social.network.Huffman

class PrivateMessageSend(username: String, message: String) : Packet.Builder(77, Packet.Type.VAR_SHORT) {
    init {
        writeString(username)
        Huffman.compress(message, this)
    }
}