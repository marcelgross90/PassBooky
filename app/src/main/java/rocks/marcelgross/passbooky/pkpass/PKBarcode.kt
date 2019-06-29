package rocks.marcelgross.passbooky.pkpass

data class PKBarcode(
    val format: PKBarcodeFormat,
    val altText: String?,
    val message: String,
    val messageEncoding: String
)