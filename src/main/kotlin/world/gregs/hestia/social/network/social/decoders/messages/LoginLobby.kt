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
 * @param isaacKey Isaac key seeds
 */
data class LoginLobby(val username: String, val password: String, val hd: Boolean, val resize: Boolean, val settings: String, val affiliate: Int, val isaacKey: IntArray) : Message {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LoginLobby

        if (username != other.username) return false
        if (password != other.password) return false
        if (hd != other.hd) return false
        if (resize != other.resize) return false
        if (settings != other.settings) return false
        if (affiliate != other.affiliate) return false
        if (!isaacKey.contentEquals(other.isaacKey)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = username.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + hd.hashCode()
        result = 31 * result + resize.hashCode()
        result = 31 * result + settings.hashCode()
        result = 31 * result + affiliate
        result = 31 * result + isaacKey.contentHashCode()
        return result
    }
}