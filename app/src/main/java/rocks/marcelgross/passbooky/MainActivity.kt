package rocks.marcelgross.passbooky

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val chris = findViewById<Button>(R.id.chris)
        val admiral = findViewById<Button>(R.id.admiral)
        val cine = findViewById<Button>(R.id.cine)

        chris.setOnClickListener {
            val intent = Intent(this, CardActivity::class.java)
            intent.putExtra("fileName", "cbr-businesscard.pkpass")
            startActivity(intent)
        }

        admiral.setOnClickListener {
            val intent = Intent(this, CardActivity::class.java)
            intent.putExtra("fileName", "pass.pkpass")
            startActivity(intent)
        }

        cine.setOnClickListener {
            val intent = Intent(this, CardActivity::class.java)
            intent.putExtra("fileName", "cine.pkpass")
            startActivity(intent)
        }
    }
}
