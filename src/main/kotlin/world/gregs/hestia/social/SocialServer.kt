package world.gregs.hestia.social

import world.gregs.hestia.core.Settings
import world.gregs.hestia.core.network.NetworkConstants
import world.gregs.hestia.core.network.codec.Encoder
import world.gregs.hestia.core.network.codec.Pipeline
import world.gregs.hestia.core.network.codec.inbound.packet.PacketHandler
import world.gregs.hestia.core.network.login.LoginHandshake
import world.gregs.hestia.core.network.login.LoginHandshakeDecoder
import world.gregs.hestia.core.network.server.Network
import world.gregs.hestia.core.services.Cache
import world.gregs.hestia.core.services.load.PacketLoader
import world.gregs.hestia.social.core.World
import world.gregs.hestia.social.core.player.PlayersImpl
import world.gregs.hestia.social.core.social.*
import world.gregs.hestia.social.network.Huffman
import world.gregs.hestia.social.network.social.LobbyAttempt
import world.gregs.hestia.social.network.social.SocialConnections
import world.gregs.hestia.social.network.social.SocialPacketDecoder
import world.gregs.hestia.social.network.social.`in`.LoginLobby
import world.gregs.hestia.social.network.worlds.WorldPacketDecoder
import world.gregs.hestia.social.network.worlds.WorldsConnections

class SocialServer {

    fun start() {
        val loader = PacketLoader(Settings.get("packetPath", ""))

        //Load packets
        //Note: World packets makes use of social packets so must be loaded after
        val socialPackets = loader.load("world.gregs.hestia.social.network.social.in")
        val worldPackets = loader.load("world.gregs.hestia.social.network.worlds.in")
        SocialPacketDecoder.packets = socialPackets
        WorldPacketDecoder.packets = worldPackets

        startSocialServer()
        startWorldServer()
    }

    /**
     * Social Server
     * Handles login requests, lobby, relations & messages
     */
    private fun startSocialServer() {
        val socialPipeline = Pipeline()

        socialPipeline.apply {
            //Decodes login packet
            add(LoginHandshakeDecoder::class)
            //After lobby login, pipeline get's switched with social decoders
            add(LoginHandshake(LoginLobby(), LobbyAttempt(SocialPacketDecoder::class, PacketHandler(SocialPacketDecoder.packets), SocialConnections())))
            //Packet encoder
            add(Encoder())
        }

        Network(name = "Login Server", channel = socialPipeline).start(NetworkConstants.BASE_PORT)
    }

    /**
     * World Server
     * Handles re-routed client packets & game-server worlds
     */
    private fun startWorldServer() {
        val worldPipeline = Pipeline()

        worldPipeline.apply {
            //Decodes packets
            add(WorldPacketDecoder::class)
            //Removes world if game-server disconnected
            add(WorldsConnections())
            //Handles world packets
            add(PacketHandler(WorldPacketDecoder.packets))
            //Packet encoder
            add(Encoder())
        }

        Network(name = "World Server", channel = worldPipeline).start(NetworkConstants.BASE_PORT - 1)
    }
}

fun main() {
    Settings.load()
    Cache.init()
    val players = PlayersImpl()
    World.init(players, SocialTransmission(players), SocialPresence(players), SocialStatus(players), SocialAffiliations(players), FriendsChatChannels(players))
    Huffman.init()
    SocialServer().start()
}