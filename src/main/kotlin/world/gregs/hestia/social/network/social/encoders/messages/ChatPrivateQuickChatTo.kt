package world.gregs.hestia.social.network.social.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Private quick chat message sent to another player
 * @param name The display name of the player the message was sent to
 * @param file The quick chat file id
 * @param data Additional data
 */
data class ChatPrivateQuickChatTo(val name: String, val file: Int, val data: ByteArray?) : Message {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ChatPrivateQuickChatTo

        if (name != other.name) return false
        if (file != other.file) return false
        if (data != null) {
            if (other.data == null) return false
            if (!data.contentEquals(other.data)) return false
        } else if (other.data != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + file
        result = 31 * result + (data?.contentHashCode() ?: 0)
        return result
    }
}