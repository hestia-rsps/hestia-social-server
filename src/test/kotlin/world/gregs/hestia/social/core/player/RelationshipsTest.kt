package world.gregs.hestia.social.core.player

import com.nhaarman.mockitokotlin2.mock
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import world.gregs.hestia.social.api.FriendsChat
import world.gregs.hestia.social.api.Name
import world.gregs.hestia.social.api.Relations

internal class RelationshipsTest {

    private lateinit var names: Name
    private lateinit var relations: Relations

    @BeforeEach
    fun setup() {
        names = mock()
        relations = Relationships()
    }

    @Test
    fun `Friend is friend`() {
        //Given
        friend()
        //Then
        assertFriend(true)
        assertRank(FriendsChat.Ranks.FRIEND)
    }

    @Test
    fun `Existing friend rank is override`() {
        //Given
        friend()
        //When
        friend(FriendsChat.Ranks.RECRUIT)
        //Then
        assertFriend(true)
        assertRank(FriendsChat.Ranks.RECRUIT)
    }

    @Test
    fun `Ignore is ignored`() {
        //Given
        ignore()
        //Then
        assertIgnored(true)
    }

    @Test
    fun `Temporary ignore is removed`() {
        //Given
        ignore(true)
        //When
        clearTemps()
        //Then
        assertIgnored(false)
    }

    private fun friend(rank: FriendsChat.Ranks = FriendsChat.Ranks.FRIEND) {
        relations.addFriend(names, rank)
    }

    private fun ignore(temp: Boolean = false) {
        relations.addIgnore(names, temp)
    }

    private fun clearTemps() {
        relations.removeIgnores()
    }

    private fun assertFriend(friend: Boolean) {
        assertThat(relations.isFriend(names)).isEqualTo(friend)
    }

    private fun assertIgnored(ignored: Boolean) {
        assertThat(relations.isIgnored(names)).isEqualTo(ignored)
    }

    private fun assertRank(rank: FriendsChat.Ranks) {
        assertThat(relations.getFriend(names)).isEqualTo(rank)
    }
}