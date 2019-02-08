package world.gregs.hestia.social.network.worlds.out

import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.codec.Encoder
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.social.network.worlds.WorldPackets.SEND_CLIENT_PACKET

class SendClientPacket(entityId: Int, builder: Packet.Builder) : Packet.Builder(SEND_CLIENT_PACKET, Packet.Type.VAR_SHORT) {
    init {
        val packet = builder.build()
        writeInt(entityId)
        packet.resetReader()
        val encoded = Encoder.encode(packet)
        writeBytes(encoded)
    }
}

fun Session.send(entityId: Int, builder: Packet.Builder) {
    write(SendClientPacket(entityId, builder))
}