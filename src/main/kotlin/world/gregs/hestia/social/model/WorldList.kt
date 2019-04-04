package world.gregs.hestia.social.model

import world.gregs.hestia.core.network.protocol.Details
import world.gregs.hestia.social.api.Worlds

class WorldList : Worlds {
    override val worlds = ArrayList<Details>()

    override fun add(info: Details): Int {
        info.id = getId()
        worlds.add(info)
        return info.id
    }

    override fun remove(id: Int) {
        worlds.removeIf { it.id == id }
    }

    override fun set(id: Int, info: Details) {
        info.id = id
        worlds.add(info)
    }

    private fun getId(): Int {
        val ids = worlds.map { it.id }
        for(i in 1 until Int.MAX_VALUE) {
            if(!ids.contains(i)) {
                return i
            }
        }
        return -1
    }
}