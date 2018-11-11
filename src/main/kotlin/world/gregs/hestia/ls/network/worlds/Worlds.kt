package world.gregs.hestia.ls.network.worlds

import org.slf4j.LoggerFactory
import world.gregs.hestia.core.WorldDetails
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.services.load.PacketMap
import world.gregs.hestia.ls.network.worlds.out.WorldListPacket

object Worlds {

    /*
        This is static for now
        Perhaps in the future make it an instance & listen on an event bus
     */
    private val worlds = WorldList()
    private lateinit var packet: Packet
    lateinit var packets: PacketMap
    private val logger = LoggerFactory.getLogger(Worlds::class.java)

    init {
        updateList()
    }

    fun add(info: WorldDetails): Int {
        val id = worlds.add(info)
        logger.info("World $id registered")
        updateList()
        return id
    }

    fun remove(id: Int) {
        worlds.remove(id)
        logger.info("World $id deregistered")
        updateList()
    }

    fun set(id: Int, info: WorldDetails) {
        worlds.set(id, info)
        logger.info("World $id re-registered")
        updateList()
    }

    fun get(): List<WorldDetails> {
        return worlds.get()
    }

    private fun updateList() {
        packet = WorldListPacket(worlds).build()
    }

    fun getWorldListPacket(): Packet {
        packet.buffer.resetReaderIndex()
        return packet
    }
}