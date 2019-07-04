package rocks.marcelgross.passbooky.zxing

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import rocks.marcelgross.passbooky.pkpass.PKBarcodeFormat
import java.util.EnumMap

fun encodeAsBitmap(
    text: String,
    format: BarcodeFormat
): Bitmap? {
    val hints = EnumMap<EncodeHintType, Any>(EncodeHintType::class.java)
    hints[EncodeHintType.MARGIN] = 0
    //todo use different sizes for PDF_417
    val size = 384
    val result = MultiFormatWriter().encode(
        text,
        format,
        size,
        size,
        hints
    )
    val w = result.width
    val h = result.height
    val pixels = IntArray(w * h)
    var offset = 0
    for (y in 0 until h) {
        for (x in 0 until w) {
            pixels[offset + x] = if (result.get(x, y)) {
                0xff000000.toInt() // black
            } else {
                0xffffffff.toInt() // white
            }
        }
        offset += w
    }
    val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
    bitmap.setPixels(pixels, 0, size, 0, 0, w, h)
    return bitmap
}

fun PKBarcodeFormat.getZxingFormat(): BarcodeFormat {
    return when (this) {
        PKBarcodeFormat.PKBarcodeFormatQR -> BarcodeFormat.QR_CODE
        PKBarcodeFormat.PKBarcodeFormatPDF417 -> BarcodeFormat.PDF_417
        PKBarcodeFormat.PKBarcodeFormatAztec -> BarcodeFormat.AZTEC
        PKBarcodeFormat.PKBarcodeFormatCode128 -> BarcodeFormat.CODE_128
    }
}