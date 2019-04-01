package world.gregs.hestia.social.network.social

import world.gregs.hestia.core.network.codec.MessageHandshakeDispatcher
import world.gregs.hestia.core.network.protocol.handlers.LoginHandshakeHandler
import world.gregs.hestia.social.network.social.handlers.*

class SocialMessages(codec: SocialCodec) : MessageHandshakeDispatcher() {
    init {
        bind(LoginHandshakeHandler(), true)
        bind(LoginLobbyHandler(codec), true)

        bind(ChatTypeHandler())
        bind(FriendListAddHandler())
        bind(FriendListRemoveHandler())
        bind(FriendsChatJoinHandler())
        bind(FriendsChatKickHandler())
        bind(FriendsChatRankHandler())
        bind(IgnoreListAddHandler())
        bind(IgnoreListRemoveHandler())
        bind(LobbyOnlineStatusHandler())
        bind(PingHandler())
        bind(LobbyWorldListRefreshHandler())
        bind(MessagePrivateHandler())
        bind(MessagePrivateQuickChatHandler())
        bind(MessagePublicHandler())
        bind(MessagePublicQuickChatHandler())
        bind(ReportAbuseHandler())
    }
}