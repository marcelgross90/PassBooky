package rocks.marcelgross.passbooky

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import rocks.marcelgross.passbooky.customComponents.receiver.PassReceiver
import rocks.marcelgross.passbooky.fragment.EventTicketFragment
import rocks.marcelgross.passbooky.fragment.StoreCardFragment
import rocks.marcelgross.passbooky.pkpass.PKPass
import rocks.marcelgross.passbooky.pkpass.PassType

class CardActivity : AppCompatActivity(), PassReceiver {

    private val navigationMenuId = 1
    private val calenderMenuId = 2

    private lateinit var fm: FragmentManager
    private lateinit var contentContainer: View
    private lateinit var pass: PKPass
    private lateinit var fileName: String

    override fun getPass(): PKPass {
        return pass
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (menu != null) {
            val passContent = pass.passContent
            if (passContent.locations.isNotEmpty()) {
                val menuItem: MenuItem =
                    menu.add(Menu.NONE, navigationMenuId, Menu.NONE, R.string.open_in_maps)
                menuItem.icon = ContextCompat.getDrawable(this, R.drawable.ic_navigation)
                menuItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM)
            }
            if (passContent.relevantDate != null) {
                val menuItem: MenuItem =
                    menu.add(Menu.NONE, calenderMenuId, Menu.NONE, R.string.add_to_calendar)
                menuItem.icon = ContextCompat.getDrawable(this, R.drawable.ic_today)
                menuItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM)
            }
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            navigationMenuId -> {
                openInMaps()
                return true
            }
            calenderMenuId -> {
                saveInCalendar()
                return true
            }
        }
        return false
    }

    private fun saveInCalendar() {
        prepareCalendarIntent(pass.passContent, this)
    }

    private fun openInMaps() {
        prepareNavigationIntent(pass.passContent, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)

        contentContainer = findViewById(R.id.content_container)

        fm = supportFragmentManager

        initToolbar()

        fileName = intent.getStringExtra("fileName")

        pass = load(assets.open(fileName))
        val passType = pass.getPassType()

        var fragment: Fragment? = null

        when (passType) {
            PassType.STORE_CARD -> fragment = StoreCardFragment()
            PassType.EVENT_TICKET -> fragment = EventTicketFragment()
            PassType.UNKNOWN -> finish()
        }

        if (fragment != null) {
            val bundle = Bundle()
            bundle.putString(
                "fileName",
                fileName
            )
            fragment.arguments = bundle
            replaceFragment(
                fm,
                fragment
            )
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
