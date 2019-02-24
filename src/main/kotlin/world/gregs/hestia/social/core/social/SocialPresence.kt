package world.gregs.hestia.social.core.social

import world.gregs.hestia.core.network.protocol.encoders.messages.FriendListUnlock
import world.gregs.hestia.social.api.Connection
import world.gregs.hestia.social.api.Player
import world.gregs.hestia.social.api.Players
import world.gregs.hestia.social.network.social.encoders.messages.IgnoreListUnlock

class SocialPresence(private val players: Players) : Connection {

    /**
     * Sends a connecting player their friends and ignores
     * @param player The player connecting
     */
    override fun connect(player: Player) {
        //Unlock list if empty (if not empty it get's unlocked anyway)
        if (player.relations.friendCount() == 0) {
            player.send(FriendListUnlock())
        } else {
            //Send friends
            player.sendFriends(players)
        }
        /*
        TODO - Unlocks aren't needed (they only work because when client reads size it defaults to 0 if out of buffer bounds)
        Shouldn't sendFriends send all in one go rather than one at a time?
         */
        if(player.relations.ignoreCount() == 0) {
            player.send(IgnoreListUnlock())
        } else {
            //Send ignores
            player.sendIgnores()
        }
    }

    override fun disconnect(player: Player) {
    }
}