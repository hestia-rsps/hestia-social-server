package world.gregs.hestia.ls.network.login.out

import world.gregs.hestia.core.network.packets.Packet

class UnlockFriendList : Packet.Builder(85, Packet.Type.VAR_SHORT)