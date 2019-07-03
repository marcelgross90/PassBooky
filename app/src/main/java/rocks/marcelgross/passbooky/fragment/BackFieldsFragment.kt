package rocks.marcelgross.passbooky.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import rocks.marcelgross.passbooky.PKPassLoader
import rocks.marcelgross.passbooky.R
import rocks.marcelgross.passbooky.customComponents.BackFieldsView
import rocks.marcelgross.passbooky.pkpass.PKPass
import rocks.marcelgross.passbooky.pkpass.PassType

class BackFieldsFragment : Fragment() {

    private lateinit var pkPass: PKPass
    private lateinit var passType: PassType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = arguments
        bundle?.let {
            when (it.getString("passType")) {
                PassType.STORE_CARD.name -> {
                    passType = PassType.STORE_CARD
                    context?.let { context ->
                        pkPass = PKPassLoader.load(context.assets.open("cbr-businesscard.pkpass"))
                    }
                }
                PassType.EVENT_TICKET.name -> {
                    passType = PassType.EVENT_TICKET
                    context?.let { context ->
                        pkPass = PKPassLoader.load(context.assets.open("pass.pkpass"))
                    }
                }
                else ->
                    pkPass = PKPass()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_back_fields, container, false)

        val backFieldsView = view.findViewById<BackFieldsView>(R.id.backFieldsView)

        backFieldsView.setUpView(pkPass, passType)

        return view
    }
}
