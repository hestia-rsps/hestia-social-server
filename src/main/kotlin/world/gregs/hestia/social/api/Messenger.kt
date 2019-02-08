package world.gregs.hestia.social.api

interface Messenger {
    /**
     * Chat message type (0 = game, 1 = friends, ? = clan, ? = quick)
     */
    var messageType: Int

}