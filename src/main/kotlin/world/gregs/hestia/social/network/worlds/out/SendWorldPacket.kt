package world.gregs.hestia.social.network.worlds.out

import world.gregs.hestia.core.network.codec.Encoder
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.social.network.worlds.WorldPackets.WORLD_PACKET

class SendWorldPacket(entityId: Int, packet: Packet) : Packet.Builder(WORLD_PACKET, Packet.Type.VAR_SHORT) {
    constructor(entityId: Int, builder: Packet.Builder) : this(entityId, builder.build())

    init {
        writeInt(entityId)
        packet.resetReader()
        val encoded = Encoder.encode(packet)
        writeBytes(encoded)
    }
}