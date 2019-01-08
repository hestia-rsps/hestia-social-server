package world.gregs.hestia.social.api

interface Connection {

    /**
     * Connecting to the lobby
     * @param player The player to connect
     */
    fun connect(player: Player)

    /**
     * Disconnecting from the lobby
     * @param player The player to disconnect
     */
    fun disconnect(player: Player)

}