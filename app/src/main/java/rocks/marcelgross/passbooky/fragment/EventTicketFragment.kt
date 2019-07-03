package rocks.marcelgross.passbooky.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import rocks.marcelgross.passbooky.R
import rocks.marcelgross.passbooky.customComponents.EventTicketView
import rocks.marcelgross.passbooky.customComponents.receiver.PassReceiver

class EventTicketFragment : Fragment() {

    private lateinit var passReceiver: PassReceiver

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        context?.let {
            passReceiver = it as PassReceiver
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_event_ticket, container, false)

        val card = view.findViewById<EventTicketView>(R.id.card)

        fragmentManager?.let { fm ->
            card.setUpView(passReceiver.getPass(), fm)
        }

        return view
    }
}
