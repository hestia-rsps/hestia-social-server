package world.gregs.hestia.social.core.social

import world.gregs.hestia.social.api.OnlineStatus
import world.gregs.hestia.social.api.OnlineStatus.Companion.FRIENDS
import world.gregs.hestia.social.api.OnlineStatus.Companion.ON
import world.gregs.hestia.social.api.OnlineStatus.Companion.PRIVATE
import world.gregs.hestia.social.api.Player
import world.gregs.hestia.social.api.Players
import world.gregs.hestia.social.network.social.out.PrivateStatus

class SocialStatus(private val players: Players) : OnlineStatus {

    override fun connect(player: Player) {
        setStatus(player, 0)//TODO saving
    }

    override fun setStatus(player: Player, status: Int) {
        if(!player.online) {
            player.status = status
            return
        }

        player.send(PrivateStatus(status))

        val old = player.status
        if (old != status) {
            player.status = status
            players.all
                    .filter { it != player && it.friends(player) && if (old == FRIENDS && status == ON || old == ON && status == FRIENDS) !player.friends(it) else if (old == PRIVATE && status == FRIENDS) player.friends(it) else true }
                    .forEach {
                        it.sendFriend(player.names, players.get(player.names))
                    }
        }
    }

    override fun disconnect(player: Player) {
    }
}