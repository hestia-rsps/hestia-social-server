package world.gregs.hestia.social.core.player

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import world.gregs.hestia.social.api.Player
import world.gregs.hestia.social.api.Players

internal class PlayersImplTest {
    private lateinit var players: Players
    private lateinit var player: Player

    @BeforeEach
    fun setup() {
        players = PlayersImpl()
        player = mock()
        whenever(player.names).thenReturn(mock())
        whenever(player.names.name).thenReturn("Player")
    }

    @Test
    fun `Connect successful`() {
        //When
        players.add(player)
        //Then
        verify(player, times(1)).worldId = 0
    }

    @Test
    fun `Connect unsuccessful`() {
        //Given
        players.add(player)
        //When
        players.add(player)
        //Then
        verify(player, times(1)).worldId = 0
    }

    @Test
    fun `Disconnect successful`() {
        //Given
        players.add(player)
        //When
        players.remove(player)
        //Then
        verify(player, times(1)).worldId = -1
    }

    @Test
    fun `Disconnect unsuccessful`() {
        //When
        players.remove(player)
        //Then
        verify(player, times(0)).worldId = -1
    }

}