package world.gregs.hestia.social.core.social

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import world.gregs.hestia.core.services.int
import world.gregs.hestia.social.api.Player
import world.gregs.hestia.social.api.Players
import world.gregs.hestia.social.api.Relations

internal class SocialAffiliationsTest {

    private lateinit var player: Player
    private lateinit var friend: Player
    private lateinit var players: Players
    private lateinit var manager: SocialAffiliations
    private lateinit var relations: Relations

    @BeforeEach
    fun setup() {
        player = mock()
        friend = mock()
        players = mock()
        relations = mock()
        manager = SocialAffiliations(players)
        whenever(player.names).thenReturn(mock())
        whenever(friend.names).thenReturn(mock())
        whenever(player.names.name).thenReturn("Player")
        whenever(friend.names.name).thenReturn("Friend")
        whenever(player.relations).thenReturn(relations)
        whenever(friend.friends(player)).thenReturn(true)
        whenever(player.online).thenReturn(true)
        whenever(players.all).thenReturn(listOf(player, friend))
    }

    @Test
    fun `Friend is friends`() {
        //When
        addFriend()
        //Then
        assertFriendAdded(true)
    }

    @Test
    fun `Ignore is ignore`() {
        //When
        addIgnore()
        //Then
        assertIgnoreAdded(true)
    }

    @Test
    fun `Can't ignore a friend`() {
        //Given
        friend()
        //When
        addIgnore()
        //Then
        assertIgnoreAdded(false)
    }

    @Test
    fun `Can't friend a ignore`() {
        //Given
        ignore()
        //When
        addFriend()
        //Then
        assertFriendAdded(false)
    }

    @Test
    fun `Friend removed`() {
        //Given
        friend()
        //When
        removeFriend()
        //Then
        assertFriendRemoved()
    }

    @Test
    fun `Ignore removed`() {
        //Given
        ignore()
        //When
        removeIgnore()
        //Then
        assertIgnoreRemoved()
    }

    @Test
    fun `Add friend doesn't notify if status on`() {
        //Given
        setStatus(0)
        //When
        addFriend()
        //Then
        assertFriendNotified(0)
    }

    @Test
    fun `Add friend notifies if status friend`() {
        //Given
        setStatus(1)
        //When
        addFriend()
        //Then
        assertFriendNotified(1)
    }

    @Test
    fun `Add friend doesn't notify if status private`() {
        //Given
        setStatus(2)
        //When
        addFriend()
        //Then
        assertFriendNotified(0)
    }

    @Test
    fun `Remove friend doesn't notify if status on`() {
        //Given
        setStatus(0)
        //When
        removeFriend()
        //Then
        assertFriendNotified(0)
    }

    @Test
    fun `Remove friend notifies if status friend`() {
        //Given
        setStatus(1)
        //When
        removeFriend()
        //Then
        assertFriendNotified(1)
    }

    @Test
    fun `Remove friend doesn't notify if status private`() {
        //Given
        setStatus(2)
        //When
        removeFriend()
        //Then
        assertFriendNotified(0)
    }

    @Test
    fun `Add ignore notifies if status on`() {
        //Given
        setStatus(0)
        //When
        addIgnore()
        //Then
        assertFriendNotified(1)
    }

    @Test
    fun `Add ignore notifies if status friends`() {
        //Given
        setStatus(1)
        //When
        addIgnore()
        //Then
        assertFriendNotified(1)
    }

    @Test
    fun `Add ignore doesn't notify if status private`() {
        //Given
        setStatus(2)
        //When
        addIgnore()
        //Then
        assertFriendNotified(0)
    }

    @Test
    fun `Remove ignore notifies if status on`() {
        //Given
        setStatus(0)
        //When
        removeIgnore()
        //Then
        assertFriendNotified(1)
    }

    @Test
    fun `Remove ignore notifies if status friends`() {
        //Given
        setStatus(1)
        //When
        removeIgnore()
        //Then
        assertFriendNotified(1)
    }

    @Test
    fun `Remove ignore doesn't notify if status private`() {
        //Given
        setStatus(2)
        //When
        removeIgnore()
        //Then
        assertFriendNotified(0)
    }

    @Test
    fun `Can add a hundred ignores`() {
        //Given
        setIgnoreCount(99)
        //When
        addIgnore()
        //Then
        assertIgnoreAdded(true)
    }

    @Test
    fun `Can't add over a hundred ignores`() {
        //Given
        setIgnoreCount(100)
        //When
        addIgnore()
        //Then
        assertIgnoreAdded(false)
    }

    @Test
    fun `Can add two hundred friends`() {
        //Given
        setFriendCount(199)
        //When
        addFriend()
        //Then
        assertFriendAdded(true)
    }

    @Test
    fun `Can't add over two hundred friends`() {
        //Given
        setFriendCount(200)
        //When
        addFriend()
        //Then
        assertFriendAdded(false)
    }

    /* Settings */
    private fun setFriendCount(count: Int) {
        whenever(relations.friendCount()).thenReturn(count)
    }

    private fun setIgnoreCount(count: Int) {
        whenever(relations.ignoreCount()).thenReturn(count)
    }

    private fun friend() {
        whenever(relations.isFriend(friend.names)).thenReturn(true)
    }

    private fun ignore() {
        whenever(relations.isIgnored(friend.names)).thenReturn(true)
    }

    private fun setStatus(status: Int) {
        whenever(player.status).thenReturn(status)
    }

    /* Actions */

    private fun addFriend() {
        manager.addFriend(player, friend.names)
    }

    private fun addIgnore() {
        manager.addIgnore(player, friend.names, false)
    }

    private fun removeFriend() {
        manager.removeFriend(player, friend.names)
    }

    private fun removeIgnore() {
        manager.removeIgnore(player, friend.names)
    }

    /* Assertions */

    private fun assertFriendNotified(times: Int) {
        verify(friend, times(times)).sendFriend(player.names, null)
    }

    private fun assertFriendAdded(isFriend: Boolean) {
        verify(relations, times(isFriend.int)).addFriend(friend.names)
    }

    private fun assertIgnoreAdded(isIgnore: Boolean) {
        verify(relations, times(isIgnore.int)).addIgnore(friend.names)
    }

    private fun assertFriendRemoved() {
        verify(relations, times(1)).removeFriend(friend.names)
    }

    private fun assertIgnoreRemoved() {
        verify(relations, times(1)).removeIgnore(friend.names)
    }
}