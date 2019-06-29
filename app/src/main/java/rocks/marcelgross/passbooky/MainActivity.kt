package rocks.marcelgross.passbooky

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val storeCardBtn = findViewById<Button>(R.id.storeCard)
        val eventTicketBtn = findViewById<Button>(R.id.eventTicket)

        storeCardBtn.setOnClickListener {
            val intent = Intent(this, StoreCardActivity::class.java)
            startActivity(intent)
        }

        eventTicketBtn.setOnClickListener {
            val intent = Intent(this, EventTicketActivity::class.java)
            startActivity(intent)
        }
    }
}
