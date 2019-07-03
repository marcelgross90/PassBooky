package rocks.marcelgross.passbooky.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import rocks.marcelgross.passbooky.R
import rocks.marcelgross.passbooky.customComponents.BackFieldsView
import rocks.marcelgross.passbooky.customComponents.receiver.PassReceiver

class BackFieldsFragment : Fragment() {

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
        val view = inflater.inflate(R.layout.fragment_back_fields, container, false)

        val backFieldsView = view.findViewById<BackFieldsView>(R.id.backFieldsView)
        backFieldsView.setUpView(passReceiver.getPass())

        return view
    }
}
