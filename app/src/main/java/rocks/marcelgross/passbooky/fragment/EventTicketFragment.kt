package rocks.marcelgross.passbooky.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import rocks.marcelgross.passbooky.PKPassLoader
import rocks.marcelgross.passbooky.R
import rocks.marcelgross.passbooky.customComponents.EventTicketView
import rocks.marcelgross.passbooky.pkpass.PassType

class EventTicketFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_event_ticket, container, false)

        context?.let {
            val eventTicket = PKPassLoader.load(it.assets.open("pass.pkpass"))

            val card = view.findViewById<EventTicketView>(R.id.card)

            fragmentManager?.let {
                card.setUpView(eventTicket, PassType.EVENT_TICKET, it)
            }
        }

        return view
    }
}
