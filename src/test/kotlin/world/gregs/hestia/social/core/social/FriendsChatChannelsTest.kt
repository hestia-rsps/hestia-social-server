package world.gregs.hestia.social.core.social

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import world.gregs.hestia.core.services.int
import world.gregs.hestia.social.api.FriendChannels
import world.gregs.hestia.social.api.FriendsChat
import world.gregs.hestia.social.api.Name
import world.gregs.hestia.social.api.Player
import world.gregs.hestia.social.core.player.Names

internal class FriendsChatChannelsTest {

    private lateinit var channels: FriendChannels
    private lateinit var channel: FriendsChat
    private lateinit var player: Player
    private lateinit var name: Name

    @BeforeEach
    fun setup() {
        channels = FriendsChatChannels(mock())
        channel = mock()
        player = mock()
        name = mock()
        whenever(name.name).thenReturn("Player")
        whenever(player.names).thenReturn(name)
        (channels as FriendsChatChannels).channels.add(channel)
    }

    @Test
    fun `Not blocked if not attempted`() {
        assertBlocked(false)
    }

    @Test
    fun `Blocked if over attempt limit`() {
        //Given
        increment(10)
        //Then
        assertBlocked(true)
    }

    @Test
    fun `Clean removes all block progress`() {
        //Given
        increment(10)
        //When
        clear()
        //Then
        assertBlocked(false)
    }

    @Test
    fun `Re-join same channel refreshes display`() {
        //Given
        channel()
        whenever(player.channel).thenReturn(channel)
        //When
        join()
        //Then
        assertDisplay(true)
    }
    @Test
    fun `Can't join if already in a channel`() {
        //Given
        channel()
        whenever(player.channel).thenReturn(mock())
        //When
        join()
        //Then
        assertJoined(false)
    }

    @Test
    fun `Can join if channel exists`() {
        //Given
        channel()
        //When
        join()
        //Then
        assertJoined(true)
    }

    @Test
    fun `Can't join non-existing channel`() {
        //Given
        channel()
        //When
        join(Names("Nwner"))
        //Then
        assertJoined(false)
    }

    @Test
    fun `Can setup if owner`() {
        //Given
        channel()
        //Then
        assertSetup(true)
    }

    @Test
    fun `Can't setup if member`() {
        //Given
        channel()
        //When
        member()
        owner(false)
        //Then
        assertSetup(false)
    }

    private fun join(name: Name = this.name) {
        channels.join(player, name)
    }

    private fun channel() {
        (channels as FriendsChatChannels).channels.add(channel)
        owner(true)
        whenever(channel.channelName).thenReturn("Name")
    }

    private fun owner(owner: Boolean) {
        whenever(channel.owner).thenReturn(if(owner) name else mock())
    }

    private fun member() {
        whenever(player.channel).thenReturn(channel)
    }

    private fun clear() {
        channels.clearBlocks()
    }

    private fun increment(times: Int) {
        for(i in 0 until times) {
            channels.increment(name)
        }
    }

    private fun assertSetup(setup: Boolean) {
        assertThat(channels.canSetup(player)).isEqualTo(setup)
    }

    private fun assertDisplay(displayed: Boolean) {
        verify(channel, times(displayed.int)).display(player)
    }

    private fun assertJoined(joined: Boolean) {
        verify(channel, times(joined.int)).join(player)
    }

    private fun assertBlocked(blocked: Boolean) {
        assertThat(channels.isBlocked(name)).isEqualTo(blocked)
    }
}