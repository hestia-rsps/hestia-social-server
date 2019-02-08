package world.gregs.hestia.social.core.player

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import world.gregs.hestia.core.network.Session
import world.gregs.hestia.social.api.Name
import world.gregs.hestia.social.api.Relations

internal class PlayerImplTest {

    private lateinit var friendsName: Name
    private lateinit var player: PlayerImpl
    private lateinit var relations: Relations

    @BeforeEach
    fun setup() {
        relations = mock()
        friendsName = mock()
        player = PlayerImpl(Session(), mock(), relations)
        setStatus(0)
    }

    @Test
    fun `Online Status online`() {
        //Given
        addFriend()
        //Then
        assertStatus(true)
    }

    @Test
    fun `Online Status false if status friend but isn't friend`() {
        //Given
        setStatus(1)
        //Then
        assertStatus(false)
    }

    @Test
    fun `Online Status true if status friend and is friend`() {
        //Given
        setStatus(1)
        addFriend()
        //Then
        assertStatus(true)
    }

    @Test
    fun `Online Status false if status friend but has private`() {
        //Given
        setStatus(2)
        addFriend()
        //Then
        assertStatus(false)
    }

    @Test
    fun `Online Status true if status on but isn't friend`() {
        //Given
        setStatus(0)
        //Then
        assertStatus(true)
    }

    @Test
    fun `Online Status false if status on but ignored`() {
        //Given
        setStatus(0)
        addIgnore()
        //Then
        assertStatus(false)
    }

    private fun addFriend() {
        whenever(relations.isFriend(friendsName)).thenReturn(true)
    }

    private fun addIgnore() {
        whenever(relations.isIgnored(friendsName)).thenReturn(true)
    }

    private fun setStatus(status: Int) {
        player.status = status
    }

    private fun assertStatus(online: Boolean) {
        assertThat(player.statusOnline(friendsName)).isEqualTo(online)
    }
}