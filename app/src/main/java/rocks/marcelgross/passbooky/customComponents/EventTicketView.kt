package rocks.marcelgross.passbooky.customComponents

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import rocks.marcelgross.passbooky.R
import rocks.marcelgross.passbooky.pkpass.PKPass
import rocks.marcelgross.passbooky.pkpass.PassType
import rocks.marcelgross.passbooky.pkpass.asColor

class EventTicketView : ConstraintLayout {

    private lateinit var header: CardHeaderView
    private lateinit var primary: PrimaryFieldsView
    private lateinit var secondary: SecondaryFieldsView
    private lateinit var auxiliary: AuxiliaryFieldsView
    private lateinit var backgroundImage: ImageView
    private lateinit var background: ConstraintLayout
    private lateinit var thumbnail: ImageView
    private lateinit var moreButton: Button

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

    fun setUpView(pass: PKPass, fm: FragmentManager) {
        val passContent = pass.passContent
        val backgroundColor = passContent.backgroundColorAsPKColor
        background.setBackgroundColor(backgroundColor.asColor())
        val labelColor = passContent.labelColorAsPKColor
        val labelColorInt = Color.rgb(labelColor.red, labelColor.green, labelColor.blue)
        val textColor = passContent.foregroundColorAsPKColor
        val textColorInt = Color.rgb(textColor.red, textColor.green, textColor.blue)
        val eventTicket = passContent.eventTicket
        if (eventTicket != null) {
            header.setUpView(pass, PassType.EVENT_TICKET)
            primary.setUpView(eventTicket.primaryFields, labelColorInt, textColorInt)
            secondary.setUpView(eventTicket.secondaryFields, labelColorInt, textColorInt)
            auxiliary.setUpView(eventTicket.auxiliaryFields, labelColorInt, textColorInt)

            if (eventTicket.backFields.isEmpty()) {
                moreButton.visibility = View.GONE
            }
        }
        backgroundImage.setImageDrawable(pass.background)
        thumbnail.setImageDrawable(pass.thumbnail)

        moreButton.setOnClickListener {
            replaceFragment(fm)
        }
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
        moreButton = findViewById(R.id.button)
    }
}