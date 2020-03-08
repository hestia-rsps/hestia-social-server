package world.gregs.hestia.social.network.social.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.WORLD_LIST
import world.gregs.hestia.social.network.social.encoders.messages.WorldListUpdate

class WorldListUpdateEncoder : MessageEncoder<WorldListUpdate>() {

    override fun encode(builder: PacketBuilder, message: WorldListUpdate) {
        builder.apply {
            writeOpcode(WORLD_LIST, Packet.Type.VAR_SHORT)
            writeBytes(message.data)
        }
    }

}