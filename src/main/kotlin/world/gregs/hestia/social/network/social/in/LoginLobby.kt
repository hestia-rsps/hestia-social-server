package world.gregs.hestia.social.network.social.`in`

import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.packets.InboundPacket
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.network.packets.PacketOpcode
import world.gregs.hestia.core.network.packets.PacketSize
import world.gregs.hestia.social.network.lobby.out.LobbyDetails
import world.gregs.hestia.social.network.social.out.SendFriend
import world.gregs.hestia.social.network.social.out.UnlockFriendList

@PacketSize(-2)
@PacketOpcode(19)
class LoginLobby : InboundPacket {

    override fun read(session: Session, packet: Packet, length: Int): Boolean {
        val name = packet.readString()
        val isHD = packet.readByte() == 1
        val isResizable = packet.readByte() == 1
        for (i in 0..23)
            packet.readByte()
        val settings = packet.readString()

        val unknown = packet.readInt()
        val crcValues = IntArray(35)
        for (i in crcValues.indices)
            crcValues[i] = packet.readInt()
        /*val player: Player?
        if (!playerExists(name)) {
            val salt = session.salt()
            pass = session.hashSHA(pass!! + salt)
            player = Player(name, salt)
            player.password = pass
        } else {
            player = loadPlayer(name)
            if (player == null) {
                session.send(ClientResponse(AddressingFeature.Responses.INVALID_LOGIN_SERVER))
                return
            }
            pass = session.hashSHA(pass!! + player.salt)
            if (player.password != pass) {
                session.send(ClientResponse(AddressingFeature.Responses.INVALID_CREDENTIALS))
                return
            }
        }
        if (player.isPermBanned || player.banned > System.currentTimeMillis())
            session.send(ClientResponse(AddressingFeature.Responses.ACCOUNT_DISABLED))
        else {
            player.init(ClientDetails(session, name))
            player.start(true)
            session.setDecoder(3, player)
            player.save()
        }*/
        session.write(LobbyDetails(name, true))
        session.write(UnlockFriendList())
        session.write(SendFriend(name, "", false, false, false, 1))
        return true
    }

}