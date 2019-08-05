package rocks.marcelgross.passbooky.components.viewHolder

import android.content.Context
import android.database.Cursor
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import rocks.marcelgross.passbooky.R
import rocks.marcelgross.passbooky.components.OnCardClickListener
import rocks.marcelgross.passbooky.database.PASSES_BACKGROUND_COLOR
import rocks.marcelgross.passbooky.database.PASSES_ID
import rocks.marcelgross.passbooky.database.PASSES_LABEL_COLOR
import rocks.marcelgross.passbooky.database.PASSES_PRIMARY
import rocks.marcelgross.passbooky.database.PASSES_PRIMARY_COLOR
import rocks.marcelgross.passbooky.database.PASSES_SECONDARY
import rocks.marcelgross.passbooky.pkpass.ImageTypes
import rocks.marcelgross.passbooky.pkpass.PKColor
import rocks.marcelgross.passbooky.pkpass.asColor
import rocks.marcelgross.passbooky.pkpass.baseDirName
import rocks.marcelgross.passbooky.pkpass.getUriForImage
import java.text.DateFormat
import java.util.Locale
import java.text.SimpleDateFormat

class PassListViewHolder(
    private val view: View,
    private val onCardClickListener: OnCardClickListener,
    context: Context
) :
    RecyclerView.ViewHolder(view) {

    private var baseUri: String = "${context.getDir(baseDirName, Context.MODE_PRIVATE)}"
    private var contentView: View = view.findViewById(R.id.content)
    private var primary: TextView = view.findViewById(R.id.primaryText)
    private var secondary: TextView = view.findViewById(R.id.secondaryText)
    private var icon: ImageView = view.findViewById(R.id.icon)
    private var backgroundImage: ImageView = view.findViewById(R.id.background_image)
    private var background: ConstraintLayout = view.findViewById(R.id.background)

    fun assignData(cursor: Cursor) {
        val uri = "$baseUri/${cursor.getInt(cursor.getColumnIndex(PASSES_ID))}"

        getUriForImage(uri, ImageTypes.ICON)?.let {
            icon.setImageURI(it)
        }

        getUriForImage(uri, ImageTypes.BACKGROUND)?.let {
            backgroundImage.setImageURI(it)
        }

        val backgroundColor: String? =
            cursor.getString(cursor.getColumnIndex(PASSES_BACKGROUND_COLOR))
        backgroundColor?.let {
            background.setBackgroundColor(PKColor(backgroundColor).asColor())
        }

        primary.setTextColor(PKColor(cursor.getString(cursor.getColumnIndex(PASSES_PRIMARY_COLOR))).asColor())
        primary.text = cursor.getString(cursor.getColumnIndex(PASSES_PRIMARY))

        secondary.setTextColor(PKColor(cursor.getString(cursor.getColumnIndex(PASSES_LABEL_COLOR))).asColor())
        setSecondary(cursor.getString(cursor.getColumnIndex(PASSES_SECONDARY)))

        contentView.setOnClickListener {
            onCardClickListener.onClick(background, uri)
        }
    }

    private fun setSecondary(secondaryText: String?) {
        if (secondaryText != null) {
            try {
                val format = SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.US)
                val date = format.parse(secondaryText)
                val locale = getLocale()
                val df = DateFormat.getDateInstance(DateFormat.SHORT, locale)
                val tf = DateFormat.getTimeInstance(DateFormat.SHORT, locale)

                secondary.text = view.context.resources.getString(
                    R.string.date_time,
                    df.format(date),
                    tf.format(date)
                )
            } catch (ex: Exception) {
                secondary.visibility = View.INVISIBLE
            }
        } else {
            secondary.visibility = View.INVISIBLE
        }
    }

    private fun getLocale(): Locale {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            view.context.resources.configuration.locales[0]
        } else {
            Locale.getDefault()
        }
    }
}