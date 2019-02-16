package world.gregs.hestia.social.network.world

import world.gregs.hestia.core.network.codec.MessageCodec
import world.gregs.hestia.core.network.protocol.decoders.FriendsChatSetupRequestDecoder
import world.gregs.hestia.core.network.protocol.encoders.FriendsChatSetupRequestEncoder
import world.gregs.hestia.social.network.social.SocialCodec
import world.gregs.hestia.social.network.world.decoders.*
import world.gregs.hestia.social.network.world.encoders.ClientMessageEncoder
import world.gregs.hestia.social.network.world.encoders.PlayerLoginResponseEncoder
import world.gregs.hestia.social.network.world.encoders.PlayerLoginSuccessEncoder
import world.gregs.hestia.social.network.world.encoders.SocialDetailsEncoder

class WorldCodec(socialCodec: SocialCodec) : MessageCodec() {
    init {
        bind(LoginGameDecoder())
        bind(WorldInfoDecoder())
        bind(PlayerLoginDecoder())
        bind(ClientPacketDecoder())
        bind(PlayerLogoutDecoder())
        bind(PlayerReconnectDecoder())
        bind(FriendsChatSettingsDecoder())
        bind(FriendsChatNameDecoder())
        bind(FriendsChatSetupRequestDecoder())

        bind(ClientMessageEncoder(socialCodec))
        bind(SocialDetailsEncoder(socialCodec))
        bind(PlayerLoginResponseEncoder())
        bind(PlayerLoginSuccessEncoder())
        bind(FriendsChatSetupRequestEncoder())
    }
}