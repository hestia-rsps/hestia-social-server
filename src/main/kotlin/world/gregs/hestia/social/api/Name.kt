package world.gregs.hestia.social.api

interface Name {
    /**
     * The players login name
     */
    val username: String

    /**
     * The players display name
     */
    var name: String

    /**
     * The players previous display name
     */
    var previousName: String
}