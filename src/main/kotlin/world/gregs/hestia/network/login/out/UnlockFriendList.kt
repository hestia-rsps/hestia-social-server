package world.gregs.hestia.network.login.out

import world.gregs.hestia.network.packets.Packet

class UnlockFriendList : Packet.Builder(85, Packet.Type.VAR_SHORT)