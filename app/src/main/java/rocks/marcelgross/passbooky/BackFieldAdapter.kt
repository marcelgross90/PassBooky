package rocks.marcelgross.passbooky

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import rocks.marcelgross.passbooky.pkpass.PKField

class BackFieldAdapter : RecyclerView.Adapter<BackFieldViewHolder>() {

    private val backFields: MutableList<PKField> = mutableListOf()
    private var labelColor: Int = 0
    private var textColor: Int = 0

    fun addBackFields(backFields: List<PKField>, labelColor: Int, textColor: Int) {
        this.labelColor = labelColor
        this.textColor = textColor
        this.backFields.clear()
        this.backFields.addAll(backFields)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BackFieldViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.back_field, parent, false)
        return BackFieldViewHolder(view)
    }

    override fun getItemCount() = backFields.size

    override fun onBindViewHolder(holder: BackFieldViewHolder, position: Int) {
        holder.assignData(backFields[position], labelColor, textColor)
    }
}