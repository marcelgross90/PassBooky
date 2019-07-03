package rocks.marcelgross.passbooky.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import rocks.marcelgross.passbooky.ImageLoader.addImagesToEventTicket
import rocks.marcelgross.passbooky.R
import rocks.marcelgross.passbooky.TestsPasses
import rocks.marcelgross.passbooky.customComponents.EventTicketView
import rocks.marcelgross.passbooky.pkpass.PKPass
import rocks.marcelgross.passbooky.pkpass.PassType

class EventTicketFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_event_ticket, container, false)

        val gson = Gson()
        val eventTicket: PKPass = gson.fromJson(TestsPasses.eventTicket(), PKPass::class.java)
        addImagesToEventTicket(eventTicket, context)

        val card = view.findViewById<EventTicketView>(R.id.card)

        fragmentManager?.let {
            card.setUpView(eventTicket, PassType.EVENT_TICKET, it)
        }

        return view
    }
}
