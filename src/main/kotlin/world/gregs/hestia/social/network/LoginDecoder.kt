package world.gregs.hestia.social.network

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.Settings
import world.gregs.hestia.core.cache.crypto.Rsa
import world.gregs.hestia.core.cache.crypto.Xtea
import world.gregs.hestia.core.network.NetworkConstants
import world.gregs.hestia.core.network.client.Response
import world.gregs.hestia.core.network.clientRespond
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.codec.packet.PacketReader
import java.math.BigInteger

/**
 * LoginDecoder
 * Decodes the first half of the login message
 */
object LoginDecoder {

    private val RSA_MODULUS = BigInteger(Settings.getString("rsaModulus"), 16)
    private val RSA_PRIVATE = BigInteger(Settings.getString("rsaPrivate"), 16)

    /**
     * Decodes login message
     * @param ctx Channel
     * @param packet Packet to decode
     * @param extra Whether to read extra byte
     * @return Triple(password, server seed, client seed)
     */
    fun decode(ctx: ChannelHandlerContext, packet: Packet, extra: Boolean = false) : Pair<String, IntArray>? {
        val version = packet.readInt()
        if (version != NetworkConstants.CLIENT_MAJOR_VERSION) {
            ctx.clientRespond(Response.GAME_UPDATED)
            return null
        }

        if (extra) {
            packet.readUnsignedByte()
        }

        val rsaBlockSize = packet.readUnsignedShort()//RSA block size
        if (rsaBlockSize > packet.readableBytes()) {
            ctx.clientRespond(Response.BAD_SESSION_ID)
            return null
        }
        val data = ByteArray(rsaBlockSize)
        packet.readBytes(data)
        val rsa = Rsa.crypt(data, RSA_MODULUS, RSA_PRIVATE)
        val rsaBuffer = PacketReader(rsa)
        val sessionId = rsaBuffer.readUnsignedByte()
        if (sessionId != 10) {//rsa block start check
            ctx.clientRespond(Response.BAD_SESSION_ID)
            return null
        }

        val isaacKeys = IntArray(4)
        for (i in isaacKeys.indices) {
            isaacKeys[i] = rsaBuffer.readInt()
        }

        if (rsaBuffer.readLong() != 0L) {//password should start here (marked by 0L)
            ctx.clientRespond(Response.COULD_NOT_COMPLETE_LOGIN)
            return null
        }

        val password: String = rsaBuffer.readString()
        val serverSeed = rsaBuffer.readLong()
        val clientSeed = rsaBuffer.readLong()

        Xtea.decipher(packet.buffer, isaacKeys)
        return Pair(password, isaacKeys)
    }
}