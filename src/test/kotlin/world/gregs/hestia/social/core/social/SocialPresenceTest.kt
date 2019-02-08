package world.gregs.hestia.social.core.social

import com.nhaarman.mockitokotlin2.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import world.gregs.hestia.social.api.Player
import world.gregs.hestia.social.api.Players
import world.gregs.hestia.social.api.Relations

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
    fun `Player connect sends online friends`() {
        //Given
        connectFriend()
        //When
        manager.connect(player)
        //Then
        assertFriend(1)
        assertIgnoresSent()
    }

    private fun connectFriend() {
        whenever(relations.friendCount()).thenReturn(1)
    }

    private fun assertFriend(times: Int) {
        verify(player, times(times)).sendFriends(any())
    }

    private fun assertIgnoresSent() {
        verify(player, times(1)).sendIgnores()
    }
}