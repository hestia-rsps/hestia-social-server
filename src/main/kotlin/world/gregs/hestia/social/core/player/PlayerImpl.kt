package world.gregs.hestia.social.core.player

import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.social.api.FriendsChat
import world.gregs.hestia.social.api.Name
import world.gregs.hestia.social.api.Player
import world.gregs.hestia.social.api.Relations
import world.gregs.hestia.social.core.Server
import world.gregs.hestia.social.network.social.out.ChatMessage
import world.gregs.hestia.social.network.social.out.SendFriend
import world.gregs.hestia.social.network.social.out.SendIgnore
import world.gregs.hestia.social.network.worlds.out.send

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

    override fun send(builder: Packet.Builder) {
        session?.write(builder) ?: if (entityId != null) Server.sessions[worldId]?.send(entityId!!, builder)
    }

    override fun statusOnline(friend: Name): Boolean {
        return status == 0 && !relations.isIgnored(friend) || status == 1 && relations.isFriend(friend)
    }

    override fun sendFriend(name: Name, friend: Player?) {
        send(if (friend == null || !friend.statusOnline(this)) {
            SendFriend(name, false, false, 0, channel?.getRank(name)?.value ?: 0, false)//Display offline
        } else {
            //Note: Having lobby as world as 1 will have it appear as green but stop the continuous login-in messages
            SendFriend(name, false, friend.lobby, if (friend.lobby) 256 else friend.worldId, channel?.getRank(friend)?.value ?: 0, false)//Display online
        })
    }

    override fun sendIgnore(name: Name) {
        send(SendIgnore(name.name, name.previousName, false, true))
    }

    override fun message(message: String, type: Int) {
        send(ChatMessage(message, type, null))
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
            return names.name == other.names.name
        }
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return names.name.hashCode()
    }
}