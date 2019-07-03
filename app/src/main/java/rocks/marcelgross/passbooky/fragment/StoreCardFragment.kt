package rocks.marcelgross.passbooky.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import rocks.marcelgross.passbooky.ImageLoader.addImagesToStoreCard
import rocks.marcelgross.passbooky.R
import rocks.marcelgross.passbooky.TestsPasses
import rocks.marcelgross.passbooky.customComponents.StoreCardView
import rocks.marcelgross.passbooky.pkpass.PKPass
import rocks.marcelgross.passbooky.pkpass.PassType

class StoreCardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_store_card, container, false)

        val gson = Gson()
        val storeCard: PKPass = gson.fromJson(TestsPasses.storeCard(), PKPass::class.java)
        addImagesToStoreCard(storeCard, context)

        val card = view.findViewById<StoreCardView>(R.id.card)

        fragmentManager?.let {
            card.setUpView(storeCard, PassType.STORE_CARD, it)
        }

        return view
    }
}
