package world.gregs.hestia.social.api

interface OnlineStatus : Connection {

    /**
     * Changes a players online status
     * @param player The player who's removing changing status
     * @param status The new status value (0 = on, 1 = friends, 2 = private)
     */
    fun setStatus(player: Player, status: Int)

    companion object {
        const val ON = 0
        const val FRIENDS = 1
        const val PRIVATE = 2
    }
}