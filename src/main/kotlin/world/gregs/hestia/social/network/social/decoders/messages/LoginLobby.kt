package world.gregs.hestia.social.network.social.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Request to login to the lobby
 * @param username Players username
 * @param password Players password
 * @param hd Client graphics setting
 * @param resize Client display mode
 * @param settings Client setting
 * @param affiliate Facebook login id
 * @param serverSeed Server seed
 * @param clientSeed Client seed
 */
data class LoginLobby(val username: String, val password: String, val hd: Boolean, val resize: Boolean, val settings: String, val affiliate: Int, val serverSeed: Long, val clientSeed: Long) : Message