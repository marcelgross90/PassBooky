package rocks.marcelgross.passbooky.components.viewHolder

import android.graphics.Color
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import rocks.marcelgross.passbooky.R
import rocks.marcelgross.passbooky.components.OnCardClickListener
import rocks.marcelgross.passbooky.pkpass.getPassFields
import rocks.marcelgross.passbooky.pkpass.PKPass
import rocks.marcelgross.passbooky.pkpass.PassContent
import rocks.marcelgross.passbooky.pkpass.asColor
import rocks.marcelgross.passbooky.prepareCalendarIntent
import rocks.marcelgross.passbooky.prepareNavigationIntent
import java.text.DateFormat
import java.util.Locale

class PassListViewHolder(
    private val view: View,
    private val onCardClickListener: OnCardClickListener
) :
    RecyclerView.ViewHolder(view) {

    private var contentView: View = view.findViewById(R.id.content)
    private var primary: TextView = view.findViewById(R.id.primaryText)
    private var secondary: TextView = view.findViewById(R.id.secondaryText)
    private var navigation: TextView = view.findViewById(R.id.navigation)
    private var calendar: TextView = view.findViewById(R.id.calendar)
    private var icon: ImageView = view.findViewById(R.id.icon)
    private var backgroundImage: ImageView = view.findViewById(R.id.background_image)
    private var background: ConstraintLayout = view.findViewById(R.id.background)

    fun assignData(passWithName: Pair<String, PKPass>) {
        val pkPass = passWithName.second
        icon.setImageDrawable(pkPass.icon)
        backgroundImage.setImageDrawable(pkPass.background)
        val content = pkPass.passContent

        val backgroundColor = content.backgroundColorAsPKColor
        background.setBackgroundColor(backgroundColor.asColor())

        setPrimary(pkPass)
        setSecondary(content)

        setNavigationIfNeeded(content)
        setCalendarIfNeeded(content)

        contentView.setOnClickListener {
            onCardClickListener.onClick(background, passWithName.first)
        }
    }

    private fun setNavigationIfNeeded(content: PassContent) {
        if (content.locations.isNullOrEmpty()) {
            navigation.visibility = View.GONE
        } else {
            navigation.visibility = View.VISIBLE
            navigation.setTextColor(labelColor(content))
            navigation.setOnClickListener {
                prepareNavigationIntent(content, itemView.context)
            }
        }
    }

    private fun setCalendarIfNeeded(content: PassContent) {
        if (content.relevantDate == null) {
            calendar.visibility = View.GONE
        } else {
            calendar.setTextColor(labelColor(content))
            calendar.visibility = View.VISIBLE
            calendar.setOnClickListener {
                prepareCalendarIntent(content, itemView.context)
            }
        }
    }

    private fun setPrimary(pkPass: PKPass) {
        val textColor = pkPass.passContent.foregroundColorAsPKColor
        val textColorInt = Color.rgb(textColor.red, textColor.green, textColor.blue)
        getPassFields(pkPass)?.let {
            primary.setTextColor(textColorInt)
            primary.text = it.primaryFields[0].value
        }
    }

    private fun setSecondary(content: PassContent) {
        val relevantDate = content.relevantDate
        if (relevantDate != null) {
            val locale = getLocale()
            val df = DateFormat.getDateInstance(DateFormat.SHORT, locale)
            val tf = DateFormat.getTimeInstance(DateFormat.SHORT, locale)

            secondary.setTextColor(labelColor(content))
            secondary.text = view.context.resources.getString(
                R.string.date_time,
                df.format(relevantDate),
                tf.format(relevantDate)
            )
        } else {
            secondary.visibility = View.INVISIBLE
        }
    }

    private fun labelColor(content: PassContent): Int {
        val labelColor = content.labelColorAsPKColor
        return Color.rgb(labelColor.red, labelColor.green, labelColor.blue)
    }

    private fun getLocale(): Locale {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            view.context.resources.configuration.locales[0]
        } else {
            Locale.getDefault()
        }
    }
}