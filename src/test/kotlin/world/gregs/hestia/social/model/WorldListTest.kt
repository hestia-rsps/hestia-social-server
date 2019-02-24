package world.gregs.hestia.social.model

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import world.gregs.hestia.core.world.Details
import world.gregs.hestia.core.world.Worlds

internal class WorldListTest {

    private lateinit var worlds: Worlds
    private lateinit var info: Details

    @BeforeEach
    fun startup() {
        worlds = WorldList()
        info = mock()
    }

    @Test
    fun `Add world with id generation`() {
        //Given
        worlds.add(mock())
        //When
        worlds.add(info)
        //Then
        assertWorldCount(2)
        assertWorldId(1)
    }

    @Test
    fun `Set world with id`() {
        //When
        worlds.set(2, info)
        //Then
        assertWorldCount(1)
        assertWorldId(2)
    }

    @Test
    fun `Already set id doesn't break id generation`() {
        //Given
        worlds.set(0, mock())
        //When
        worlds.add(info)
        //Then
        assertWorldCount(2)
        assertWorldId(1)
    }

    @Test
    fun `Can override existing world with set`() {
        //TODO should this be allowed? What would the alternative be?
        //Given
        worlds.add(mock())
        //When
        worlds.set(0, info)
        //Then
        assertWorldCount(2)
        assertWorldId(0)
    }

    private fun assertWorldCount(count: Int) {
        assertThat(worlds.worlds.size).isEqualTo(count)
    }

    private fun assertWorldId(id: Int) {
        verify(info, times(1)).id = id
    }
}