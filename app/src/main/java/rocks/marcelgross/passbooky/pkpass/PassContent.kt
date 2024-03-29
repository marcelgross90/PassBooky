package rocks.marcelgross.passbooky.pkpass

import java.util.Date

data class PassContent(
    val formatVersion: Int = 1,
    val serialNumber: String = "",
    val passTypeIdentifier: String = "",
    val description: String = "",
    val teamIdentifier: String = "",
    val organizationName: String = "",
    val foregroundColor: String? = null,
    val backgroundColor: String? = null,
    val labelColor: String? = null,
    val eventTicket: PassFields? = null,
    val coupon: PassFields? = null,
    val storeCard: PassFields? = null,
    val boardingPass: PassFields? = null,
    val generic: PassFields? = null,
    val locations: List<PKLocation> = mutableListOf(),
    val barcodes: List<PKBarcode> = mutableListOf(),
    val barcode: PKBarcode? = null,
    val relevantDate: Date? = null
) {
    val foregroundColorAsPKColor: PKColor
        get() {
            foregroundColor?.let {
                return PKColor(it)
            }
            return PKColor(0, 0, 0)
        }
    val backgroundColorAsPKColor: PKColor
        get() {
            backgroundColor?.let {
                return PKColor(it)
            }
            return PKColor(0, 0, 0)
        }
    val labelColorAsPKColor: PKColor
        get() {
            labelColor?.let {
                return PKColor(it)
            }
            return PKColor(0, 0, 0)
        }

    fun getFields(): PassFields? {
        if (eventTicket != null) {
            return eventTicket
        }
        if (storeCard != null) {
            return storeCard
        }
        return null
    }
}