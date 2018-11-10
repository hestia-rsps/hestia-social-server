package world.gregs.hestia.network.worlds

import world.gregs.hestia.WorldDetails

class WorldList {
    private val worlds = ArrayList<WorldDetails>()

    fun add(info: WorldDetails): Int {
        info.id = getId()
        worlds.add(info)
        return info.id
    }

    fun remove(id: Int) {
        worlds.removeIf { it.id == id }
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

    fun set(id: Int, info: WorldDetails) {
        info.id = id
        worlds.add(info)
    }

    fun get(): List<WorldDetails> {
        return worlds
    }
}