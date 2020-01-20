package world.gregs.hestia.social

import world.gregs.hestia.core.Settings
import world.gregs.hestia.core.cache.CacheStore
import world.gregs.hestia.core.cache.compress.Huffman
import world.gregs.hestia.core.network.NetworkConstants
import world.gregs.hestia.core.network.codec.debug.DebugMessageEncoder
import world.gregs.hestia.core.network.codec.decode.SimplePacketDecoder
import world.gregs.hestia.core.network.codec.decode.SimplePacketHandshakeDecoder
import world.gregs.hestia.core.network.codec.message.SimpleMessageDecoder
import world.gregs.hestia.core.network.codec.message.SimpleMessageHandler
import world.gregs.hestia.core.network.codec.message.SimpleMessageHandshakeDecoder
import world.gregs.hestia.core.network.pipe.Pipeline
import world.gregs.hestia.core.network.server.Network
import world.gregs.hestia.social.core.World
import world.gregs.hestia.social.core.player.PlayersImpl
import world.gregs.hestia.social.core.social.*
import world.gregs.hestia.social.network.social.SocialCodec
import world.gregs.hestia.social.network.social.SocialHandshake
import world.gregs.hestia.social.network.social.SocialMessages
import world.gregs.hestia.social.network.world.WorldCodec
import world.gregs.hestia.social.network.world.WorldConnections
import world.gregs.hestia.social.network.world.WorldMessages

/**
 * Structured using traditional hierarchical "god" player class, unlike the game-server
 * @author Greg Hibberd
 */
class SocialServer {

    private val socialCodec = SocialCodec()
    private val socialMessages = SocialMessages(socialCodec)

    fun start() {
        startSocialServer()
        startWorldServer()
    }

    /**
     * Social Server
     * Handles login requests, lobby, relations & messages
     */
    private fun startSocialServer() {
        val handshake = SocialHandshake(socialMessages)

        val socialPipeline = Pipeline {
            it.addLast("packet", SimplePacketHandshakeDecoder(socialCodec, handshake))
        }

        socialPipeline.apply {
            //Decodes packets
            add(SimpleMessageHandshakeDecoder(socialCodec, handshake))
            //Handles handshake & messages
            add(handshake, "handler")
            //Encodes messages
            add(DebugMessageEncoder(socialCodec), "encoder")
        }

        Network(name = "Login Server", channel = socialPipeline).start(Settings.getInt("loginServerPort")!!)
    }

    /**
     * World Server
     * Handles re-routed client packets & game-server worlds
     */
    private fun startWorldServer() {
        val codec = WorldCodec(socialCodec)
        val worldPipeline = Pipeline {
            it.addLast(SimplePacketDecoder(codec))
        }

        worldPipeline.apply {
            //Decode
            add(SimpleMessageDecoder(codec))
            //Handle
            add(SimpleMessageHandler(WorldMessages(socialCodec, socialMessages)))
            //Encode
            add(DebugMessageEncoder(codec))
            //Disconnect
            add(WorldConnections())
        }

        Network(name = "World Server", channel = worldPipeline).start(Settings.getInt("worldServerPort")!!)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Settings.load("./social-settings.yml")
            val cache = CacheStore()
            val players = PlayersImpl()
            World.init(players, SocialTransmission(players), SocialPresence(players), SocialStatus(players), SocialAffiliations(players), FriendsChatChannels(players))
            Huffman.init(cache)
            SocialServer().start()
        }
    }
}