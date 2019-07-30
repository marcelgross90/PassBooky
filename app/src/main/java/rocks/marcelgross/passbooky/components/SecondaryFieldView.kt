package rocks.marcelgross.passbooky.components

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import rocks.marcelgross.passbooky.R
import rocks.marcelgross.passbooky.components.abstractViews.PKFieldView

class SecondaryFieldView : PKFieldView {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init()
    }

    private fun init() {
        label.setTypeface(null, Typeface.BOLD)
    }

    override fun setLayout() = R.layout.secondary_field
}