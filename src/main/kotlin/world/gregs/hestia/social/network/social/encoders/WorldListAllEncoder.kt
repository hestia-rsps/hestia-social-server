package world.gregs.hestia.social.network.social.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.WORLD_LIST
import world.gregs.hestia.social.network.social.encoders.messages.WorldListAll

class WorldListAllEncoder : MessageEncoder<WorldListAll>() {

    override fun encode(builder: PacketBuilder, message: WorldListAll) {
        val (worlds, worldConfiguration, worldStatus) = message
        builder.apply {
            writeOpcode(WORLD_LIST, Packet.Type.VAR_SHORT)
            writeByte(1)
            writeByte(if(worldConfiguration) 2 else 0)
            writeByte(worldStatus)
            val list = worlds.worlds
            if (worldConfiguration) {
                writeSmart(list.size)
                list.forEach {
                    writeSmart(it.country)
                    writeByte(0)//For string below
                    writeString(it.region)
                }
                writeSmart(0)
                writeSmart(list.size + 1)
                writeSmart(list.size)
                list.forEachIndexed { index, world ->
                    writeSmart(world.id)
                    writeByte(index)//Country/region index
                    writeInt(world.flag)
                    writeByte(0)//For string below
                    writeString(world.activity)
                    writeByte(0)//For string below
                    writeString(world.ip)
                }
                writeInt(0)//Passed back in ping packet - latency
            }
            if (worldStatus) {
                list.forEach {
                    writeSmart(it.id)
                    writeShort(it.size)
                }
            }
        }
    }

}