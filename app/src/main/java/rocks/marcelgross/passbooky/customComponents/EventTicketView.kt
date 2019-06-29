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

class EventTicketView : ConstraintLayout {

    private lateinit var header: CardHeaderView
    private lateinit var primary: PrimaryFieldsView
    private lateinit var secondary: SecondaryFieldsView
    private lateinit var auxiliary: AuxiliaryFieldsView
    private lateinit var backgroundImage: ImageView
    private lateinit var background: ConstraintLayout
    private lateinit var thumbnail: ImageView


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
        val passContent = pass.eventTicket
        if (passContent != null) {
            header.setUpView(pass, PassType.EVENT_TICKET)
            primary.setUpView(passContent.primaryFields, labelColorInt, textColorInt)
            secondary.setUpView(passContent.secondaryFields, labelColorInt, textColorInt)
            auxiliary.setUpView(passContent.auxiliaryFields, labelColorInt, textColorInt)
        }
        backgroundImage.setImageDrawable(pass.background)
        thumbnail.setImageDrawable(pass.thumbnail)
    }

    private fun init(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        this.addView(inflater.inflate(R.layout.event_ticket, this, false))

        header = findViewById(R.id.header)
        primary = findViewById(R.id.primary)
        secondary = findViewById(R.id.secondary)
        auxiliary = findViewById(R.id.auxiliary)
        background = findViewById(R.id.background)
        backgroundImage = findViewById(R.id.background_image)
        thumbnail = findViewById(R.id.thumbnail)
    }
}