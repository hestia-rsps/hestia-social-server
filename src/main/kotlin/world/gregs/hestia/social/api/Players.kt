package world.gregs.hestia.social.api

import world.gregs.hestia.core.network.Session

interface Players {
    /**
     * Immutable list of players across all worlds & lobby
     */
    val all: List<Player>

    /**
     * Adds a player to the online list
     * @param player The player to connect
     * @return Successfully connected
     */
    fun add(player: Player): Boolean

    /**
     * Removes a player from the online list
     * @param player The player to disconnect
     * @return Successfully disconnected
     */
    fun remove(player: Player): Boolean

    /**
     * Find a player by username if they're online
     * Note: use [get] [Name] when possible
     * @param name The username to search for
     * @return The player if found
     */
    fun get(name: String): Player?

    /**
     * Find a player by username if they're online
     * @param name The username to search for
     * @return The player if found
     */
    fun get(name: Name?): Player?

    /**
     * Find a player by lobby session if they're in the lobby
     * @param session The lobby session to find
     * @return The player if found
     */
    fun get(session: Session): Player?

    /**
     * Find a player by entity and world if they're in-game
     * @param entityId The entity id to find
     * @param world The world to look in
     * @return The player if found
     */
    fun get(entityId: Int, world: Int): Player?
}