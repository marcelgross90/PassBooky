package rocks.marcelgross.passbooky.components

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
import rocks.marcelgross.passbooky.fragment.BackFieldsFragment
import rocks.marcelgross.passbooky.pkpass.PKPass
import rocks.marcelgross.passbooky.pkpass.asColor

fun replaceFragment(fm: FragmentManager) {
    val fragment = BackFieldsFragment()
    fm
        .beginTransaction()
        .setCustomAnimations(
            R.animator.right_in, R.animator.right_out,
            R.animator.left_in, R.animator.left_out
        )
        .replace(
            R.id.content_container,
            fragment, fragment.javaClass.name
        )
        .addToBackStack(null)
        .commit()
}

class StoreCardView : ConstraintLayout {

    private lateinit var header: CardHeaderView
    private lateinit var primary: PrimaryFieldsView
    private lateinit var secondary: SecondaryFieldsView
    private lateinit var background: ConstraintLayout
    private lateinit var strip: ImageView
    private lateinit var barcode: BarcodeView
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
        val storeCard = passContent.storeCard
        if (storeCard != null) {
            header.setUpView(pass)
            primary.setUpView(storeCard.primaryFields, labelColorInt, textColorInt)
            secondary.setUpView(storeCard.secondaryFields, labelColorInt, textColorInt)

            if (storeCard.backFields.isEmpty()) {
                moreButton.visibility = View.GONE
            }
        }
        strip.setImageDrawable(pass.strip)

        when {
            passContent.barcodes.isNotEmpty() -> barcode.setUpView(passContent.barcodes[0])
            passContent.barcode != null -> barcode.setUpView(passContent.barcode)
            else -> barcode.visibility = View.INVISIBLE
        }
        moreButton.setTextColor(textColorInt)
        moreButton.setOnClickListener {
            replaceFragment(fm)
        }
    }

    private fun init(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        this.addView(inflater.inflate(R.layout.store_card, this, false))

        header = findViewById(R.id.header)
        primary = findViewById(R.id.primary)
        secondary = findViewById(R.id.secondary)
        background = findViewById(R.id.background)
        strip = findViewById(R.id.strip)
        barcode = findViewById(R.id.barcode)
        moreButton = findViewById(R.id.more)
    }
}