package world.gregs.hestia.social.network.social.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.LOBBY_DETAILS
import world.gregs.hestia.social.network.social.encoders.messages.LobbyDetails
import java.util.*

class LobbyDetailsEncoder : MessageEncoder<LobbyDetails>() {

    override fun encode(builder: PacketBuilder, message: LobbyDetails) {
        val (username, member) = message
        builder.apply {
            writeOpcode(LOBBY_DETAILS, Packet.Type.VAR_BYTE)
            writeByte(0)//Black marks
            writeByte(0)//Muted
            writeByte(0)//3
            writeByte(0)//4
            writeByte(0)//5
            writeLong(Date().time)//members subscription end
            writeByte(0)//0 not member & no recovery, 1 member & no rec, 2 not member & rec, 3 member & recovery
            writeInt(0)//recovery questions set date

            writeByte(if(member) 2 else 0)// 0 - Not a member, 1 - Membership expires, 2 - Subscription active
            writeInt(0)
            writeByte(0)
            writeInt(0)

            writeShort(1)//recovery questions set
            writeShort(0)//Number of unread messages
            writeShort(0)//Message: 0 - Welcome, 1 - Last login x days from: ip, 11162011 or (6 * (60000)) + 20000 - Last logged in earlier today
            writeInt(0)//Resolve hostname - last login ip

            writeByte(3)//Email registration: 0 - Unregistered, 1 - Pending Parental Confirm, 2 - Pending Confirm, 3 - Registered, 4 - No longer registered, 5 - Blank
            writeShort(0)//Credit card expiration time
            writeShort(0)//Credit card loyalty expiration time
            writeByte(1)//Script 6909
            writeByte(0)//Part of string below
            writeString(username)
            writeByte(0)//Script 6911
            writeInt(1)//Script 6912
            writeByte(1)//Bool Script 4700
            writeShort(0)//Server port offset id
            writeByte(0)//Part of string below
            writeString("")//Server ip address
        }
    }

}