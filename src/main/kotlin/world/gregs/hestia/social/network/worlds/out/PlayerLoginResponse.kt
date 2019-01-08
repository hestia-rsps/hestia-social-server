package world.gregs.hestia.social.network.worlds.out

import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.network.packets.out.Response
import world.gregs.hestia.social.api.Name
import world.gregs.hestia.social.network.worlds.WorldPackets.LOGIN_RESPONSE

class PlayerLoginResponse(sessionId: Int, response: Response, name: Name? = null, mode: Int? = null, width: Int? = null, height: Int? = null) : Packet.Builder(LOGIN_RESPONSE, Packet.Type.VAR_BYTE) {
    init {
        writeShort(sessionId)
        writeByte(response.opcode)
        if(response == Response.NORMAL) {
            writeString(name!!.name)
            writeByte(mode!!)
            writeShort(width!!)
            writeShort(height!!)
        }
    }
}