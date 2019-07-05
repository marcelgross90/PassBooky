package rocks.marcelgross.passbooky.customComponents.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import rocks.marcelgross.passbooky.R
import rocks.marcelgross.passbooky.customComponents.OnCardClickListener
import rocks.marcelgross.passbooky.customComponents.viewHolder.PassListViewHolder
import rocks.marcelgross.passbooky.pkpass.PKPass

class PassListAdapter(
    private val onCardClickListener: OnCardClickListener
) : RecyclerView.Adapter<PassListViewHolder>() {

    private val passes: MutableList<Pair<String, PKPass>> = mutableListOf()

    fun addPasses(passes: List<Pair<String, PKPass>>) {
        this.passes.clear()
        this.passes.addAll(passes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.pass_list_item, parent, false)
        return PassListViewHolder(view, onCardClickListener)
    }

    override fun getItemCount() = passes.size

    override fun onBindViewHolder(holder: PassListViewHolder, position: Int) {
        holder.assignData(passes[position])
    }
}