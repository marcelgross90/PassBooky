package rocks.marcelgross.passbooky.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import rocks.marcelgross.passbooky.ImageLoader
import rocks.marcelgross.passbooky.R
import rocks.marcelgross.passbooky.TestsPasses
import rocks.marcelgross.passbooky.customComponents.BackFieldsView
import rocks.marcelgross.passbooky.pkpass.PKPass
import rocks.marcelgross.passbooky.pkpass.PassType

class BackFieldsFragment : Fragment() {

    private lateinit var pass: PKPass
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = arguments
        bundle?.let {
            when (it.getString("passType")) {
                PassType.STORE_CARD.name -> {
                    pass = gson.fromJson(TestsPasses.storeCard(), PKPass::class.java)
                    ImageLoader.addImagesToStoreCard(pass, context)
                }
                PassType.EVENT_TICKET.name -> {
                    pass = gson.fromJson(TestsPasses.eventTicket(), PKPass::class.java)
                    ImageLoader.addImagesToEventTicket(pass, context)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_back_fields, container, false)

        val backFieldsView = view.findViewById<BackFieldsView>(R.id.backFieldsView)

        backFieldsView.setUpView(pass)

        return view
    }
}
