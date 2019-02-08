package world.gregs.hestia.social.core.social.channel

import com.nhaarman.mockitokotlin2.mock
import org.junit.jupiter.api.Test
import world.gregs.hestia.social.api.FriendsChat
import world.gregs.hestia.social.core.World

internal class FriendsChatChannelSettingsTest : FriendsChatHelper() {

    /*  Settings changing  */

    @Test
    fun `Can't set if not owner`() {
        //Given
        setupChannel(player = true)
        //Then
        assertSetJoin(FriendsChat.Ranks.GENERAL, null)
        assertSetTalk(FriendsChat.Ranks.GENERAL, null)
        assertSetKick(FriendsChat.Ranks.GENERAL, null)
        assertSetLoot(FriendsChat.Ranks.GENERAL, null)
    }

    @Test
    fun `Who can enter chat?`() {
        //Given
        setupChannel(player = true)
        //When
        rank(FriendsChat.Ranks.OWNER)
        //Then
        FriendsChat.Ranks.values().forEach {
            assertSetJoin(it, if(it == FriendsChat.Ranks.NO_ONE) null else it)
        }
    }

    @Test
    fun `Who can talk on chat?`() {
        //Given
        setupChannel(player = true)
        //When
        rank(FriendsChat.Ranks.OWNER)
        //Then
        FriendsChat.Ranks.values().forEach {
            assertSetTalk(it, if(it == FriendsChat.Ranks.NO_ONE) null else it)
        }
    }

    @Test
    fun `Who can kick from chat?`() {
        //Given
        setupChannel(player = true)
        //When
        rank(FriendsChat.Ranks.OWNER)
        //Then
        FriendsChat.Ranks.values().forEach {
            assertSetKick(it, if(it.value < FriendsChat.Ranks.CORPOREAL.value) null else it)
        }
    }

    @Test
    fun `Who can share loot?`() {
        //Given
        setupChannel(player = true)
        //When
        rank(FriendsChat.Ranks.OWNER)
        //Then
        FriendsChat.Ranks.values().forEach {
            assertSetLoot(it, if(it == FriendsChat.Ranks.ANYONE || it == FriendsChat.Ranks.OWNER) null else it)
        }
    }

    /*  Delayed refreshing  */

    @Test
    fun `Refresh all settings`() {
        //Given
        setupChannel(player = true, friend = true)
        rank(FriendsChat.Ranks.OWNER)
        rename()
        //When
        update()
        //Then
        assertDetails(1)
        assertDetails(1, friend)
        assertDetails(0, target)
    }

    @Test
    fun `Refresh individual settings`() {
        //Given
        setupChannel(player = true, friend = true)
        World.players = mock()
        rank(FriendsChat.Ranks.OWNER)
        friend(friend)
        channel.promote(player, friend.names, 3)
        //When
        update()
        //Then
        assertDetailChange(1)
        assertDetailChange(1, friend)
        assertDetailChange(0, target)
    }
}