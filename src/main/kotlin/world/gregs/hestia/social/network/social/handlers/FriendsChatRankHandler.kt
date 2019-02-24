package world.gregs.hestia.social.network.social.handlers

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.social.api.Player
import world.gregs.hestia.social.core.World
import world.gregs.hestia.social.network.PlayerMessageHandler
import world.gregs.hestia.social.network.social.decoders.messages.FriendsChatRank

class FriendsChatRankHandler : PlayerMessageHandler<FriendsChatRank>() {

    override fun handle(ctx: ChannelHandlerContext, player: Player, message: FriendsChatRank) {
        val name = World.names.getName(message.name)
        if(name == null) {
            player.message("Could not find player with the username '${message.name}'.")
            return
        }
        World.channels?.promote(player, name, message.rank)
    }

}