package rocks.marcelgross.passbooky.customComponents.abstractViews

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import rocks.marcelgross.passbooky.R
import rocks.marcelgross.passbooky.pkpass.PKField

abstract class PKFieldView : ConstraintLayout {

    protected lateinit var label: TextView
    private lateinit var value: TextView

    protected abstract fun setLayout(): Int

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

    fun setUpView(field: PKField, labelColor: Int, textColor: Int) {
        label.setTextColor(labelColor)
        label.text = field.label

        value.setTextColor(textColor)
        value.text = field.value
    }

    private fun init(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        this.addView(inflater.inflate(setLayout(), this, false))

        label = findViewById(R.id.label)
        value = findViewById(R.id.value)
    }
}