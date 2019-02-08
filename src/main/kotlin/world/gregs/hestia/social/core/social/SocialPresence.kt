package world.gregs.hestia.social.core.social

import world.gregs.hestia.social.api.Connection
import world.gregs.hestia.social.api.Player
import world.gregs.hestia.social.api.Players
import world.gregs.hestia.social.network.social.out.UnlockFriendList

class SocialPresence(private val players: Players) : Connection {

    /**
     * Sends a connecting player their friends and ignores
     * @param player The player connecting
     */
    override fun connect(player: Player) {
        //Unlock list if empty (if not empty it get's unlocked anyway)
        if (player.relations.friendCount() == 0) {
            player.send(UnlockFriendList())
        } else {
            //Send friends
            player.sendFriends(players)
        }
        //Send ignores
        player.sendIgnores()
    }

    override fun disconnect(player: Player) {
    }
}