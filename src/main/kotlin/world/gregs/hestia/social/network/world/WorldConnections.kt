package world.gregs.hestia.social.network.world

import io.netty.channel.ChannelHandler
import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.codec.ConnectionSessionListener
import world.gregs.hestia.social.core.Server
import world.gregs.hestia.social.core.World

@ChannelHandler.Sharable
class WorldConnections : ConnectionSessionListener() {
    override fun connect(session: Session) {
    }

    override fun disconnect(session: Session) {
        World.players.all.filter { it.worldId == session.id }.forEach {
            World.disconnect(it)
        }
        Server.remove(session.id)
    }

}