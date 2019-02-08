package world.gregs.hestia.social.network.social.`in`

import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.network.packets.PacketInfo
import world.gregs.hestia.social.api.Player
import world.gregs.hestia.social.core.World
import world.gregs.hestia.social.network.Packets.QUICK_CHAT
import world.gregs.hestia.social.network.PlayerPacket
import world.gregs.hestia.social.network.social.out.PublicChatMessage

@PacketInfo(-1, QUICK_CHAT)
class QuickChatMessage : PlayerPacket {
    override fun read(player: Player, packet: Packet) {
        val script = packet.readByte()
        val fileId = packet.readUnsignedShort()
        var data: ByteArray? = if(packet.readableBytes() > 0) ByteArray(packet.readableBytes()) else null
        if(data != null) {
            packet.readBytes(data)
        }

        //TODO proper system for handling these (prob should be writing to a packet not setting a ByteArray manually
        when(fileId) {
            952 -> {}//SK2 combat level
            526 -> {}//GL1 loyalty points
            547 -> {}//GA7 quest points
            702 -> {}//GEG6 runecrafting level
            1 -> {}//SA2 agility level
            615 -> {}//SA8 diff between agility levels
            8 -> {}//SZ2 attack level
            616 -> {}//SA8 diff between attack levels
            70 -> {}//SL2 constitution level
            626 -> {}//SL6 dif const level
            965 -> {//Life points
                val value = 990
                //Same as writeInt?
                data = byteArrayOf((value shr 24).toByte(), (value shr 16).toByte(), (value shr 8).toByte(), value.toByte())
            }
            13 -> {}//SC2 construction level
            617 -> {}//SC4 dif const
            16 -> {}//SV2 cooking
            618 -> {}//SV8 dif cooking
            23 -> {}//SX2 craft
            619 -> {}//SX8 dif craft
            30 -> {}//SD2 def
            620 -> {}//SD6 dif def
            990 -> {}//SF2 dung
            988 -> {}//SF3 dif dung
            34 -> {}//SQ2 farm
            621 -> {}//SQ8 dif farm
            41 -> {}//SP2 firemaking
            622 -> {}//SP7 dif firemaking
            47 -> {}//SW2 fishing
            623 -> {}//SW5 fishing
            55 -> {}//SE2 fletching
            624 -> {}//SE8 dif fletching
            62 -> {}//SH2 herblore
            625 -> {}//SH8 dif herblore
            74 -> {}//SU2 hunter
            627 -> {}//SU6 dif hunter
            135 -> {}//SM2 magic
            639 -> {}//SM8 dif magic
            127 -> {}//SI2 mining
            638 -> {}//SI8 dif mining
            120 -> {}//SY2 prayer
            637 -> {}//SY5 dif prayer
            116 -> {}//SR2 range
            636 -> {}//SR5 dif range
            111 -> {}//SN2 runecrafting
            635 -> {}//SN6 dif runecrafting
            103 -> {}//SS2 slayer
            634 -> {}//SS8 dif slayer
            96 -> {}//SB2 smithing
            631 -> {}//SB8 dif smithing
            92 -> {}//SG2 strength
            630 -> {}//SG6 dif strength
            85 -> {}//SO2 summoning
            629 -> {}//SO8 dif summoning
            79 -> {}//ST2 thieving
            628 -> {}//ST7 dif thieving
            142 -> {}//SJ2 woodcutting
            640 -> {}//SJ7 dif woodcutting
            906 -> {}//ECOGS2 blue team's avatar level
            907 -> {}//ECOGS3 red team's avatar level
            908 -> {}//ECOGS4 blue team's avatar health
            909 -> {}//ECOGS5 red team's avatar health
            957 -> {}//ECH5 champion challenges complete
            963 -> {}//ECWS1 average clan combat level
            1108 -> {}//ECM1 dominion tower boss kill count
            1109 -> {}//ECM2 dominion factor
            1110 -> {}//ECM3 climber mode floor
            1111 -> {}//ECM4 endurance mode floor
            356 -> {}//ECFC4 fist of guthix rating
            361 -> {}//ECFD5 fist of guthix charges
            604 -> {}//ECSLS3 stealing creation my levels: fishing, hunter, woodcutting, mining
            605 -> {}//ECSLS4 stealing creation my levels: smithing crafting fletching
            606 -> {}//ECSLS5 stealing creation my levels: construction herblore cooking runecrafting
            608 -> {}//ECSLS7 stealing creation my levels: attack strength ranged magic
            609 -> {}//ECSLS8 stealing creation my levels: defence prayer constitution summoning
            611 -> {}//ECSLP2 stealing creation points
            850 -> {}//EFMCM1 mobilising armies rank
            941 -> {}//EFMCM2 mobilising armies investment credits
            942 -> {}//EFMCM3 mobilising armies reward credits
            961 -> {}//EFDF3 familiarisation raw shards collected
            821 -> {}//EFDP2 penguins found this week
            1088 -> {}//ESL1 Livid farm produce
            1089 -> {}//ESL2 Livid farm spells unlocked count
            1093 -> {//CS2 Friends chat member count
                data = byteArrayOf(World.channels?.get(player)?.members?.size?.toByte() ?: 0)
            }
        }

        when(script) {
            0 -> {//Public
                World.players.all.forEach {
                    if(player.index != null) {
                        it.send(PublicChatMessage(player.index!!, 0x8000, player.rights, null, fileId, data))
                    }
                }
            }
            1 -> {//Friends chat
                player.channel?.quickMessage(player, fileId, data)
            }
        }
    }
}