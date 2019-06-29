package rocks.marcelgross.passbooky.pkpass

data class PassContent (
    val headerFields: List<PKField>,
    val primaryFields: List<PKField>,
    val secondaryFields: List<PKField>,
    val auxiliaryFields: List<PKField>,
    val backFields: List<PKField>
)





