package rocks.marcelgross.passbooky

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import rocks.marcelgross.passbooky.pkpass.PassType

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        test()
        val storeCardBtn = findViewById<Button>(R.id.storeCard)
        val eventTicketBtn = findViewById<Button>(R.id.eventTicket)

        storeCardBtn.setOnClickListener {
            val intent = Intent(this, CardActivity::class.java)
            intent.putExtra("type", PassType.STORE_CARD.name)
            startActivity(intent)
        }

        eventTicketBtn.setOnClickListener {
            val intent = Intent(this, CardActivity::class.java)
            intent.putExtra("type", PassType.EVENT_TICKET.name)
            startActivity(intent)
        }
    }

    private fun test() {
        val cbr = ImageLoader.pkPassLoader(this, "cbr-businesscard.pkpass")
        Log.d("mgr", "next card")
        val pass = ImageLoader.pkPassLoader(this, "pass.pkpass")

        Log.d("mgr", pass.toString())
    }
}
