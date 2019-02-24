package world.gregs.hestia.social.network.social

import io.netty.channel.ChannelHandler
import world.gregs.hestia.core.network.codec.HandshakeDispatcher
import world.gregs.hestia.core.network.codec.message.SimpleMessageHandshake

@ChannelHandler.Sharable
class SocialHandshake(dispatcher: HandshakeDispatcher) : SimpleMessageHandshake(dispatcher)