package world.gregs.hestia.social.network.social.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Public quick chat message
 * @param script The quick chat script
 * @param file The quick chat file id
 * @param data Any additional display data required (skill levels etc...)
 */
data class MessagePublicQuickChat(val script: Int, val file: Int, var data: ByteArray?) : Message {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MessagePublicQuickChat

        if (script != other.script) return false
        if (file != other.file) return false
        if (data != null) {
            if (other.data == null) return false
            if (!data!!.contentEquals(other.data!!)) return false
        } else if (other.data != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = script
        result = 31 * result + file
        result = 31 * result + (data?.contentHashCode() ?: 0)
        return result
    }
}