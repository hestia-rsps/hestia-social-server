package world.gregs.hestia.social.network.social.`in`

import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.network.packets.PacketInfo
import world.gregs.hestia.social.api.Player
import world.gregs.hestia.social.core.World
import world.gregs.hestia.social.network.Huffman
import world.gregs.hestia.social.network.Packets.CHAT
import world.gregs.hestia.social.network.PlayerPacket
import world.gregs.hestia.social.network.social.out.PublicChatMessage

@PacketInfo(-1, CHAT)
class ChatMessage : PlayerPacket {
    override fun read(player: Player, packet: Packet) {
        val colour = packet.readUnsignedByte()
        val move = packet.readUnsignedByte()
        val message = Huffman.decompress(packet, packet.readSmart())?.capitalize() ?: return
        val effects = colour shl 8 or (move and 0xff)
        when(player.messenger.messageType) {
            0 -> {//Public
                World.players.all.forEach {
                    if(player.index != null) {
                        it.send(PublicChatMessage(player.index!!, effects, player.rights, message))
                    }
                }
            }
            1 -> {//Friends chat
                player.channel?.message(player, message)
            }
        }
    }
}