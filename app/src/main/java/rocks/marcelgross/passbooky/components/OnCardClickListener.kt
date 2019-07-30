package rocks.marcelgross.passbooky.components

import android.view.View

interface OnCardClickListener {

    fun onClick(view: View, fileName: String)
}