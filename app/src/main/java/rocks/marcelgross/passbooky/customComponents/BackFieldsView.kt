package rocks.marcelgross.passbooky.customComponents

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import rocks.marcelgross.passbooky.R
import rocks.marcelgross.passbooky.customComponents.adapter.BackFieldAdapter
import rocks.marcelgross.passbooky.pkpass.PKPass
import rocks.marcelgross.passbooky.pkpass.asColor

class BackFieldsView : ConstraintLayout {

    private lateinit var background: FrameLayout
    private lateinit var backFields: RecyclerView
    private lateinit var backFieldAdapter: BackFieldAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var backgroundImage: ImageView

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
        val backgroundColor = pass.backgroundColorAsPKColor
        background.setBackgroundColor(backgroundColor.asColor())
        val labelColor = pass.labelColorAsPKColor
        val labelColorInt = Color.rgb(labelColor.red, labelColor.green, labelColor.blue)
        val textColor = pass.foregroundColorAsPKColor
        val textColorInt = Color.rgb(textColor.red, textColor.green, textColor.blue)
        val passContent = pass.storeCard
        if (passContent != null) {
            backFieldAdapter.addBackFields(passContent.backFields, labelColorInt, textColorInt)
            backFieldAdapter.notifyDataSetChanged()
        }
        backgroundImage.setImageDrawable(pass.background)

    }

    private fun init(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        this.addView(inflater.inflate(R.layout.back_fields, this, false))

        backgroundImage = findViewById(R.id.background_image)
        background = findViewById(R.id.background)
        backFields = findViewById(R.id.backFieldsList)
        backFieldAdapter = BackFieldAdapter()
        layoutManager = LinearLayoutManager(context)
        backFields.adapter = backFieldAdapter
        backFields.setHasFixedSize(true)
        backFields.layoutManager = layoutManager

    }
}