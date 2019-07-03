package rocks.marcelgross.passbooky.pkpass

import android.graphics.drawable.Drawable

data class PKPassFile(
    val pass: PKPass?,
    val strip: Drawable?,
    val logo: Drawable?,
    val icon: Drawable?,
    val thumbnail: Drawable?,
    val background: Drawable?
)