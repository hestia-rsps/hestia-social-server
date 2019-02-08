package world.gregs.hestia.social.model

/**
 * Report types
 * Temp; this information can be obtained from the cache.
 */
enum class ReportTypes(val id: Int, val string: String) {
    ACCOUNT(6, "Buying or selling an account"),
    RULE(9, "Encouraging rule breaking"),
    STAFF(5, "Staff impersonation"),
    MACRO(7, "Macroing or use of bots"),
    SCAM(15, "Scamming"),
    BUG(4, "Exploiting a bug"),
    LANGUAGE(16, "Seriously offensive language"),
    SOLICIT(17, "Solicitation"),
    BEHAVIOUR(18, "Disruptive behaviour"),
    NAME(19, "Offensive account name"),
    THREAT(20, "Real-life threats"),
    CONTACT(13, "Asking for or providing contact information"),
    LAWS(21, "Breaking real-world laws"),
    ADVERTISE(11, "Advertising websites");
}