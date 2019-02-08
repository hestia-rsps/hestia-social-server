package world.gregs.hestia.social.core.social.channel

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Test
import world.gregs.hestia.social.api.FriendsChat
import world.gregs.hestia.social.api.Player
import world.gregs.hestia.social.core.Duration.MINUTE

internal class FriendsChatChannelJoinTest : FriendsChatHelper() {
    /*  Join  */

    @Test
    fun `Can join active channel`() {
        //Given
        setupChannel()
        //When
        join()
        //Then
        assertJoined(true)
    }

    @Test
    fun `Can't join inactive channel`() {
        //When
        join()
        //Then
        assertJoined(false)
    }

    @Test
    fun `Can't join if in another channel`() {
        //Given
        setupChannel()
        whenever(player.channel).thenReturn(mock())
        //When
        join()
        //Then
        assertJoined(false)
    }

    @Test
    fun `Can't join if ignored`() {
        //Given
        setupChannel()
        ignore()
        //When
        join()
        //Then
        assertJoined(false)
    }

    @Test
    fun `Can't join if banned`() {
        //Given
        setupChannel()
        ban(MINUTE)
        //When
        join()
        //Then
        assertJoined(false)
    }

    @Test
    fun `Can't join twice`() {
        //Given
        setupChannel()
        addMember(player)
        //When
        join()
        //Then
        assertJoined(false)
    }

    @Test
    fun `Can join if ban elapsed`() {
        //Given
        setupChannel()
        ban(-MINUTE)
        //When
        join()
        //Then
        assertJoined(true)
    }

    @Test
    fun `Can't join if under rank`() {
        //Given
        setupChannel()
        joinRank(FriendsChat.Ranks.RECRUIT)
        //When
        join()
        //Then
        assertJoined(false)
    }

    @Test
    fun `Can join if ranked`() {
        //Given
        setupChannel()
        joinRank(FriendsChat.Ranks.RECRUIT)
        rank(FriendsChat.Ranks.RECRUIT)
        //When
        join()
        //Then
        assertJoined(true)
    }

    @Test
    fun `Can join if over ranked`() {
        //Given
        setupChannel()
        joinRank(FriendsChat.Ranks.RECRUIT)
        rank(FriendsChat.Ranks.OWNER)
        //When
        join()
        //Then
        assertJoined(true)
    }

    @Test
    fun `Can join if under rank and admin`() {
        //Given
        setupChannel()
        joinRank(FriendsChat.Ranks.RECRUIT)
        admin(true)
        //When
        join()
        //Then
        assertJoined(true)
    }

    @Test
    fun `Can't join if full`() {
        //Given
        setupChannel()
        rank(FriendsChat.Ranks.ANYONE)
        for(i in 0 until 100) {
            val p = mock<Player>()
            username(i.toString(), p)
            rank(FriendsChat.Ranks.FRIEND, p)
            addMember(p)
        }
        //When
        join()
        //Then
        assertJoined(false)
    }

    @Test
    fun `Can join if full but ranked and lowest rank kicked`() {
        //Given
        setupChannel(friend = true)
        rank(FriendsChat.Ranks.FRIEND, friend)
        for(i in 1 until 100) {
            val p = mock<Player>()
            username(i.toString(), p)
            rank(FriendsChat.Ranks.RECRUIT, p)
            addMember(p)
        }
        rank(FriendsChat.Ranks.RECRUIT)
        //When
        join()
        //Then
        assertJoined(true)
        assertKicked(true, friend)
    }

    @Test
    fun `Can't join if ranked and equal rank to lowest`() {
        //Given
        setupChannel(friend = true)
        rank(FriendsChat.Ranks.RECRUIT, friend)
        for(i in 2 until 101) {
            val p = mock<Player>()
            username(i.toString(), p)
            rank(FriendsChat.Ranks.CORPOREAL, p)
            addMember(p)
        }
        rank(FriendsChat.Ranks.RECRUIT)
        //When
        join()
        //Then
        assertJoined(false)
        assertKicked(false, friend)
    }
}