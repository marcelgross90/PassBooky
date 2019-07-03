package rocks.marcelgross.passbooky

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val storeCardBtn = findViewById<Button>(R.id.storeCard)
        val eventTicketBtn = findViewById<Button>(R.id.eventTicket)
        val cardBtn = findViewById<Button>(R.id.card)

        storeCardBtn.setOnClickListener {
            val intent = Intent(this, StoreCardActivity::class.java)
            startActivity(intent)
        }

        eventTicketBtn.setOnClickListener {
            val intent = Intent(this, EventTicketActivity::class.java)
            startActivity(intent)
        }

        cardBtn.setOnClickListener {
            val intent = Intent(this, CardActivity::class.java)
            startActivity(intent)
        }
    }
}
