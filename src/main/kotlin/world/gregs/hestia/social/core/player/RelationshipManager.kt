package world.gregs.hestia.social.core.player

import world.gregs.hestia.social.api.Name
import world.gregs.hestia.social.api.Relations
import world.gregs.hestia.social.api.RelationsManager

class RelationshipManager : RelationsManager {
    override val relations = HashMap<Name, Relations>()

    override fun create(name: Name): Relations {
        val relation = Relationships()
        relations[name] = relation
        return relation
    }

    override fun get(name: Name): Relations? {
        return relations.getOrDefault(name, null)
    }
}