package world.gregs.hestia.social.network.social.out

import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.social.core.social.FriendsChatChannel.Companion.writeRand
import world.gregs.hestia.social.core.social.FriendsChatChannel.Companion.writeUsername
import world.gregs.hestia.social.network.Huffman

class PrivateMessageReceive(name: String, rights: Int, message: String) : Packet.Builder(120, Packet.Type.VAR_SHORT) {
    init {
        writeUsername(this, name)
        writeRand(this)
        writeByte(rights)
        Huffman.compress(message, this)
    }
}