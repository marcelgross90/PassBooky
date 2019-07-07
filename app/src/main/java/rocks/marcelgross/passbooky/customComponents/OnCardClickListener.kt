package rocks.marcelgross.passbooky.customComponents

import android.view.View

interface OnCardClickListener {

    fun onClick(view: View, fileName: String)
}