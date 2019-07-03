package rocks.marcelgross.passbooky

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import rocks.marcelgross.passbooky.fragment.EventTicketFragment
import rocks.marcelgross.passbooky.fragment.StoreCardFragment
import rocks.marcelgross.passbooky.pkpass.PassType

class CardActivity : AppCompatActivity() {

    private lateinit var fm: FragmentManager
    private lateinit var contentContainer: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)

        contentContainer = findViewById(R.id.content_container)

        fm = supportFragmentManager

        initToolbar()

        val passType = intent.getStringExtra("type")
        var fragment: Fragment? = null

        when (passType) {
            PassType.STORE_CARD.name -> fragment = StoreCardFragment()
            PassType.EVENT_TICKET.name -> fragment = EventTicketFragment()
        }

        if (fragment != null) {
            replaceFragment(
                fm,
                fragment
            )
        } else {
            finish()
        }
    }

    private fun replaceFragment(fm: FragmentManager, fragment: Fragment) {
        fm.beginTransaction()
            .replace(
                R.id.content_container,
                fragment, fragment.javaClass.name
            )
            .addToBackStack(null)
            .commit()
    }

    private fun initToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onBackPressed() {
        if (fm.backStackEntryCount > 1)
            super.onBackPressed()
        else
            finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
