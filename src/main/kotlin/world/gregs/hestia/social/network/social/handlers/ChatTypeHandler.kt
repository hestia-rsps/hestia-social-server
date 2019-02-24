package world.gregs.hestia.social.network.social.handlers

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.social.api.Player
import world.gregs.hestia.social.network.PlayerMessageHandler
import world.gregs.hestia.social.network.social.decoders.messages.ChatType

class ChatTypeHandler : PlayerMessageHandler<ChatType>() {
    override fun handle(ctx: ChannelHandlerContext, player: Player, message: ChatType) {
        player.messenger.messageType = message.type
    }

}