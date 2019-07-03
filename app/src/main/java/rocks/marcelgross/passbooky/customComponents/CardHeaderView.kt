package rocks.marcelgross.passbooky.customComponents

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import rocks.marcelgross.passbooky.R
import rocks.marcelgross.passbooky.pkpass.PKPass
import rocks.marcelgross.passbooky.pkpass.PassContent
import rocks.marcelgross.passbooky.pkpass.PassType

class CardHeaderView : ConstraintLayout {

    private lateinit var header: HeaderFieldsView
    private lateinit var logo: ImageView

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

    fun setUpView(pass: PKPass, passType: PassType) {
        val labelColor = pass.labelColorAsColor
        val labelColorInt = Color.rgb(labelColor.red, labelColor.green, labelColor.blue)
        val textColor = pass.foregroundColorAsColor
        val textColorInt = Color.rgb(textColor.red, textColor.green, textColor.blue)
        val passContent = getContentForType(pass, passType)
        if (passContent != null) {
            header.setUpView(passContent.headerFields, labelColorInt, textColorInt)
        }
        logo.setImageDrawable(pass.logo)
    }

    private fun getContentForType(pass: PKPass, passType: PassType): PassContent? {
        return when (passType) {
            PassType.EVENT_TICKET -> pass.eventTicket
            PassType.STORE_CARD -> pass.storeCard
        }
    }

    private fun init(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        this.addView(inflater.inflate(R.layout.card_header, this, false))

        header = findViewById(R.id.headerFields)
        logo = findViewById(R.id.logo)
    }
}