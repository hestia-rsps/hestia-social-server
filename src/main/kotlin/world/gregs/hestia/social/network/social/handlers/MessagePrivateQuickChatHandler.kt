package world.gregs.hestia.social.network.social.handlers

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.social.api.OnlineStatus
import world.gregs.hestia.social.api.Player
import world.gregs.hestia.social.core.World
import world.gregs.hestia.social.network.PlayerMessageHandler
import world.gregs.hestia.social.network.social.decoders.messages.MessagePrivateQuickChat
import world.gregs.hestia.social.network.social.encoders.messages.ChatPrivateQuickChatFrom
import world.gregs.hestia.social.network.social.encoders.messages.ChatPrivateQuickChatTo

class MessagePrivateQuickChatHandler : PlayerMessageHandler<MessagePrivateQuickChat>() {

    override fun handle(ctx: ChannelHandlerContext, player: Player, message: MessagePrivateQuickChat) {
        val (username, file, data) = message
        val name = World.names.getName(username)
        if(name == null) {
            player.message("Could not find player with the username '$username'.")
            return
        }
        val friend = World.players.get(name) ?: return
        if(player.status == OnlineStatus.PRIVATE) {
            World.contacts.setStatus(player, OnlineStatus.FRIENDS)
        }
        player.send(ChatPrivateQuickChatTo(name.name, file, data))
        friend.send(ChatPrivateQuickChatFrom(player.names.name, player.rights, file, data))
    }

}