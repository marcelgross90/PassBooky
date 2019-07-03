package rocks.marcelgross.passbooky.customComponents.viewHolder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import rocks.marcelgross.passbooky.R
import rocks.marcelgross.passbooky.pkpass.PKField

class BackFieldViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private var label: TextView = view.findViewById(R.id.label)
    private var value: TextView = view.findViewById(R.id.value)

    fun assignData(field: PKField, labelColor: Int, textColor: Int) {
        label.setTextColor(labelColor)
        value.setTextColor(textColor)
        label.text = field.label
        if (field.value != null) {
            value.text = field.value
        } else if (field.attributedValue != null) {
            value.text = field.attributedValue
        }
    }
}