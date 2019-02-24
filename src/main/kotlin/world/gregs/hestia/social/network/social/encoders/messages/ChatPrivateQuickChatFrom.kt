package world.gregs.hestia.social.network.social.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Private quick chat message from another player
 * @param name The display name of the player who sent the message
 * @param rights The players rights (0 = normal, 1 = player mod, 2 = admin)
 * @param file The quick chat file id
 * @param data Additional data
 */
data class ChatPrivateQuickChatFrom(val name: String, val rights: Int, val file: Int, val data: ByteArray?) : Message {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ChatPrivateQuickChatFrom

        if (name != other.name) return false
        if (rights != other.rights) return false
        if (file != other.file) return false
        if (data != null) {
            if (other.data == null) return false
            if (!data.contentEquals(other.data)) return false
        } else if (other.data != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + rights
        result = 31 * result + file
        result = 31 * result + (data?.contentHashCode() ?: 0)
        return result
    }
}