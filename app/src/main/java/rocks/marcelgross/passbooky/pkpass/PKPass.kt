package rocks.marcelgross.passbooky.pkpass

import android.graphics.drawable.Drawable

data class PKPass(
    var passContent: PassContent = PassContent(),
    var strip: Drawable? = null,
    var logo: Drawable? = null,
    var icon: Drawable? = null,
    var thumbnail: Drawable? = null,
    var background: Drawable? = null
) {
    fun getPassType(): PassType {
        if (passContent.eventTicket != null) {
            return PassType.EVENT_TICKET
        }
        if (passContent.storeCard != null) {
            return PassType.STORE_CARD
        }
        return PassType.UNKNOWN
    }
}