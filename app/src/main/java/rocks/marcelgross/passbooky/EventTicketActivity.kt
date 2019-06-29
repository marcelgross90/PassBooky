package rocks.marcelgross.passbooky

import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import rocks.marcelgross.passbooky.customComponents.EventTicketView
import rocks.marcelgross.passbooky.pkpass.PKPass
import java.lang.Exception
import android.content.Intent
import android.view.MenuItem


class EventTicketActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_ticket)

        val gson = Gson()

        val eventTicket: PKPass = gson.fromJson(TestsPasses.eventTicket(), PKPass::class.java)
        addImagesToEventTicket(eventTicket)

        val view = findViewById<EventTicketView>(R.id.card)
        view.setUpView(eventTicket)
    }

    private fun addImagesToEventTicket(eventTicket: PKPass) {
        try {
            val ims = assets.open("event_ticket_logo.png")
            val logo = Drawable.createFromStream(ims, null)
            eventTicket.logo = logo

            val ims1 = assets.open("event_ticket_background.png")
            val background = Drawable.createFromStream(ims1, null)
            eventTicket.background = background

            val ims2 = assets.open("event_ticket_thumbnail.png")
            val thumbnail = Drawable.createFromStream(ims2, null)
            eventTicket.thumbnail = thumbnail


        } catch (ex: Exception) {
            Log.d("mgr1", ex.message)
        }
    }
}
