package world.gregs.hestia.social.network.social.handlers

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.social.api.OnlineStatus
import world.gregs.hestia.social.api.Player
import world.gregs.hestia.social.core.World
import world.gregs.hestia.social.network.PlayerMessageHandler
import world.gregs.hestia.social.network.social.decoders.messages.MessagePrivate
import world.gregs.hestia.social.network.social.encoders.messages.ChatPrivateFrom
import world.gregs.hestia.social.network.social.encoders.messages.ChatPrivateTo

class MessagePrivateHandler : PlayerMessageHandler<MessagePrivate>() {

    override fun handle(ctx: ChannelHandlerContext, player: Player, message: MessagePrivate) {
        val name = World.names.getName(message.name)
        if(name == null) {
            player.message("Could not find player with the username '${message.name}'.")
            return
        }
        val friend = World.players.get(name) ?: return
        val text = message.message.capitalize()
        if(player.status == OnlineStatus.PRIVATE) {
            World.contacts.setStatus(player, OnlineStatus.FRIENDS)
        }
        player.send(ChatPrivateTo(name.name, text))
        friend.send(ChatPrivateFrom(player.names.name, player.rights, text))
    }

}