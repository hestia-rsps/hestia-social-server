package world.gregs.hestia.social.core.social.channel

import org.junit.jupiter.api.Test
import world.gregs.hestia.social.api.FriendsChat
import world.gregs.hestia.social.core.Duration.HOUR

internal class FriendsChatChannelTest : FriendsChatHelper() {

    @Test
    fun `Player isn't banned`() {
        //Given
        setupChannel(player = true, friend = true)
        //Then
        assertBanned(false)
    }

    @Test
    fun `Player is banned`() {
        //Given
        setupChannel(player = true, friend = true)
        //When
        ban(HOUR)
        //Then
        assertBanned(true)
    }

    @Test
    fun `Elapsed ban is removed`() {
        //Given
        setupChannel(player = true, friend = true)
        //When
        ban(0)
        //Then
        assertBanned(false)
    }

    @Test
    fun `Clean removes all elapsed bans`() {
        //Given
        setupChannel(player = true, friend = true)
        //When
        ban(-1)
        ban(HOUR, friend)
        //Then
        channel.clean()
        assertIsBanned(false, player)
        assertIsBanned(true, friend)
    }

    @Test
    fun `Leave removes member`() {
        //Given
        setupChannel(player = true, friend = true)
        //When
        leave()
        //Then
        assertMember(false)
        assertMember(true, friend)
    }

    @Test
    fun `Owner leaving doesn't need friends settings refresh`() {
        //Given
        setupChannel(player = true)
        rank(FriendsChat.Ranks.OWNER)
        //When
        leave()
        //Then
        assertRefresh(false)
    }

    @Test
    fun `Admin leaving needs friends settings refresh`() {
        //Given
        setupChannel(player = true)
        rank(FriendsChat.Ranks.RECRUIT)
        admin(true)
        //When
        leave()
        //Then
        assertRefresh(true)
    }

    @Test
    fun `Member leaving needs friends settings refresh`() {
        //Given
        setupChannel(player = true)
        //When
        leave()
        //Then
        assertRefresh(true)
    }

    @Test
    fun `Admin is owner`() {
        //Given
        setupChannel(player = true, friend = true)
        rank(FriendsChat.Ranks.FRIEND)
        //When
        admin(true)
        //Then
        assertOwner(true)
        assertOwner(false, friend)
    }

    @Test
    fun `Owner is owner`() {
        //Given
        setupChannel(player = true, friend = true)
        admin(false)
        //When
        rank(FriendsChat.Ranks.OWNER)
        //Then
        assertOwner(true)
        assertOwner(false, friend)
    }

    @Test
    fun `Disabled channel has no ranks`() {
        assertHasRank(false, FriendsChat.Ranks.FRIEND)
    }

    @Test
    fun `Rank is inclusive`() {
        //Given
        setupChannel(player = true, friend = true)
        //When
        rank(FriendsChat.Ranks.CORPOREAL)
        //Then
        assertHasRank(false, FriendsChat.Ranks.SERGEANT, true)
        assertHasRank(true, FriendsChat.Ranks.CORPOREAL, true)
        assertHasRank(true, FriendsChat.Ranks.RECRUIT, true)
    }

    @Test
    fun `Rank is exclusive`() {
        //Given
        setupChannel(player = true, friend = true)
        //When
        rank(FriendsChat.Ranks.CORPOREAL)
        //Then
        assertHasRank(false, FriendsChat.Ranks.SERGEANT)
        assertHasRank(false, FriendsChat.Ranks.CORPOREAL)
        assertHasRank(true, FriendsChat.Ranks.RECRUIT)
    }

    @Test
    fun `Admin outranks all`() {
        //Given
        setupChannel(player = true, friend = true)
        //When
        rank(FriendsChat.Ranks.FRIEND)
        admin(true)
        //Then
        assertHasRank(true, FriendsChat.Ranks.SERGEANT)
        assertHasRank(true, FriendsChat.Ranks.CORPOREAL)
        assertHasRank(true, FriendsChat.Ranks.RECRUIT)
    }

    @Test
    fun `No ranks defaults to un-ranked`() {
        //Given
        setupChannel(player = true, friend = true)
        //Then
        assertFriend(false)
        assertRank(FriendsChat.Ranks.NO_ONE)
    }

    @Test
    fun `Ranks are correct`() {
        //Given
        setupChannel(player = true, friend = true)
        rank(FriendsChat.Ranks.LIEUTENANT)
        //Then
        assertRank(FriendsChat.Ranks.LIEUTENANT)
    }

    @Test
    fun `Channel setup`() {
        //Given
        setupChannel()
        //Then
        assertRenamed(false)
        assertOwnerName()
    }

    @Test
    fun `Disable kicks all players`() {
        //Given
        setupChannel(player = true, friend = true)
        rank(FriendsChat.Ranks.OWNER)
        //When
        disable()
        //Then
        assertKicked(true, player)
        assertKicked(true, friend)
    }
}