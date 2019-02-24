package world.gregs.hestia.social

import world.gregs.hestia.core.Settings
import world.gregs.hestia.core.cache.CacheStore
import world.gregs.hestia.core.cache.compress.Huffman
import world.gregs.hestia.core.network.NetworkConstants
import world.gregs.hestia.core.network.Pipeline
import world.gregs.hestia.core.network.codec.decode.SimplePacketDecoder
import world.gregs.hestia.core.network.codec.decode.SimplePacketHandshakeDecoder
import world.gregs.hestia.core.network.codec.message.SimpleMessageDecoder
import world.gregs.hestia.core.network.codec.message.SimpleMessageEncoder
import world.gregs.hestia.core.network.codec.message.SimpleMessageHandler
import world.gregs.hestia.core.network.codec.message.SimpleMessageHandshakeDecoder
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

class SocialServer {

    private val socialCodec = SocialCodec()
    private val socialMessages = SocialMessages()

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
            it.addLast(SimplePacketHandshakeDecoder(socialCodec, handshake))
        }

        socialPipeline.apply {
            //Decodes packets
            add(SimpleMessageHandshakeDecoder(socialCodec, handshake))
            //Handles handshake & messages
            add(handshake, "handler")
            //Encodes messages
            add(SimpleMessageEncoder(socialCodec))
        }

        Network(name = "Login Server", channel = socialPipeline).start(NetworkConstants.BASE_PORT)
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
            add(SimpleMessageEncoder(codec))
            //Disconnect
            add(WorldConnections())
        }

        Network(name = "World Server", channel = worldPipeline).start(NetworkConstants.BASE_PORT - 1)
    }
}

fun main() {
    Settings.load()
    val cache = CacheStore()
    val players = PlayersImpl()
    World.init(players, SocialTransmission(players), SocialPresence(players), SocialStatus(players), SocialAffiliations(players), FriendsChatChannels(players))
    Huffman.init(cache)
    SocialServer().start()
}