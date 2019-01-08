package world.gregs.hestia.social.api

interface Lobby : Connection {

    /**
     * Players already in-game reconnecting to the (recently restarted) social server
     * @param player The player to re-login
     * @param world The world id that the player is on
     */
    fun reconnect(player: Player, world: Int)

    /**
     * Disconnecting from the lobby and connecting to the game-server (login)
     * @param player The player to login
     */
    fun login(player: Player)

    /**
     * Connecting to the lobby after disconnecting from the game-server (logout)
     * @param player The player to log out
     */
    fun logout(player: Player)

}