package world.gregs.hestia.social.network.social.handlers

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.social.api.Player
import world.gregs.hestia.social.core.World
import world.gregs.hestia.social.network.PlayerMessageHandler
import world.gregs.hestia.social.network.social.decoders.messages.IgnoreListAdd

class IgnoreListAddHandler : PlayerMessageHandler<IgnoreListAdd>() {

    override fun handle(ctx: ChannelHandlerContext, player: Player, message: IgnoreListAdd) {
        val name = World.names.getName(message.name)
        if(name == null) {
            player.message("Could not find player with the username '${message.name}'.")
            return
        }
        World.contacts.addIgnore(player, name, message.temporary)
    }

}