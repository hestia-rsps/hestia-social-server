package world.gregs.hestia.social.api

interface RelationsManager {
    val relations: Map<Name, Relations>

    fun create(name: Name): Relations

    fun get(name: Name): Relations?

    fun get(player: Player): Relations? {
        return get(player.names)
    }
}