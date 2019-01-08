package world.gregs.hestia.social.core.player

import world.gregs.hestia.social.api.Name
import world.gregs.hestia.social.api.NameList

class NamesList : NameList {
    private val names = ArrayList<Name>()

    override fun addName(name: Name) {
        names.add(name)
    }

    override fun getName(name: String): Name? {
        return names.firstOrNull { it.name.equals(name, true) }
    }

    override fun getUserName(username: String): Name? {
        return names.firstOrNull { it.username.equals(username, true) }
    }

}