package world.gregs.hestia.social.core.player

import world.gregs.hestia.core.network.Session
import world.gregs.hestia.social.api.Name
import world.gregs.hestia.social.api.Player
import world.gregs.hestia.social.api.Players
import world.gregs.hestia.social.core.World

open class PlayersImpl : Players {

    override val all = ArrayList<Player>()

    override fun add(player: Player): Boolean {
        val success = !all.contains(player) && all.add(player)
        if (success) {
            player.worldId = 0
        }
        return success
    }

    override fun remove(player: Player): Boolean {
        val success = all.remove(player)
        if (success) {
            player.worldId = -1
        }
        return success
    }

    override fun get(name: String): Player? {
        return get(World.names.getName(name) ?: return null)
    }

    override fun get(name: Name?): Player? {
        return if(name == null) null else all.firstOrNull { it.names == name }
    }

    override fun get(session: Session): Player? {
        return all.firstOrNull { it.session == session }
    }

    override fun get(entityId: Int, world: Int): Player? {
        return all.firstOrNull { it.entityId != null && it.entityId == entityId && it.worldId == world }
    }
}