package rocks.marcelgross.passbooky.customComponents

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import rocks.marcelgross.passbooky.R
import rocks.marcelgross.passbooky.pkpass.PKBarcode
import rocks.marcelgross.passbooky.zxing.encodeAsBitmap
import rocks.marcelgross.passbooky.zxing.getZxingFormat

class BarcodeView : ConstraintLayout {

    private lateinit var barCodeContent: TextView
    private lateinit var barcodeImg: ImageView

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(context)
    }

    fun setUpView(barcode: PKBarcode) {
        if (barcode.altText != null) {
            barCodeContent.text = barcode.altText
        } else {
            barCodeContent.visibility = View.GONE
        }

        val bitmap: Bitmap?
        try {
            bitmap = encodeAsBitmap(
                barcode.message,
                barcode.format.getZxingFormat()
            )
            bitmap?.let {
                barcodeImg.setImageBitmap(bitmap)
            }
        } catch (e: Exception) {
            Toast.makeText(
                context,
                R.string.barcode_error,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun init(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        this.addView(inflater.inflate(R.layout.barcode, this, false))

        barCodeContent = findViewById(R.id.barcodeContent)
        barcodeImg = findViewById(R.id.barcodeImg)
    }
}