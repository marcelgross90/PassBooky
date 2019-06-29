package rocks.marcelgross.passbooky.customComponents

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import rocks.marcelgross.passbooky.R
import rocks.marcelgross.passbooky.pkpass.PKField

class HeaderText : ConstraintLayout {

    private lateinit var headerLabel: TextView
    private lateinit var headerValue: TextView


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

    fun setUpView(headerField: PKField, labelColor: Int, textColor: Int) {
        headerLabel.setTextColor(labelColor)
        headerLabel.text = headerField.label

        headerValue.setTextColor(textColor)
        headerValue.text = headerField.value
    }

    private fun init(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        this.addView(inflater.inflate(R.layout.store_card, this, false))

        headerLabel = findViewById(R.id.headerLabel)
        headerValue = findViewById(R.id.headerValue)
    }
}