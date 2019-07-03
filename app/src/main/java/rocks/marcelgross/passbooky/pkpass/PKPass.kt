package rocks.marcelgross.passbooky.pkpass

import android.graphics.drawable.Drawable

data class PKPass(
    var passContent: PassContent = PassContent(),
    var strip: Drawable? = null,
    var logo: Drawable? = null,
    var icon: Drawable? = null,
    var thumbnail: Drawable? = null,
    var background: Drawable? = null
)