package rocks.marcelgross.passbooky.pkpass

data class PKField(
    val key: String,
    val label: String,
    val value: String?,
    val attributedValue: String?
)