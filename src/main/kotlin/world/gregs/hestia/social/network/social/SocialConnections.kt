package world.gregs.hestia.social.network.social

import io.netty.channel.ChannelHandler
import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.codec.ConnectionSessionListener
import world.gregs.hestia.social.core.World

@ChannelHandler.Sharable
class SocialConnections : ConnectionSessionListener() {

    override fun connect(session: Session) {
    }

    override fun disconnect(session: Session) {
        println("Disconnect $session ${World.players.get(session)} ${World.players.get(session)?.session}")
        val player = World.players.get(session) ?: return
        if(player.session != null) {
            World.disconnect(player)
        }
    }

}