package rocks.marcelgross.passbooky.zxing

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import rocks.marcelgross.passbooky.pkpass.PKBarcodeFormat

class Zxing {

    companion object {
        private const val BLACK = 0xff000000.toInt()
        private const val WHITE = 0xffffffff.toInt()

        fun encodeAsBitmap(
            text: String,
            format: BarcodeFormat,
            width: Int,
            height: Int
        ): Bitmap? {
            val result = MultiFormatWriter().encode(
                text,
                format,
                width,
                height,
                null
            )
            val w = result.width
            val h = result.height
            val pixels = IntArray(w * h)
            var offset = 0
            for (y in 0 until h) {
                for (x in 0 until w) {
                    pixels[offset + x] = if (result.get(x, y)) {
                        BLACK
                    } else {
                        WHITE
                    }
                }
                offset += w
            }
            val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
            bitmap.setPixels(pixels, 0, width, 0, 0, w, h)
            return bitmap
        }
    }
}

fun PKBarcodeFormat.getZxingFormat(): BarcodeFormat {
    return when (this) {
        PKBarcodeFormat.PKBarcodeFormatQR -> BarcodeFormat.QR_CODE
        PKBarcodeFormat.PKBarcodeFormatPDF417 -> BarcodeFormat.PDF_417
        PKBarcodeFormat.PKBarcodeFormatAztec -> BarcodeFormat.AZTEC
        PKBarcodeFormat.PKBarcodeFormatCode128 -> BarcodeFormat.CODE_128
    }
}