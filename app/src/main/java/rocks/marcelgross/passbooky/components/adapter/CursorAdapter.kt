package rocks.marcelgross.passbooky.components.adapter

import android.database.Cursor
import androidx.recyclerview.widget.RecyclerView

abstract class CursorAdapter<V : RecyclerView.ViewHolder>(c: Cursor) :
    RecyclerView.Adapter<V>() {
    private var mCursor: Cursor? = null
    private var mDataValid: Boolean = false
    private var mRowIDColumn: Int = 0

    abstract fun onBindViewHolder(holder: V, cursor: Cursor)

    init {
        this.setHasStableIds(true)
        swapCursor(c)
    }

    override fun onBindViewHolder(holder: V, position: Int) {
        val cursor = mCursor
            ?: throw IllegalStateException("Cursor is null")

        if (!mDataValid) {
            throw IllegalStateException("Cannot bind view holder when cursor is in invalid state.")
        }
        if (!cursor.moveToPosition(position)) {
            throw IllegalStateException("Could not move cursor to position $position when trying to bind view holder")
        }

        onBindViewHolder(holder, cursor)
    }

    override fun getItemCount(): Int {
        val cursor = mCursor ?: throw IllegalStateException("Cursor is empty")

        return if (mDataValid) {
            cursor.count
        } else {
            0
        }
    }

    override fun getItemId(position: Int): Long {
        val cursor = mCursor
            ?: throw IllegalStateException("Cursor is null")

        if (!mDataValid) {
            throw IllegalStateException("Cannot lookup item id when cursor is in invalid state.")
        }
        if (!cursor.moveToPosition(position)) {
            throw IllegalStateException("Could not move cursor to position $position when trying to get an item id")
        }

        return mCursor!!.getLong(mRowIDColumn)
    }

    fun getItem(position: Int): Cursor {
        val cursor = mCursor
            ?: throw IllegalStateException("Cursor is null")

        if (!mDataValid) {
            throw IllegalStateException("Cannot lookup item id when cursor is in invalid state.")
        }
        if (!cursor.moveToPosition(position)) {
            throw IllegalStateException("Could not move cursor to position $position when trying to get an item id")
        }
        return cursor
    }

    fun swapCursor(newCursor: Cursor?) {
        if (newCursor === mCursor) {
            return
        }

        if (newCursor != null) {
            mCursor = newCursor
            mDataValid = true
            notifyDataSetChanged()
        } else {
            notifyItemRangeRemoved(0, itemCount)
            mCursor = null
            mRowIDColumn = -1
            mDataValid = false
        }
    }
}