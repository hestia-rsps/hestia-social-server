package world.gregs.hestia.social.network.social

import io.netty.channel.ChannelHandler
import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.codec.inbound.ConnectionListener
import world.gregs.hestia.social.core.World

@ChannelHandler.Sharable
class SocialConnections : ConnectionListener() {

    override fun connect(session: Session) {
    }

    override fun disconnect(session: Session) {
        val player = World.players.get(session) ?: return
        if(player.session != null) {
            World.disconnect(player)
        }
    }

}