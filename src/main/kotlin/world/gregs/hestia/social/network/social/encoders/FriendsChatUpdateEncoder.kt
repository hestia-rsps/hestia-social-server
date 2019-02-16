package world.gregs.hestia.social.network.social.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.FRIENDS_CHAT_UPDATE
import world.gregs.hestia.core.services.toRSLong
import world.gregs.hestia.social.network.social.encoders.FriendsChatListAppendEncoder.Companion.writeFriendDetails
import world.gregs.hestia.social.network.social.encoders.messages.FriendsChatUpdate

class FriendsChatUpdateEncoder : MessageEncoder<FriendsChatUpdate>() {

    override fun encode(builder: PacketBuilder, message: FriendsChatUpdate) {
        val channel = message.channel
        builder.apply {
            writeOpcode(FRIENDS_CHAT_UPDATE, Packet.Type.VAR_SHORT)
            writeString(channel.owner?.name!!)
            writeByte(0)
            writeLong(channel.channelName!!.toRSLong())
            writeByte(channel.kickRank.value)//Who can kick on chat
            writeByte(channel.members.size)//Player count
            channel.members.forEach {
                writeFriendDetails(this, it, channel.getRank(it))
            }
        }
    }

}