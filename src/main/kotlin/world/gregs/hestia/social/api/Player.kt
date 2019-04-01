package world.gregs.hestia.social.api

import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.codec.message.Message
import world.gregs.hestia.social.network.social.encoders.messages.IgnoreListAll

interface Player {
    /**
     * Players names
     */
    val names: Name

    /**
     * Players current world (-1 = disconnected, 0 = lobby, 1...255 = world 1...world 255)
     */
    var worldId: Int

    /**
     * The players entity id (if logged in-game)
     */
    var entityId: Int?

    /**
     * The players client index (if logged in-game)
     */
    var index: Int?

    /**
     * The players lobby session (if in lobby)
     */
    var session: Session?

    /**
     * Relationships with other players
     */
    val relations: Relations

    /**
     * The players current online status setting
     */
    var status: Int

    /**
     * The players rights (0 = normal, 1 = player moderator, 2 = j moderator)
     */
    var rights: Int

    /**
     * Whether the player is connect to the lobby
     */
    val lobby: Boolean

    /**
     * Whether the player is an administrator
     */
    val admin: Boolean

    /**
     * Whether the player is in the lobby or in-game
     */
    val online: Boolean

    /**
     * Messenger
     */
    val messenger: Messenger

    /**
     * Friends Chat Channel
     */
    var channel: FriendsChat?
    /**
     * The players friends chat
     */
    var friendsChat: Name?

    /**
     * If [Player] is friends with [friend]
     * @param friend The suspected friend
     * @return If [friend] is a friend
     */
    fun friends(friend: Player): Boolean {
        return relations.isFriend(friend.names)
    }

    /**
     * If [Player] is ignoring [ignore]
     * @param ignore The suspected ignored player
     * @return If [ignore] is being ignored
     */
    fun ignores(ignore: Player): Boolean {
        return relations.isIgnored(ignore.names)
    }

    /**
     * Sends friends list friends online status change
     * @param name The friends username
     * @param friend The friends player
     */
    fun sendFriend(name: Name, friend: Player?)

    /**
     * Sends ignore list change
     * @param name The username to add/update
     * @param renamed Whether the update is to rename the ignored player
     */
    fun sendIgnore(name: Name, renamed: Boolean = false)

    /**
     * Sends the players friends list
     */
    fun sendFriends(players: Players) {
        relations.getFriends().forEach {
            sendFriend(it, players.get(it))
        }
    }

    /**
     * Sends the players ignores list
     */
    fun sendIgnores() {
        send(IgnoreListAll(relations.getIgnores()))
    }

    /**
     * Checks the players online status as viewed by [friend]
     * @param friend The perspective to view
     * @return Whether this player appears to be online
     */
    fun statusOnline(friend: Name): Boolean

    /**
     * Checks the players online status as viewed by [player]
     * @param player The perspective to view
     * @return Whether this player appears to be online
     */
    fun statusOnline(player: Player): Boolean {
        return statusOnline(player.names)
    }

    /**
     * Sends a packet to the players client (via game-server if logged-in)
     * @param message The message to send
     */
    fun send(message: Message)

    /**
     * Sends a chat message
     * @param message The message to send
     * @param type The message type (default 0)
     */
    fun message(message: String, type: Int = 0)

    /**
     * Links the player to a lobby network session
     * @param session The lobby client session
     */
    fun linkLobby(session: Session)

    /**
     * Links the player to a game id
     * @param entityId The entity id
     * @param clientIndex The client index
     */
    fun linkGame(entityId: Int, clientIndex: Int)
}