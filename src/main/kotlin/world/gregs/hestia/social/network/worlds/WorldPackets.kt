package world.gregs.hestia.social.network.worlds

object WorldPackets {
    /* Inbound */
    const val WORLD_CONNECTION = 11
    const val PLAYER_RECONNECT = 3
    const val FRIENDS_CHAT_NAME = 5
    const val FRIENDS_CHAT_SETTING = 6
    const val FRIENDS_CHAT_SETUP = 7
    const val FRIENDS_LOGIN_GAME = 8
    const val PLAYER_LOGOUT = 4
    const val PLAYER_LOGIN_LOBBY = 2
    const val CLIENT_PACKET = 1

    /* Outbound */
    const val SEND_CLIENT_PACKET = 2
    const val WORLD_PACKET = 3
    const val LOGIN_RESPONSE = 4
    const val SOCIAL_SERVER_DETAILS = 11
}