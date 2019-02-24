package world.gregs.hestia.social.core.social

import com.nhaarman.mockitokotlin2.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import world.gregs.hestia.core.network.protocol.encoders.messages.FriendListUnlock
import world.gregs.hestia.social.api.Player
import world.gregs.hestia.social.api.Players
import world.gregs.hestia.social.api.Relations
import world.gregs.hestia.social.network.social.encoders.messages.IgnoreListUnlock

internal class SocialPresenceTest {

    private lateinit var player: Player
    private lateinit var friend: Player
    private lateinit var players: Players
    private lateinit var relations: Relations
    private lateinit var manager: SocialPresence

    @BeforeEach
    fun setup() {
        player = mock()
        friend = mock()
        players = mock()
        relations = mock()
        manager = SocialPresence(players)
        whenever(player.relations).thenReturn(relations)
    }

    @Test
    fun `Connect sends online friends`() {
        //Given
        connectFriend()
        //When
        manager.connect(player)
        //Then
        assertFriend(1)
    }

    @Test
    fun `Connect no friends sends unlock`() {
        //When
        manager.connect(player)
        //Then
        assertFriend(0)
        assertFriendUnlock(1)
    }

    @Test
    fun `Connect sends online ignores`() {
        //Given
        connectIgnore()
        //When
        manager.connect(player)
        //Then
        assertIgnore(1)
    }

    @Test
    fun `Connect no ignores sends unlock`() {
        //When
        manager.connect(player)
        //Then
        assertIgnore(0)
        assertIgnoreUnlock(1)
    }

    private fun connectFriend() {
        whenever(relations.friendCount()).thenReturn(1)
    }

    private fun connectIgnore() {
        whenever(relations.ignoreCount()).thenReturn(1)
    }

    private fun assertFriendUnlock(times: Int) {
        verify(player, times(times)).send(any<FriendListUnlock>(), any())
    }

    private fun assertIgnoreUnlock(times: Int) {
        verify(player, times(times)).send(any<IgnoreListUnlock>(), any())
    }

    private fun assertFriend(times: Int) {
        verify(player, times(times)).sendFriends(any())
    }

    private fun assertIgnore(times: Int) {
        verify(player, times(times)).sendIgnores()
    }
}