package world.gregs.hestia.social.network.world.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.client.Response
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.WorldOpcodes.PLAYER_LOGIN_GAME
import world.gregs.hestia.core.network.protocol.messages.LoginGame
import world.gregs.hestia.core.network.protocol.messages.PlayerLoginResponse
import world.gregs.hestia.social.network.LoginDecoder

class LoginGameDecoder : MessageDecoder<LoginGame>(Packet.Type.VAR_SHORT, PLAYER_LOGIN_GAME) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): LoginGame? {
        val session = packet.readShort()
        val triple = LoginDecoder.decode(packet, true)
        if(triple.first != Response.NORMAL) {
            ctx.channel().writeAndFlush(PlayerLoginResponse(session, triple.first))
            return null
        }
        val password = triple.second!!
        val isaacKeys = triple.third!!
        val username = packet.readString()
        packet.readUnsignedByte() // snlogin
        val displayMode = packet.readUnsignedByte()
        val screenWidth = packet.readUnsignedShort()
        val screenHeight = packet.readUnsignedShort()
        val antialiasLevel = packet.readUnsignedByte()
        packet.skip(24) //TODO 24 graphics preferences need client refactor
        val settings = packet.readString()
        val affiliateId = packet.readInt()
        packet.skip(packet.readUnsignedByte()) // useless settings
        if (packet.readUnsignedByte() != 5) {
            ctx.channel().writeAndFlush(PlayerLoginResponse(session, Response.BAD_SESSION_ID))
            return null
        }
        val os = packet.readUnsignedByte()
        packet.readUnsignedByte()
        val versionType= packet.readUnsignedByte()
        val vendorType = packet.readUnsignedByte()
        val javaRelease = packet.readUnsignedByte()
        val javaVersion = packet.readUnsignedByte()
        val javaUpdate = packet.readUnsignedByte()
        packet.readUnsignedByte()
        val heapSize = packet.readShort()
        val processorCount = packet.readUnsignedByte()
        packet.readMedium()
        packet.readShort()
        packet.readUnsignedByte()
        packet.readUnsignedByte()
        packet.readUnsignedByte()
        packet.readByte()
        packet.readString()
        packet.readByte()
        packet.readString()
        packet.readByte()
        packet.readString()
        packet.readByte()
        packet.readString()
        packet.readUnsignedByte()
        packet.readShort()
        val unknown3 = packet.readInt()
        val userFlow = packet.readLong()
        val hasAdditionalInformation = packet.readUnsignedBoolean()
        if (hasAdditionalInformation) {
            val additionalInformation = packet.readString()
        }
        val hasJagtheora = packet.readUnsignedBoolean()
        val js = packet.readUnsignedBoolean()
        val hc = packet.readUnsignedBoolean()
        return LoginGame(session, username, password, isaacKeys, displayMode, screenWidth, screenHeight, antialiasLevel, settings, affiliateId, os, versionType, vendorType, javaRelease, javaVersion, javaUpdate, processorCount)
    }

}