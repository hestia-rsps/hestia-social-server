package world.gregs.hestia.social.network.worlds.`in`

import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.packets.InboundPacket
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.network.packets.PacketInfo
import world.gregs.hestia.social.api.FriendsChat
import world.gregs.hestia.social.core.World
import world.gregs.hestia.social.network.social.out.InterfaceComponentText
import world.gregs.hestia.social.network.worlds.WorldPackets.FRIENDS_CHAT_SETTING
import world.gregs.hestia.social.network.worlds.out.SendClientPacket

@PacketInfo(-1, FRIENDS_CHAT_SETTING)
class FriendsChatSettings : InboundPacket {
    override fun read(session: Session, packet: Packet) {
        val entityId = packet.readInt()
        val interfaceHash = packet.readInt()
        val widgetId = interfaceHash shr 16
        val componentId = interfaceHash - (widgetId shl 16)
        val option = packet.readByte()

        //Find the player
        val player = World.players.get(entityId, session.id) ?: return

        //Players channel
        val channel = World.channels?.get(player) ?: return

        when(componentId) {
            0 -> {//Open interface
                //TODO what will admin do about ranks as it's based on the local client friends list?
                session.write(SendClientPacket(entityId, InterfaceComponentText(widgetId, 22, channel.channelName ?: "Chat disabled")))
                session.write(SendClientPacket(entityId, InterfaceComponentText(widgetId, 23, channel.joinRank.string)))
                session.write(SendClientPacket(entityId, InterfaceComponentText(widgetId, 24, channel.talkRank.string)))
                session.write(SendClientPacket(entityId, InterfaceComponentText(widgetId, 25, channel.kickRank.string)))
                session.write(SendClientPacket(entityId, InterfaceComponentText(widgetId, 26, channel.lootRank.string)))
                //TODO send coinShare
            }
            33 -> {//Toggle coin share
                channel.setShare(player, !channel.coinShare)
            }
            else -> {//Rank settings
                val rank = if(componentId == 26 && option == 1) FriendsChat.Ranks.NO_ONE else FriendsChat.Ranks.values().firstOrNull { it.ordinal == option } ?: return

                val change = when(componentId) {
                    26 -> channel.setLoot(player, rank)
                    25 -> channel.setKick(player, rank)
                    24 -> channel.setTalk(player, rank)
                    23 -> channel.setJoin(player, rank)
                    else -> return
                }

                if(change != null) {
                    session.write(SendClientPacket(entityId, InterfaceComponentText(widgetId, componentId, change.string)))
                }
            }
        }
        return
    }
}