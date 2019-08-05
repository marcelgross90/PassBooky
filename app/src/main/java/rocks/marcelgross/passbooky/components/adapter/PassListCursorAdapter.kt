package rocks.marcelgross.passbooky.components.adapter

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.ViewGroup
import rocks.marcelgross.passbooky.R
import rocks.marcelgross.passbooky.components.OnCardClickListener
import rocks.marcelgross.passbooky.components.viewHolder.PassListViewHolder

class PassListCursorAdapter(
    private val onCardClickListener: OnCardClickListener,
    private val context: Context,
    cursor: Cursor
) : CursorAdapter<PassListViewHolder>(cursor) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.pass_list_item, parent, false)
        return PassListViewHolder(view, onCardClickListener, context)
    }

    override fun onBindViewHolder(holder: PassListViewHolder, cursor: Cursor) {
        holder.assignData(cursor)
    }
}