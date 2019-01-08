package world.gregs.hestia.social.network.social.out

import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.social.api.FriendsChat
import world.gregs.hestia.social.api.Player
import world.gregs.hestia.social.network.social.out.FriendsChatDetails.Companion.writeFriendDetails

class FriendsChatAppend(player: Player, rank: FriendsChat.Ranks) : Packet.Builder(24, Packet.Type.VAR_BYTE) {
    init {
        writeFriendDetails(player, rank)
    }
}