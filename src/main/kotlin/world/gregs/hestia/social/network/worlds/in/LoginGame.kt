package world.gregs.hestia.social.network.worlds.`in`

import org.slf4j.LoggerFactory
import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.packets.InboundPacket
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.network.packets.PacketInfo
import world.gregs.hestia.core.network.packets.out.Response
import world.gregs.hestia.core.services.Cache
import world.gregs.hestia.social.core.World
import world.gregs.hestia.social.network.worlds.WorldPackets.FRIENDS_LOGIN_GAME
import world.gregs.hestia.social.network.worlds.out.PlayerLoginResponse

@PacketInfo(-2, FRIENDS_LOGIN_GAME)
class LoginGame : InboundPacket {

    private val logger = LoggerFactory.getLogger(LoginGame::class.java)!!

    override fun read(session: Session, packet: Packet) {
        val sessionId = packet.readShort()
        val username = packet.readString().toLowerCase().capitalize()
        packet.readUnsignedByte() // unknown
        val displayMode = packet.readUnsignedByte()
        val screenWidth = packet.readShort()
        val screenHeight = packet.readShort()
        val unknown2 = packet.readUnsignedByte()
        packet.skip(24) // 24bytes directly from a file, no idea whats there
        val settings = packet.readString()
        val affId = packet.readInt()
        packet.skip(packet.readUnsignedByte()) // useless settings
        if (packet.readUnsignedByte() != 5) {
            session.write(PlayerLoginResponse(sessionId, Response.BAD_SESSION_ID))
            return
        }
        packet.readUnsignedByte()
        packet.readUnsignedByte()
        packet.readUnsignedByte()
        packet.readUnsignedByte()
        packet.readUnsignedByte()
        packet.readUnsignedByte()
        packet.readUnsignedByte()
        packet.readUnsignedByte()
        packet.readShort()
        packet.readUnsignedByte()
        packet.readTriByte()
        packet.readShort()
        packet.readUnsignedByte()
        packet.readUnsignedByte()
        packet.readUnsignedByte()
        packet.readJagString()
        packet.readJagString()
        packet.readJagString()
        packet.readJagString()
        packet.readUnsignedByte()
        packet.readShort()
        val unknown3 = packet.readInt()
        val userFlow = packet.readLong()
        val hasAdditionalInformation = packet.readUnsignedByte() == 1
        if (hasAdditionalInformation) packet.readString() // additionalInformation
        val hasJagtheora = packet.readUnsignedByte() == 1
        val js = packet.readUnsignedByte() == 1
        val hc = packet.readUnsignedByte() == 1
        for (index in 0 until Cache.indexCount()) {
            val crc = Cache.getIndex(index)?.crc ?: 0
            val receivedCRC = packet.readInt()
            if (crc != receivedCRC && index < 32) {
                session.write(PlayerLoginResponse(sessionId, Response.GAME_UPDATED))
                return
            }
        }
        // invalid chars
        if (username.length <= 1 || username.length >= 15 || username.contains("?") || username.contains(":")
                || username.endsWith("<") || username.contains("\\") || username.contains("*") || username.startsWith("_")
                || username.contains("\"")) {
            session.write(PlayerLoginResponse(sessionId, Response.INVALID_CREDENTIALS))
            return
        }

//        val salt: String = Encryption.salt
//        val password = Encryption.hashSHA("test$salt")

        val name = World.names.getUserName(username)
        if(name == null) {
            logger.info("Error finding player name '$username'")
            session.write(PlayerLoginResponse(sessionId, Response.INVALID_LOGIN_SERVER))
            return
        }

        val player = World.players.get(name)
        if(player == null) {
            logger.info("Error finding player instance '$name'")
            session.write(PlayerLoginResponse(sessionId, Response.COULD_NOT_COMPLETE_LOGIN))
            return
        }

        session.write(PlayerLoginResponse(sessionId, Response.NORMAL, name, displayMode, screenWidth, screenHeight))
    }

}