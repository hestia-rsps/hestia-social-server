package world.gregs.hestia.social.core.player

import world.gregs.hestia.social.api.Name

data class Names(override val username: String) : Name {
    override var name = username
    override var previousName = ""
}