package world.gregs.hestia.social.network.world

import world.gregs.hestia.core.network.codec.Codec
import world.gregs.hestia.core.network.codec.HandshakeDispatcher
import world.gregs.hestia.core.network.codec.MessageDispatcher
import world.gregs.hestia.social.network.world.handlers.*

class WorldMessages(codec: Codec, dispatcher: HandshakeDispatcher) : MessageDispatcher() {
    init {
        bind(ClientPacketInHandler(codec, dispatcher))
        bind(FriendsChatNameHandler())
        bind(FriendsChatSettingsHandler())
        bind(FriendsChatSetupRequestHandler())
        bind(LoginGameHandler())
        bind(PlayerLoginHandler())
        bind(PlayerLogoutHandler())
        bind(PlayerReconnectHandler())
        bind(WorldInfoHandler())
    }
}