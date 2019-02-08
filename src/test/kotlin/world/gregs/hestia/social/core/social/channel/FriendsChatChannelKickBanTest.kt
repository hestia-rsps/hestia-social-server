package world.gregs.hestia.social.core.social.channel

import org.junit.jupiter.api.Test
import world.gregs.hestia.social.api.FriendsChat
import world.gregs.hestia.social.core.Duration.HOUR

internal class FriendsChatChannelKickBanTest : FriendsChatHelper() {

    /*  Kick  */

    @Test
    fun `Can't kick if channel inactive`() {
        //When
        kick()
        //Then
        assertKicked(false)
        assertIsMember(false)
    }

    @Test
    fun `Can kick if ranked`() {
        //Given
        setupChannel(player = true, target = true)
        kickRank(FriendsChat.Ranks.RECRUIT)
        rank(FriendsChat.Ranks.RECRUIT)
        //When
        kick()
        //Then
        assertKicked(true)
        assertIsMember(false)
    }

    @Test
    fun `Can kick if not ranked if admin`() {
        //Given
        setupChannel(player = true, target = true)
        kickRank(FriendsChat.Ranks.RECRUIT)
        admin(true)
        //When
        kick()
        //Then
        assertKicked(true)
        assertIsMember(false)
    }

    @Test
    fun `Can't kick if not found`() {
        //Given
        setupChannel(player = true, target = true)
        kickRank(FriendsChat.Ranks.FRIEND)
        //When
        kick(target)
        //Then
        assertKicked(false)
        assertIsMember(true)
    }

    @Test
    fun `Can kick if over ranked`() {
        //Given
        setupChannel(player = true, target = true)
        kickRank(FriendsChat.Ranks.RECRUIT)
        rank(FriendsChat.Ranks.OWNER)
        //When
        kick()
        //Then
        assertKicked(true)
        assertIsMember(false)
    }

    @Test
    fun `Can't kick if not ranked`() {
        //Given
        setupChannel(player = true, target = true)
        kickRank(FriendsChat.Ranks.RECRUIT)
        //When
        kick()
        //Then
        assertKicked(false)
        assertIsMember(true)
    }

    @Test
    fun `Can't kick if target not in channel`() {
        //Given
        setupChannel(player = true, target = true)
        //When
        kick()
        //Then
        assertKicked(false)
        assertIsMember(true)
    }

    @Test
    fun `Can't kick if kicker not in channel`() {
        //Given
        setupChannel(player = false, target = true)
        //When
        kick()
        //Then
        assertKicked(false)
        assertIsMember(true)
    }

    @Test
    fun `Can't kick self`() {
        //Given
        setupChannel(player = true, target = true)
        kickRank(FriendsChat.Ranks.FRIEND)
        //When
        kick(player)
        //Then
        assertKicked(false, player)
        assertIsMember(true, player)
    }

    @Test
    fun `Can't kick admin`() {
        //Given
        setupChannel(player = true, target = true)
        rank(FriendsChat.Ranks.OWNER)
        admin(true, target)
        //When
        kick()
        //Then
        assertKicked(false)
        assertIsMember(true)
    }

    @Test
    fun `Can't kick higher rank`() {
        //Given
        setupChannel(player = true, target = true)
        kickRank(FriendsChat.Ranks.RECRUIT)
        rank(FriendsChat.Ranks.CAPTAIN)
        rank(FriendsChat.Ranks.GENERAL, target)
        //When
        kick()
        //Then
        assertKicked(false)
        assertIsMember(true)
    }

    @Test
    fun `Can't kick same rank`() {
        //Given
        setupChannel(player = true, target = true)
        kickRank(FriendsChat.Ranks.RECRUIT)
        rank(FriendsChat.Ranks.GENERAL)
        rank(FriendsChat.Ranks.GENERAL, target)
        //When
        kick()
        //Then
        assertKicked(false)
        assertIsMember(true)
    }

    @Test
    fun `Can kick higher rank if admin`() {
        //Given
        setupChannel(player = true, target = true)
        kickRank(FriendsChat.Ranks.RECRUIT)
        rank(FriendsChat.Ranks.CAPTAIN)
        admin(true)
        rank(FriendsChat.Ranks.GENERAL, target)
        //When
        kick()
        //Then
        assertKicked(true)
        assertIsMember(false)
    }


    /*  Ban  */

    @Test
    fun `Can extend ban by re-banning`() {
        //Given
        setupChannel(player = true, target = true)
        kickRank(FriendsChat.Ranks.FRIEND)
        rank(FriendsChat.Ranks.RECRUIT)
        ban()
        //When
        val time = System.nanoTime() + HOUR
        ban()
        //Then
        assertIsBanned(true)
        assertBannedTimer(time)
    }
}