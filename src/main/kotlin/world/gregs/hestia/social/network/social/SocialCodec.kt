package world.gregs.hestia.social.network.social

import world.gregs.hestia.core.network.codec.MessageHandshakeCodec
import world.gregs.hestia.core.network.protocol.decoders.LoginHandshakeDecoder
import world.gregs.hestia.core.network.protocol.decoders.PingDecoder
import world.gregs.hestia.core.network.protocol.encoders.*
import world.gregs.hestia.social.network.social.decoders.*
import world.gregs.hestia.social.network.social.encoders.*

class SocialCodec : MessageHandshakeCodec() {
    init {
        bind(LoginLobbyDecoder(), true)
        bind(LoginHandshakeDecoder(), true)

        bind(ChatTypeDecoder())
        bind(FriendListAddDecoder())
        bind(FriendListRemoveDecoder())
        bind(FriendsChatJoinDecoder())
        bind(FriendsChatKickDecoder())
        bind(FriendsChatRankDecoder())
        bind(IgnoreListAddDecoder())
        bind(IgnoreListRemoveDecoder())
        bind(LobbyOnlineStatusDecoder())
        bind(PingDecoder())
        bind(LobbyWorldListRefreshDecoder())
        bind(MessagePrivateDecoder())
        bind(MessagePrivateQuickChatDecoder())
        bind(MessagePublicDecoder())
        bind(MessagePublicQuickChatDecoder())
        bind(ReportAbuseDecoder())

        bind(ChatEncoder())
        bind(FriendsChatQuickChatEncoder())
        bind(ChatPrivateFromEncoder())
        bind(ChatPrivateQuickChatFromEncoder())
        bind(ChatPrivateQuickChatToEncoder())
        bind(ChatPrivateToEncoder())
        bind(ChatPublicEncoder())
        bind(ChatPublicQuickChatEncoder())
        bind(FriendListUnlockEncoder())
        bind(FriendListUpdateEncoder())
        bind(FriendsChatListAppendEncoder())
        bind(FriendsChatMessageEncoder())
        bind(FriendsChatUpdateEncoder())
        bind(FriendsChatDisconnectEncoder())
        bind(IgnoreListAllEncoder())
        bind(IgnoreListUnlockEncoder())
        bind(IgnoreListUpdateEncoder())
        bind(WidgetComponentTextEncoder())
        bind(LobbyDetailsEncoder())
        bind(PingEncoder())
        bind(PrivateStatusEncoder())
        bind(WorldListAllEncoder())
        bind(WorldListUpdateEncoder())

        bind(ClientResponseEncoder())
    }
}