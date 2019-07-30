package rocks.marcelgross.passbooky.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import rocks.marcelgross.passbooky.R
import rocks.marcelgross.passbooky.components.StoreCardView
import rocks.marcelgross.passbooky.components.receiver.PassReceiver

class StoreCardFragment : Fragment() {

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
        val view = inflater.inflate(R.layout.fragment_store_card, container, false)

        val card = view.findViewById<StoreCardView>(R.id.card)

        fragmentManager?.let { fm ->
            card.setUpView(passReceiver.getPass(), fm)
        }

        return view
    }
}
