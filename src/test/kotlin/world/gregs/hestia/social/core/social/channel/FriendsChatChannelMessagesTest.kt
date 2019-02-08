package world.gregs.hestia.social.core.social.channel

import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Test
import world.gregs.hestia.social.api.FriendsChat

internal class FriendsChatChannelMessagesTest : FriendsChatHelper() {

    /*  Messaging  */

    @Test
    fun `Can talk if in a channel`() {
        //Given
        setupChannel(true)
        //When
        message()
        quickMessage()
        //Then
        assertMessage(1)
        assertQuickMessage(1)
    }

    @Test
    fun `Can't talk if not in a channel`() {
        //Given
        admin(true)
        //When
        message()
        quickMessage()
        //Then
        assertMessage(0)
        assertQuickMessage(0)
    }

    @Test
    fun `Can't talk if under rank`() {
        //Given
        setupChannel(player = true)
        talkRank(FriendsChat.Ranks.RECRUIT)
        //When
        message()
        quickMessage()
        //Then
        assertMessage(0)
        assertQuickMessage(0)
    }

    @Test
    fun `Can talk if under rank and admin`() {
        //Given
        setupChannel(player = true)
        talkRank(FriendsChat.Ranks.RECRUIT)
        admin(true)
        //When
        message()
        quickMessage()
        //Then
        assertMessage(1)
        assertQuickMessage(1)
    }

    @Test
    fun `Can talk ranked`() {
        //Given
        setupChannel(player = true)
        talkRank(FriendsChat.Ranks.RECRUIT)
        rank(FriendsChat.Ranks.RECRUIT)
        //When
        message()
        quickMessage()
        //Then
        assertMessage(1)
        assertQuickMessage(1)
    }

    @Test
    fun `Can talk over ranked`() {
        //Given
        setupChannel(player = true)
        talkRank(FriendsChat.Ranks.RECRUIT)
        rank(FriendsChat.Ranks.OWNER)
        //When
        message()
        quickMessage()
        //Then
        assertMessage(1)
        assertQuickMessage(1)
    }

    @Test
    fun `Member receives message`() {
        //Given
        setupChannel(player = true, friend = true)
        //When
        message()
        quickMessage()
        //Then
        assertMessage(1, friend)
        assertQuickMessage(1, friend)
    }

    @Test
    fun `Ignored member doesn't receive message`() {
        //Given
        setupChannel(player = true, friend = true)
        whenever(friend.ignores(player)).thenReturn(true)
        //When
        message()
        quickMessage()
        //Then
        assertMessage(0, friend)
        assertQuickMessage(0, friend)
    }
}