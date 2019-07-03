package rocks.marcelgross.passbooky.pkpass

import android.graphics.Color

class PKColor {
    val red: Int
    val green: Int
    val blue: Int

    constructor(
        input: String
    ) {
        val rgbValues = input.replace("rgb(", "")
            .replace(")", "")
        val rgb = rgbValues.split(",")
        red = rgb[0].trim().toInt()
        green = rgb[1].trim().toInt()
        blue = rgb[2].trim().toInt()
    }

    constructor(
        red: Int,
        green: Int,
        blue: Int
    ) {
        this.red = red
        this.green = green
        this.blue = blue
    }
}

fun PKColor.asColor() =
    Color.rgb(
        this.red,
        this.green,
        this.blue
    )
