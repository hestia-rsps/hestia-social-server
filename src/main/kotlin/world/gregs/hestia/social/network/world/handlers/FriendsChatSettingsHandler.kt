package world.gregs.hestia.social.network.world.handlers

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageHandler
import world.gregs.hestia.core.network.getSession
import world.gregs.hestia.core.network.protocol.encoders.messages.InterfaceComponentText
import world.gregs.hestia.core.network.protocol.messages.FriendsChatSettings
import world.gregs.hestia.social.api.FriendsChat
import world.gregs.hestia.social.core.World

class FriendsChatSettingsHandler : MessageHandler<FriendsChatSettings> {

    override fun handle(ctx: ChannelHandlerContext, message: FriendsChatSettings) {
        val (entity, interfaceHash, option) = message
        val id = interfaceHash shr 16
        val component = interfaceHash - (id shl 16)
        val world = ctx.getSession().id

        //Find the player
        val player = World.players.get(entity, world) ?: return

        //Players channel
        val channel = World.channels?.get(player) ?: return

        when(component) {
            0 -> {//Open interface
                //TODO what will admin do about ranks as it's based on the local client friends list?
                player.send(InterfaceComponentText(id, 22, channel.channelName ?: "Chat disabled"))
                player.send(InterfaceComponentText(id, 23, channel.joinRank.string))
                player.send(InterfaceComponentText(id, 24, channel.talkRank.string))
                player.send(InterfaceComponentText(id, 25, channel.kickRank.string))
                player.send(InterfaceComponentText(id, 26, channel.lootRank.string))
                //TODO send coinShare
            }
            33 -> {//Toggle coin share
                channel.setShare(player, !channel.coinShare)
            }
            else -> {//Rank settings
                val rank = if(component == 26 && option == 1) FriendsChat.Ranks.NO_ONE else FriendsChat.Ranks.values().firstOrNull { it.ordinal == option } ?: return

                val change = when(component) {
                    26 -> channel.setLoot(player, rank)
                    25 -> channel.setKick(player, rank)
                    24 -> channel.setTalk(player, rank)
                    23 -> channel.setJoin(player, rank)
                    else -> return
                }

                if(change != null) {
                    player.send(InterfaceComponentText(id, component, change.string))
                }
            }
        }
    }

}