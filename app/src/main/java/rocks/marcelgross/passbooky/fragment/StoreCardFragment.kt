package rocks.marcelgross.passbooky.fragment


import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson

import rocks.marcelgross.passbooky.R
import rocks.marcelgross.passbooky.TestsPasses
import rocks.marcelgross.passbooky.customComponents.StoreCardView
import rocks.marcelgross.passbooky.pkpass.PKPass

class StoreCardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_store_card, container, false)

        val gson = Gson()
        val storeCard: PKPass = gson.fromJson(TestsPasses.storeCard(), PKPass::class.java)
        addImagesToStoreCard(storeCard)

        val card = view.findViewById<StoreCardView>(R.id.card)

        card.setUpView(storeCard)

        return view
    }

    private fun addImagesToStoreCard(storeCard: PKPass) {
        try {
            context?.let {
                val ims = it.assets.open("logo_store_card.png")
                val logo = Drawable.createFromStream(ims, null)
                storeCard.logo = logo

                val ims1 = it.assets.open("strip_store_card.png")
                val strip = Drawable.createFromStream(ims1, null)
                storeCard.strip = strip
            }
        } catch (ex: Exception) {
            Log.d("mgr1", ex.message)
        }
    }


}
