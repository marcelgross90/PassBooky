package rocks.marcelgross.passbooky.customComponents

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import rocks.marcelgross.passbooky.R
import rocks.marcelgross.passbooky.fragment.BackFieldsFragment
import rocks.marcelgross.passbooky.pkpass.PKPass
import rocks.marcelgross.passbooky.pkpass.PassType
import rocks.marcelgross.passbooky.pkpass.asColor

class StoreCardView : ConstraintLayout {

    private lateinit var header: CardHeaderView
    private lateinit var primary: PrimaryFieldsView
    private lateinit var secondary: SecondaryFieldsView
    private lateinit var background: LinearLayout
    private lateinit var strip: ImageView
    private lateinit var passType: PassType
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

    fun setUpView(pass: PKPass, passType: PassType, fm: FragmentManager) {
        this.passType = passType
        val backgroundColor = pass.backgroundColorAsPKColor
        background.setBackgroundColor(backgroundColor.asColor())
        val labelColor = pass.labelColorAsPKColor
        val labelColorInt = Color.rgb(labelColor.red, labelColor.green, labelColor.blue)
        val textColor = pass.foregroundColorAsPKColor
        val textColorInt = Color.rgb(textColor.red, textColor.green, textColor.blue)
        val passContent = pass.storeCard
        if (passContent != null) {
            header.setUpView(pass, PassType.STORE_CARD)
            primary.setUpView(passContent.primaryFields, labelColorInt, textColorInt)
            secondary.setUpView(passContent.secondaryFields, labelColorInt, textColorInt)

            if (passContent.backFields.isEmpty()) {
                moreButton.visibility = View.GONE
            }
        }
        strip.setImageDrawable(pass.strip)

        moreButton.setOnClickListener {
                replaceFragment(fm)
        }
    }

    private fun replaceFragment(fm: FragmentManager) {
        val fragment = BackFieldsFragment()
        val bundle = Bundle()
        bundle.putString(
            "passType",
            passType.name
        )
        fragment.arguments = bundle
        fm
            .beginTransaction()
            .setCustomAnimations(R.animator.right_in, R.animator.right_out,
                R.animator.left_in, R.animator.left_out)
            .replace(
                R.id.content_container,
                fragment, fragment.javaClass.name
            )
            .addToBackStack(null)
            .commit()
    }

    private fun init(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        this.addView(inflater.inflate(R.layout.store_card, this, false))

        header = findViewById(R.id.header)
        primary = findViewById(R.id.primary)
        secondary = findViewById(R.id.secondary)
        background = findViewById(R.id.background)
        strip = findViewById(R.id.strip)
        moreButton = findViewById(R.id.button)
    }
}