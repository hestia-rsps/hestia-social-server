package world.gregs.hestia.social.network.social.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Updates a world in the world list
 * @param data The pre-encoded data for performance enhancements
 */
data class WorldListUpdate(val data: ByteArray) : Message {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WorldListUpdate

        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        return data.contentHashCode()
    }
}