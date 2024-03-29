package rocks.marcelgross.passbooky.components

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import rocks.marcelgross.passbooky.R
import rocks.marcelgross.passbooky.components.adapter.BackFieldAdapter
import rocks.marcelgross.passbooky.pkpass.PKPass
import rocks.marcelgross.passbooky.pkpass.asColor

class BackFieldsView : ConstraintLayout {

    private lateinit var background: FrameLayout
    private lateinit var backFields: RecyclerView
    private lateinit var backFieldAdapter: BackFieldAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var backgroundImage: ImageView
    private lateinit var lessBtn: Button

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

    fun setUpView(pass: PKPass, activity: Activity) {
        val passContent = pass.passContent
        val backgroundColor = passContent.backgroundColorAsPKColor
        background.setBackgroundColor(backgroundColor.asColor())
        val labelColor = passContent.labelColorAsPKColor
        val labelColorInt = Color.rgb(labelColor.red, labelColor.green, labelColor.blue)
        val textColor = passContent.foregroundColorAsPKColor
        val textColorInt = Color.rgb(textColor.red, textColor.green, textColor.blue)
        val fields = passContent.getFields()
        if (fields != null) {
            backFieldAdapter.addBackFields(fields.backFields, labelColorInt, textColorInt)
            backFieldAdapter.notifyDataSetChanged()
        }
        backgroundImage.setImageDrawable(pass.background)

        lessBtn.setTextColor(textColorInt)
        lessBtn.setOnClickListener { activity.onBackPressed() }
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
        lessBtn = findViewById(R.id.lessBtn)
    }
}