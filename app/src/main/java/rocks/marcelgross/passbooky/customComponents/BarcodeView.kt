package rocks.marcelgross.passbooky.customComponents

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import rocks.marcelgross.passbooky.R
import rocks.marcelgross.passbooky.pkpass.PKBarcode

class BarcodeView : ConstraintLayout {

    private lateinit var barCodeContent: TextView

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
    }

    private fun init(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        this.addView(inflater.inflate(R.layout.barcode, this, false))

        barCodeContent = findViewById(R.id.barcodeContent)
    }
}