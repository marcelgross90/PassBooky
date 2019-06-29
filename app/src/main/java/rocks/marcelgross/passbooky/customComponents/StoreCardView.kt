package rocks.marcelgross.passbooky.customComponents

import android.content.Context
import android.graphics.Color
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import rocks.marcelgross.passbooky.R
import rocks.marcelgross.passbooky.pkpass.PKField
import rocks.marcelgross.passbooky.pkpass.PKPass

class StoreCardView : ConstraintLayout {

    private lateinit var headerLabel: TextView
    private lateinit var headerValue: TextView
    private lateinit var primaryValue: TextView
    private lateinit var secundaryLabel: TextView
    private lateinit var secundaryValue: TextView
    private lateinit var background: ConstraintLayout


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

    fun setUpView(pass: PKPass) {
        val backgroundColor = pass.backgroundColorAsColor
        background.setBackgroundColor(
            Color.rgb(
                backgroundColor.red,
                backgroundColor.green,
                backgroundColor.blue
            )
        )
        val labelColor = pass.labelColorAsColor
        val labelColorInt = Color.rgb(labelColor.red, labelColor.green, labelColor.blue)
        val textColor = pass.foregroundColorAsColor
        val textColorInt = Color.rgb(textColor.red, textColor.green, textColor.blue)
        val passContent = pass.storeCard
        if (passContent != null) {
            setHeaderFields(passContent.headerFields, labelColorInt, textColorInt)
            setPrimaryFields(passContent.primaryFields, textColorInt)
            setSecondaryFields(passContent.secondaryFields, labelColorInt, textColorInt)
        }
        val logoView = findViewById<ImageView>(R.id.logo)
        logoView.setImageDrawable(pass.logo)
        val stripView = findViewById<ImageView>(R.id.strip)
        stripView.setImageDrawable(pass.strip)
    }

    private fun setHeaderFields(headerFields: List<PKField>, labelColor: Int, textColor: Int) {
        headerLabel.setTextColor(labelColor)
        headerLabel.text = headerFields[0].label

        headerValue.setTextColor(textColor)
        headerValue.text = headerFields[0].value
    }

    private fun setPrimaryFields(primaryFields: List<PKField>, textColor: Int) {
        primaryValue.setTextColor(textColor)
        primaryValue.text = primaryFields[0].value
    }

    private fun setSecondaryFields(secondaryFiels: List<PKField>, labelColor: Int, textColor: Int) {
        secundaryLabel.setTextColor(labelColor)
        secundaryLabel.text = secondaryFiels[0].label

        secundaryValue.setTextColor(textColor)
        secundaryValue.text = secondaryFiels[0].value
    }

    private fun init(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        this.addView(inflater.inflate(R.layout.store_card, this, false))

        headerLabel = findViewById(R.id.headerLabel)
        headerValue = findViewById(R.id.headerValue)
        primaryValue = findViewById(R.id.primaryValue)
        secundaryLabel = findViewById(R.id.secundaryLabel)
        secundaryValue = findViewById(R.id.secundaryValue)
        background = findViewById(R.id.background)
    }

}