package world.gregs.hestia.social.core

import org.slf4j.LoggerFactory
import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.packets.InboundPacket
import world.gregs.hestia.core.services.load.PacketMap
import world.gregs.hestia.core.world.WorldDetails
import world.gregs.hestia.social.api.Worlds
import world.gregs.hestia.social.model.WorldList
import world.gregs.hestia.social.network.social.out.WorldListPacket

object Server {

    /*
        This is static for now
        Perhaps in the future make it an instance & listen on an event bus
     */
    val sessions = HashMap<Int, Session>()
    private val worlds: Worlds = WorldList()
    lateinit var config: ByteArray
    lateinit var status: ByteArray
    lateinit var empty: ByteArray
    lateinit var packets: PacketMap<InboundPacket>
    private val logger = LoggerFactory.getLogger(Server::class.java)

    init {
        updateLists()
    }

    fun add(info: WorldDetails): Int {
        val id = worlds.add(info)
        logger.info("World $id registered")
        updateLists()
        return id
    }

    fun remove(id: Int) {
        worlds.remove(id)
        logger.info("World $id de-registered")
        updateLists()
    }

    fun set(id: Int, info: WorldDetails) {
        worlds.set(id, info)
        logger.info("World $id re-registered")
        updateLists()
    }

    private fun updateLists() {
        //TODO some kind of tracking system so only sends config if details have changed
        empty = WorldListPacket(worlds, worldConfiguration = false, worldStatus = false).buffer.array()
        config = WorldListPacket(worlds, worldConfiguration = true, worldStatus = true).buffer.array()
        status = WorldListPacket(worlds, worldConfiguration = false, worldStatus = true).buffer.array()
    }
}