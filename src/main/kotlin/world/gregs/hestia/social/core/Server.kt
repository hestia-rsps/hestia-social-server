package world.gregs.hestia.social.core

import org.slf4j.LoggerFactory
import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.packet.PacketWriter
import world.gregs.hestia.core.network.protocol.Details
import world.gregs.hestia.social.api.Worlds
import world.gregs.hestia.social.model.WorldList
import world.gregs.hestia.social.network.social.encoders.WorldListAllEncoder
import world.gregs.hestia.social.network.social.encoders.messages.WorldListAll

object Server {

    /*
        This is static for now
        Perhaps in the future make it an instance & listen on an event bus
     */
    val sessions = HashMap<Int, Session>()
    val worlds: Worlds = WorldList()
    private val encoder = WorldListAllEncoder()
    lateinit var config: ByteArray
    lateinit var status: ByteArray
    lateinit var empty: ByteArray
    private val logger = LoggerFactory.getLogger(Server::class.java)

    init {
        updateLists()
    }

    fun add(info: Details): Int {
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

    fun set(id: Int, info: Details) {
        worlds.set(id, info)
        logger.info("World $id re-registered")
        updateLists()
    }

    private fun updateLists() {
        //TODO some kind of tracking system so only sends config if details have changed
        var builder = PacketWriter()
        encoder.encode(builder, WorldListAll(worlds, configuration = false, status = false))
        empty = builder.build().buffer.array()
        builder = PacketWriter()
        encoder.encode(builder, WorldListAll(worlds, configuration = true, status = true))
        config = builder.build().buffer.array()
        builder = PacketWriter()
        encoder.encode(builder, WorldListAll(worlds, configuration = false, status = true))
        status = builder.build().buffer.array()
    }
}