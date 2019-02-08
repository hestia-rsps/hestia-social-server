package world.gregs.hestia.social.api

interface Contacts : OnlineStatus, Affiliate {
    override fun connect(player: Player) {
    }

    override fun disconnect(player: Player) {
    }
}