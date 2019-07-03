package rocks.marcelgross.passbooky.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import rocks.marcelgross.passbooky.PKPassLoader
import rocks.marcelgross.passbooky.R
import rocks.marcelgross.passbooky.customComponents.StoreCardView
import rocks.marcelgross.passbooky.pkpass.PassType

class StoreCardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_store_card, container, false)

        context?.let {
            val storeCard = PKPassLoader.load(it.assets.open("cbr-businesscard.pkpass"))

            val card = view.findViewById<StoreCardView>(R.id.card)

            fragmentManager?.let { fm ->
                card.setUpView(storeCard, PassType.STORE_CARD, fm)
            }
        }

        return view
    }
}
