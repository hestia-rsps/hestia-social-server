package world.gregs.hestia.social.core.social.channel

import org.junit.jupiter.api.Test
import world.gregs.hestia.social.api.FriendsChat

internal class FriendsChatChannelNameTest : FriendsChatHelper() {

    /*  Channel naming  */

    @Test
    fun `Can rename if owner`() {
        //Given
        setupChannel(player = true)
        rank(FriendsChat.Ranks.OWNER)
        //When
        rename()
        //Then
        assertRenamed(true)
    }

    @Test
    fun `Can rename if not owner but admin`() {
        //Given
        setupChannel(player = true)
        rank(FriendsChat.Ranks.ANYONE)
        admin(true)
        //When
        rename()
        //Then
        assertRenamed(true)
    }

    @Test
    fun `Can't rename if not owner`() {
        //Given
        setupChannel(player = true)
        rank(FriendsChat.Ranks.ANYONE)
        //When
        rename()
        //Then
        assertRenamed(false)
    }

    @Test
    fun `Twelve character limit applies to owners`() {
        //Given
        setupChannel(player = true)
        rank(FriendsChat.Ranks.OWNER)
        //When
        rename("More than twelve")
        //Then
        assertRenamed(false)
    }

    @Test
    fun `Twelve character limit doesn't apply to admins`() {
        //Given
        setupChannel(player = true)
        rank(FriendsChat.Ranks.ANYONE)
        admin(true)
        //When
        rename("More than twelve")
        //Then
        assertName("More Than Twelve")
    }

    @Test
    fun `Filtered words applies to owners`() {
        //Given
        setupChannel(player = true)
        rank(FriendsChat.Ranks.OWNER)
        //When
        rename("Game mode")
        //Then
        assertRenamed(false)
    }

    @Test
    fun `Admin can change another's channel`() {
        //Given
        setupChannel(player = true)
        rank(FriendsChat.Ranks.ANYONE, friend)
        admin(true, friend)
        //When
        rename(player = friend)
        //Then
        assertRenamed(true)
    }

    @Test
    fun `Players can't change another's channel`() {
        //Given
        setupChannel(player = true)
        rank(FriendsChat.Ranks.GENERAL, friend)
        //When
        rename(player = friend)
        //Then
        assertRenamed(false)
    }

    @Test
    fun `Name is formatted`() {
        //Given
        setupChannel(player = true)
        admin(true)
        //When
        rename("FORMAT name")
        //Then
        assertName("Format Name")
    }
}