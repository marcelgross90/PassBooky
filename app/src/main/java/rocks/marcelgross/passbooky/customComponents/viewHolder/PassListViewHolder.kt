package rocks.marcelgross.passbooky.customComponents.viewHolder

import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import rocks.marcelgross.passbooky.R
import rocks.marcelgross.passbooky.customComponents.OnCardClickListener
import rocks.marcelgross.passbooky.getPass
import rocks.marcelgross.passbooky.pkpass.PKPass
import rocks.marcelgross.passbooky.pkpass.PassContent
import rocks.marcelgross.passbooky.prepareCalendarIntent
import rocks.marcelgross.passbooky.prepareNavigationIntent
import java.util.Locale
import java.text.DateFormat


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
    private var divider: View = view.findViewById(R.id.divider)
    private var icon: ImageView = view.findViewById(R.id.icon)

    fun assignData(passWithName: Pair<String, PKPass>) {
        val pkPass = passWithName.second
        icon.setImageDrawable(pkPass.icon)
        val content = pkPass.passContent

        divider.visibility = View.GONE

        setPrimary(pkPass)
        setSecondary(content)

        setNavigationIfNeeded(content)
        setCalendarIfNeeded(content)

        contentView.setOnClickListener {
            onCardClickListener.onClick(passWithName.first)
        }
    }

    private fun setNavigationIfNeeded(content: PassContent) {
        if (content.locations.isNullOrEmpty()) {
            navigation.visibility = View.GONE
        } else {
            divider.visibility = View.VISIBLE
            navigation.visibility = View.VISIBLE
            navigation.setOnClickListener {
                prepareNavigationIntent(content, itemView.context)
            }
        }
    }

    private fun setCalendarIfNeeded(content: PassContent) {
        if (content.relevantDate == null) {
            calendar.visibility = View.GONE
        } else {
            divider.visibility = View.VISIBLE
            calendar.visibility = View.VISIBLE
            calendar.setOnClickListener {
                prepareCalendarIntent(content, itemView.context)
            }
        }
    }

    private fun setPrimary(pkPass: PKPass) {
        getPass(pkPass)?.let {
            primary.text = it.primaryFields[0].value
        }
    }

    private fun setSecondary(content: PassContent) {
        val relevantDate = content.relevantDate
        if (relevantDate != null) {
            val locale = getLocale()
            val df = DateFormat.getDateInstance(DateFormat.SHORT, locale)
            val tf = DateFormat.getTimeInstance(DateFormat.SHORT, locale)

            secondary.text = view.context.resources.getString(
                R.string.date_time,
                df.format(relevantDate),
                tf.format(relevantDate)
            )
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