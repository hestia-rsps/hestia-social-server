package world.gregs.hestia.social.core.social

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import world.gregs.hestia.social.api.Player
import world.gregs.hestia.social.api.Players

internal class SocialStatusTest {
    private lateinit var player: Player
    private lateinit var friend: Player
    private lateinit var players: Players
    private lateinit var status: SocialStatus

    @BeforeEach
    fun setup() {
        player = mock()
        friend = mock()
        players = mock()
        status = SocialStatus(players)
        whenever(player.names).thenReturn(mock())
        whenever(friend.names).thenReturn(mock())
        whenever(player.names.name).thenReturn("Player")
        whenever(friend.names.name).thenReturn("Friend")
        connectFriend()
        whenever(players.all).thenReturn(listOf(friend))
        whenever(friend.friends(player)).thenReturn(true)
    }

    /*
        Private -> on
        Private -> friend
        Private -> private
        Friend -> on
        Friend -> friend
        Friend -> private
        On -> on
        On -> friend
        On -> private
     */
    @Test
    fun `Set status offline`() {
        //When
        changeStatus(1)
        //Then
        assertStatus(1, 1)
    }

    @Test
    fun `Set status online`() {
        //Given
        connectPlayer()
        //When
        changeStatus(1)
        //Then
        assertStatus(1, 1)
    }

    @Test
    fun `Status changed from private to on notifies friends`() {
        //Given
        setStatus(2)
        connectPlayer()
        addFriend()
        //When
        changeStatus(0)
        //Then
        assertFriend(1)
    }

    @Test
    fun `Status changed from private to friend notifies friends`() {
        //Given
        setStatus(2)
        connectPlayer()
        addFriend()
        //When
        changeStatus(1)
        //Then
        assertFriend(1)
    }

    @Test
    fun `Status changed from private to friend doesn't notify stranger`() {
        //Given
        setStatus(2)
        connectPlayer()
        //When
        changeStatus(1)
        //Then
        assertFriend(0)
    }

    @Test
    fun `Status changed from private to private does nothing`() {
        //Given
        setStatus(2)
        connectPlayer()
        //When
        changeStatus(2)
        //Then
        assertFriend(0)
        assertStatus(0, 2)
    }

    @Test
    fun `Status changed from friend to on notifies stranger`() {
        //Given
        setStatus(1)
        connectPlayer()
        //When
        changeStatus(0)
        //Then
        assertFriend(1)
    }

    @Test
    fun `Status changed from friend to on doesn't notify friend`() {
        //Given
        setStatus(1)
        connectPlayer()
        addFriend()
        //When
        changeStatus(0)
        //Then
        assertFriend(0)
    }

    @Test
    fun `Status changed from friend to friend does nothing`() {
        //Given
        setStatus(1)
        connectPlayer()
        addFriend()
        //When
        changeStatus(1)
        //Then
        assertFriend(0)
        assertStatus(0, 1)
    }

    @Test
    fun `Status changed from friend to private notifies all friends`() {
        //Given
        setStatus(1)
        connectPlayer()
        //When
        changeStatus(2)
        //Then
        assertFriend(1)
    }

    @Test
    fun `Status changed from on to on does nothing`() {
        //Given
        setStatus(0)
        connectPlayer()
        //When
        changeStatus(0)
        //Then
        assertFriend(0)
    }

    @Test
    fun `Status changed from on to friend notifies strangers`() {
        //Given
        setStatus(0)
        connectPlayer()
        //When
        changeStatus(1)
        //Then
        assertFriend(1)
    }

    @Test
    fun `Status changed from on to friend doesn't notify friends`() {
        //Given
        setStatus(0)
        connectPlayer()
        addFriend()
        //When
        changeStatus(1)
        //Then
        assertFriend(0)
    }

    @Test
    fun `Status changed from on to private notifies all`() {
        //Given
        setStatus(0)
        connectPlayer()
        //When
        changeStatus(2)
        //Then
        assertFriend(1)
    }

    private fun changeStatus(status: Int) {
        this.status.setStatus(player, status)
    }

    private fun setStatus(status: Int) {
        whenever(player.status).thenReturn(status)
    }

    private fun addFriend() {
        whenever(player.friends(friend)).thenReturn(true)
    }

    private fun connectFriend() {
        whenever(players.get(friend.names)).thenReturn(friend)
    }

    private fun connectPlayer() {
        whenever(player.online).thenReturn(true)
    }

    private fun assertFriend(times: Int) {
        verify(friend, times(times)).sendFriend(player.names, null)
    }

    private fun assertStatus(times: Int, value: Int) {
        verify(player, times(times)).status = value
    }

}