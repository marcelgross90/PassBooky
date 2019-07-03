package rocks.marcelgross.passbooky.customComponents

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import rocks.marcelgross.passbooky.R
import rocks.marcelgross.passbooky.pkpass.PKPass

class CardHeaderView : ConstraintLayout {

    private lateinit var header: HeaderFieldsView
    private lateinit var logo: ImageView
    private lateinit var logoText: TextView

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
        val passContent = pass.passContent
        val labelColor = passContent.labelColorAsPKColor
        val labelColorInt = Color.rgb(labelColor.red, labelColor.green, labelColor.blue)
        val textColor = passContent.foregroundColorAsPKColor
        val textColorInt = Color.rgb(textColor.red, textColor.green, textColor.blue)
        val cardContent = getContentForType(passContent, pass.getPassType())
        if (cardContent != null) {
            header.setUpView(cardContent.headerFields, labelColorInt, textColorInt)
            if (cardContent.headerFields.isEmpty()) {
                logoText.visibility = View.VISIBLE
                logoText.setTextColor(textColorInt)
                logoText.text = passContent.organizationName
            } else {
                logoText.visibility = View.GONE
            }
        }
        logo.setImageDrawable(pass.logo)
    }

    private fun init(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        this.addView(inflater.inflate(R.layout.card_header, this, false))

        header = findViewById(R.id.headerFields)
        logo = findViewById(R.id.logo)
        logoText = findViewById(R.id.logoText)
    }
}