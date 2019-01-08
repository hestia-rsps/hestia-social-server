package world.gregs.hestia.social.api.worlds

import world.gregs.hestia.core.world.Details

interface Worlds {
    /**
     * List of all connected game-server worlds
     */
    val worlds: List<Details>

    /**
     * Adds details of a game-server world to the worlds list
     * @param info The world to add
     * @return The worlds generated id [Details.id]
     */
    fun add(info: Details): Int

    /**
     * Removes a game-server world from the worlds list
     * @param id The id of the world to remove
     */
    fun remove(id: Int)

    /**
     * Adds a world with an existing id
     * @param id The id of the world
     * @param info The details of the world to add
     */
    fun set(id: Int, info:  Details)
}