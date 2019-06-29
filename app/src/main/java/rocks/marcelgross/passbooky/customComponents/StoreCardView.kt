package rocks.marcelgross.passbooky.customComponents

import android.content.Context
import android.graphics.Color
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import rocks.marcelgross.passbooky.R
import rocks.marcelgross.passbooky.pkpass.PKPass
import rocks.marcelgross.passbooky.pkpass.PassType

class StoreCardView : ConstraintLayout {

    private lateinit var header: CardHeaderView
    private lateinit var primary: PrimaryFieldsView
    private lateinit var secondary: SecondaryFieldsView
    private lateinit var background: ConstraintLayout
    private lateinit var strip: ImageView


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
            header.setUpView(pass, PassType.STORE_CARD)
            primary.setUpView(passContent.primaryFields, labelColorInt, textColorInt)
            secondary.setUpView(passContent.secondaryFields, labelColorInt, textColorInt)
        }
        strip.setImageDrawable(pass.strip)
    }

    private fun init(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        this.addView(inflater.inflate(R.layout.store_card, this, false))

        header = findViewById(R.id.header)
        primary = findViewById(R.id.primary)
        secondary = findViewById(R.id.secondary)
        background = findViewById(R.id.background)
        strip = findViewById(R.id.strip)
    }

}