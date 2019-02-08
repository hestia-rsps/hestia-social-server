package world.gregs.hestia.social.core

import org.slf4j.LoggerFactory
import world.gregs.hestia.core.Settings
import world.gregs.hestia.social.api.*
import world.gregs.hestia.social.core.player.*

object World: Lobby {

    private val logger = LoggerFactory.getLogger(World::class.java)
    lateinit var lobby: Lobby
    lateinit var contacts: Contacts
    lateinit var players: Players//Should only be used for packets
    lateinit var relations: RelationsManager
    lateinit var names: NameList
    var channels: FriendChannels? = null

    fun init(players: Players, transmission: Connection, presence: Connection, status: OnlineStatus, affiliate: Affiliate, channels: FriendChannels) {
        World.players = players
        lobby = LobbyImpl(players, transmission, presence, status)
        contacts = ContactsImpl(affiliate, status)
        //TODO all below this needs to load from file
        names = NamesList()
        relations = RelationshipManager()
        //TODO temp until saving

        val test = Names("Test")
        test.name = "Bob"
        test.previousName = "Test"
        names.addName(test)

        val greg = Names("Greg")
        names.addName(greg)
        relations.create(greg).apply {
            addFriend(Names("A Friend"))
            addFriend(Names("Another friend"))
            addIgnore(Names("An ignore"))
            addIgnore(Names("Another ignore"))
        }

        this.channels = channels
        channels.init()
    }

    override fun connect(player: Player) {
        lobby.connect(player)
        //Loads friends chat
        channels?.rejoin(player)
        logger.info("Connected: ${player.names.username} entity: ${player.entityId} world: ${player.worldId}")
    }

    override fun disconnect(player: Player) {
        logger.info("Disconnected: ${player.names.username} entity: ${player.entityId} world: ${player.worldId}")
        player.channel?.leave(player, false)
        lobby.disconnect(player)
        contacts.disconnect(player)
    }

    override fun reconnect(player: Player, world: Int) {
        logger.info("Reconnected: ${player.names.username} entity: ${player.entityId} world: ${player.worldId}")
        lobby.reconnect(player, world)
        //Reconnect to fc
        channels?.rejoin(player)
    }

    override fun login(player: Player) {
        player.message("Welcome to ${Settings.getString("serverName")}.")
        lobby.login(player)
        channels?.rejoin(player)

        logger.info("Logged in: ${player.names.username} entity: ${player.entityId} world: ${player.worldId}")
    }

    override fun logout(player: Player) {
        lobby.logout(player)
        channels?.rejoin(player)
        logger.info("Logged out: ${player.names.username} entity: ${player.entityId} world: ${player.worldId}")
    }

    fun loadPlayer(name: String): Player {
        logger.info("Load player: $name")
        val names = World.names.getUserName(name) ?: let {
            val names = Names(name)
            this.names.addName(names)
            logger.info("Creating new player: $name")
            names
        }
        val relations = relations.get(names) ?: relations.create(names)
        val player = PlayerImpl(null, names, relations)
        channels?.name(player, player.names.name)//TODO temp - creates players channel on login, remove once saving implemented
        return player
    }
}