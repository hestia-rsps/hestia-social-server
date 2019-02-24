package world.gregs.hestia.social.core

import world.gregs.hestia.social.api.*
import world.gregs.hestia.social.network.social.encoders.messages.LobbyDetails
import world.gregs.hestia.social.network.social.encoders.messages.WorldListAll

class LobbyImpl(private val players: Players, private val transmission: Connection, private val presence: Connection, private val status: OnlineStatus) : Lobby {

    override fun connect(player: Player) {
        //Send lobby
        player.send(LobbyDetails(player.names.name, true), true)
        //Send worlds
        player.send(WorldListAll(Server.worlds, configuration = false, status = false))// WorldListUpdate(Server.empty))
        players.add(player)
        transmission.connect(player)
        presence.connect(player)
        status.connect(player)
    }

    override fun disconnect(player: Player) {
        players.remove(player)
        transmission.disconnect(player)
        presence.disconnect(player)
        status.disconnect(player)
    }

    override fun reconnect(player: Player, world: Int) {
        players.add(player)
        player.worldId = world
        presence.connect(player)
        transmission.connect(player)
    }

    override fun login(player: Player) {
        presence.connect(player)
        transmission.connect(player)
    }

    override fun logout(player: Player) {
        player.worldId = 0
        player.send(LobbyDetails(player.names.name, true))
        presence.connect(player)
        transmission.connect(player)
    }

}