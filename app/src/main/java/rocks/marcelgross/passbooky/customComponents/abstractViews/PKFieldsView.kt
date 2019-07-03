package rocks.marcelgross.passbooky.customComponents.abstractViews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import rocks.marcelgross.passbooky.R
import rocks.marcelgross.passbooky.pkpass.PKField

abstract class PKFieldsView : ConstraintLayout {

    private lateinit var field1: PKFieldView
    private lateinit var field2: PKFieldView
    private lateinit var field3: PKFieldView

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

    fun setUpView(fields: List<PKField>, labelColor: Int, textColor: Int) {
        when (fields.size) {
            0 -> {
                field1.visibility = View.GONE
                field2.visibility = View.GONE
                field3.visibility = View.GONE
            }
            1 -> {
                field1.setUpView(fields[0], labelColor, textColor)
                field2.visibility = View.GONE
                field3.visibility = View.GONE
            }
            2 -> {
                field1.setUpView(fields[0], labelColor, textColor)
                field2.setUpView(fields[1], labelColor, textColor)
                field3.visibility = View.GONE
            }
            3 -> {
                field1.setUpView(fields[0], labelColor, textColor)
                field2.setUpView(fields[1], labelColor, textColor)
                field3.setUpView(fields[2], labelColor, textColor)
            }
        }
    }

    private fun init(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        this.addView(inflater.inflate(setLayout(), this, false))

        field1 = findViewById(R.id.field1)
        field2 = findViewById(R.id.field2)
        field3 = findViewById(R.id.field3)
    }
}