package rocks.marcelgross.passbooky.pkpass

data class PassFields(
    val headerFields: List<PKField> = mutableListOf(),
    val primaryFields: List<PKField> = mutableListOf(),
    val secondaryFields: List<PKField> = mutableListOf(),
    val auxiliaryFields: List<PKField> = mutableListOf(),
    val backFields: List<PKField> = mutableListOf()
)