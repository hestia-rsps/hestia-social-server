package world.gregs.hestia.social.core.player

import world.gregs.hestia.social.api.*

class ContactsImpl(private val affiliate: Affiliate, private val status: OnlineStatus) : Contacts {

    override fun addFriend(player: Player, friend: Name) {
        affiliate.addFriend(player, friend)
    }

    override fun addIgnore(player: Player, ignore: Name, temporary: Boolean) {
        affiliate.addIgnore(player, ignore, temporary)
    }

    override fun removeFriend(player: Player, friend: Name) {
        affiliate.removeFriend(player, friend)
    }

    override fun removeIgnore(player: Player, ignore: Name) {
        affiliate.removeIgnore(player, ignore)
    }

    override fun setStatus(player: Player, status: Int) {
        this.status.setStatus(player, status)
    }

    override fun disconnect(player: Player) {
        player.relations.removeIgnores()
    }
}