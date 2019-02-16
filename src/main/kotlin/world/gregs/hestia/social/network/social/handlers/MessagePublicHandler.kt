package world.gregs.hestia.social.network.social.handlers

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.social.api.Player
import world.gregs.hestia.social.core.World
import world.gregs.hestia.social.network.PlayerMessageHandler
import world.gregs.hestia.social.network.social.decoders.messages.MessagePublic
import world.gregs.hestia.social.network.social.encoders.messages.ChatPublic

class MessagePublicHandler : PlayerMessageHandler<MessagePublic>() {

    override fun handle(ctx: ChannelHandlerContext, player: Player, message: MessagePublic) {
        when(player.messenger.messageType) {
            0 -> {//Public
                World.players.all.forEach {
                    if(player.index != null) {
                        it.send(ChatPublic(player.index!!, message.effects, player.rights, message.message))
                    }
                }
            }
            1 -> {//Friends chat
                player.channel?.message(player, message.message)
            }
        }
    }

}