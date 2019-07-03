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
    val eventTicket: PassStructure? = null,
    val coupon: PassStructure? = null,
    val storeCard: PassStructure? = null,
    val boardingPass: PassStructure? = null,
    val generic: PassStructure? = null,
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
}