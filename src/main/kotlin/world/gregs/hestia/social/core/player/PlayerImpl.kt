package world.gregs.hestia.social.core.player

import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.codec.message.Message
import world.gregs.hestia.core.network.protocol.encoders.messages.Chat
import world.gregs.hestia.social.api.FriendsChat
import world.gregs.hestia.social.api.Name
import world.gregs.hestia.social.api.Player
import world.gregs.hestia.social.api.Relations
import world.gregs.hestia.social.core.Server
import world.gregs.hestia.social.network.social.encoders.messages.FriendListUpdate
import world.gregs.hestia.social.network.social.encoders.messages.IgnoreListUpdate
import world.gregs.hestia.social.network.world.encoders.messages.ClientMessage

open class PlayerImpl(override var session: Session?, override val names: Name, override val relations: Relations) : Player {
    override var worldId = 0
    override var rights = 0
    override var status = 0
    override var entityId: Int? = null
    override var index: Int? = null
    override val messenger = MessengerImpl()
    override var channel: FriendsChat? = null
    override var friendsChat: Name? = names//TODO temp - remove after fc loading

    override val lobby: Boolean
        get() = worldId == 0
    override val admin: Boolean
        get() = rights >= 2
    override val online: Boolean
        get() = worldId >= 0

    override fun send(message: Message) {
        session?.write(message, true) ?: if (entityId != null) Server.sessions[worldId]?.write(ClientMessage(entityId!!, message), true)
    }

    override fun statusOnline(friend: Name): Boolean {
        return status == 0 && !relations.isIgnored(friend) || status == 1 && relations.isFriend(friend)
    }

    override fun sendFriend(name: Name, friend: Player?) {
        send(if (friend == null || !friend.statusOnline(this)) {
            FriendListUpdate(name, false, false, 0, channel?.getRank(name)?.value ?: 0, false)//Display offline
        } else {
            //Note: Having lobby as world as 1 will have it appear as green but stop the continuous login-in messages
            FriendListUpdate(name, false, friend.lobby, if (friend.lobby) 256 else friend.worldId, channel?.getRank(friend)?.value ?: 0, false)//Display online
        })
    }

    override fun sendIgnore(name: Name, renamed: Boolean) {
        send(IgnoreListUpdate(name.name, name.previousName, renamed, false))
    }

    override fun message(message: String, type: Int) {
        send(Chat(type, 0, null, message))
    }

    override fun linkLobby(session: Session) {
        this.session = session
        this.entityId = null
        this.index = null
    }

    override fun linkGame(entityId: Int, clientIndex: Int) {
        this.entityId = entityId
        this.index = clientIndex
        this.session = null
    }

    override fun equals(other: Any?): Boolean {
        if (other is PlayerImpl) {
            return names == other.names || names.name == other.names.name
        }
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return names.name.hashCode()
    }
}