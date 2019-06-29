package rocks.marcelgross.passbooky.pkpass

import android.graphics.drawable.Drawable
import java.util.Date

data class PKPass(
    val formatVersion: Int,
    val serialNumber: String,
    val passTypeIdentifier: String,
    val description: String,
    val teamIdentifier: String,
    val organizationName: String,
    val foregroundColor: String?,
    val backgroundColor: String?,
    val labelColor: String?,
    val eventTicket: PassContent?,
    val coupon: PassContent?,
    val storeCard: PassContent?,
    val boardingPass: PassContent?,
    val generic: PassContent?,
    val barcodes: List<PKBarcode>,
    val relevantDate: Date?
) {
    var logo: Drawable? = null
    var strip: Drawable? = null
    var background: Drawable? = null
    var thumbnail: Drawable? = null
    val foregroundColorAsColor: Color
        get() {
            foregroundColor?.let {
                return Color(it)
            }
            return Color(0,0,0)
        }
    val backgroundColorAsColor: Color
        get() {
            backgroundColor?.let {
                return Color(it)
            }
            return Color(0,0,0)
        }
    val labelColorAsColor: Color
        get() {
            labelColor?.let {
                return Color(it)
            }
            return Color(0,0,0)
        }

}