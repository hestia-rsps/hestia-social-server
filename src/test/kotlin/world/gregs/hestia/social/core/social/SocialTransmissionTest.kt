package world.gregs.hestia.social.core.social

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import world.gregs.hestia.social.api.Player
import world.gregs.hestia.social.api.Players

internal class SocialTransmissionTest {

    private lateinit var player: Player
    private lateinit var friend: Player
    private lateinit var players: Players
    private lateinit var manager: SocialTransmission

    @BeforeEach
    fun setup() {
        player = mock()
        friend = mock()
        players = mock()
        manager = SocialTransmission(players)
        whenever(players.all).thenReturn(listOf(friend))
        whenever(player.names).thenReturn(mock())
        whenever(player.names.name).thenReturn("Player")
        ignoreFriend(false)
        friendAdmin(false)
        playerStatus(true)
    }

    @Test
    fun `Player connecting doesn't notify if status private`() {
        //Given
        playerIsFriend(true)
        playerStatus(false)
        //When
        manager.connect(player)
        //Then
        assertFriendUpdated(0)
    }

    @Test
    fun `Player connecting notifies friend`() {
        //Given
        playerIsFriend(true)
        //When
        manager.connect(player)
        //Then
        assertFriendUpdated(1)
    }

    @Test
    fun `Player connecting doesn't notify ignored friend`() {
        //Given
        playerIsFriend(true)
        ignoreFriend(true)
        //When
        manager.connect(player)
        //Then
        assertFriendUpdated(0)
    }

    @Test
    fun `Player connecting notifies ignored and private friend if admin`() {
        //Given
        playerIsFriend(true)
        ignoreFriend(true)
        friendAdmin(true)
        playerStatus(false)
        //When
        manager.connect(player)
        //Then
        assertFriendUpdated(1)
    }

    @Test
    fun `Player connecting doesn't notify stranger`() {
        //Given
        playerIsFriend(false)
        ignoreFriend(true)
        friendAdmin(true)
        //When
        manager.connect(player)
        //Then
        assertFriendUpdated(0)
    }

    private fun playerStatus(online: Boolean) {
        whenever(player.statusOnline(friend)).thenReturn(online)
    }

    private fun playerIsFriend(boolean: Boolean) {
        whenever(friend.friends(player)).thenReturn(boolean)
    }

    private fun ignoreFriend(boolean: Boolean) {
        whenever(player.ignores(friend)).thenReturn(boolean)
    }

    private fun friendAdmin(boolean: Boolean) {
        whenever(friend.admin).thenReturn(boolean)
    }

    private fun assertFriendUpdated(times: Int) {
        verify(friend, times(times)).sendFriend(player.names, null)
    }
}