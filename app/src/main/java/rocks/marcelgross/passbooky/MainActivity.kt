package rocks.marcelgross.passbooky

import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import rocks.marcelgross.passbooky.customComponents.StoreCardView
import rocks.marcelgross.passbooky.pkpass.PKPass
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gson = Gson()
        val storeCard: PKPass = gson.fromJson(TestsPasses.storeCard(), PKPass::class.java)

        try {
            val ims = assets.open("logo3x.png")
            val logo = Drawable.createFromStream(ims, null)
            storeCard.logo = logo

            val ims1 = assets.open("strip3x.png")
            val strip = Drawable.createFromStream(ims1, null)
            storeCard.strip = strip
        } catch (ex: Exception) {
            android.util.Log.d("mgr1", ex.message)
        }

        val eventTicket: PKPass = gson.fromJson(TestsPasses.eventTicket(), PKPass::class.java)

        android.util.Log.d("mgr", storeCard.toString())
        val view = findViewById<StoreCardView>(R.id.storeCard)

        view.setUpView(storeCard)
    }
}
