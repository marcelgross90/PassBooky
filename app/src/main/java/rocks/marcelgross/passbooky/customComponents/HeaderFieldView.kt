package rocks.marcelgross.passbooky.customComponents

import android.content.Context
import android.util.AttributeSet
import rocks.marcelgross.passbooky.R
import rocks.marcelgross.passbooky.customComponents.abstractViews.PKFieldView

class HeaderFieldView : PKFieldView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    override fun setLayout() = R.layout.header_field
}