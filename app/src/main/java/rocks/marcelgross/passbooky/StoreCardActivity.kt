package rocks.marcelgross.passbooky

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import rocks.marcelgross.passbooky.customComponents.StoreCardView
import rocks.marcelgross.passbooky.pkpass.PKPass

class StoreCardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_card)

        val gson = Gson()
        val storeCard: PKPass = gson.fromJson(TestsPasses.storeCard(), PKPass::class.java)
        addImagesToStoreCard(storeCard)

        val view = findViewById<StoreCardView>(R.id.card)

        view.setUpView(storeCard)
    }

    private fun addImagesToStoreCard(storeCard: PKPass) {
        try {
            val ims = assets.open("logo_store_card.png")
            val logo = Drawable.createFromStream(ims, null)
            storeCard.logo = logo

            val ims1 = assets.open("strip_store_card.png")
            val strip = Drawable.createFromStream(ims1, null)
            storeCard.strip = strip
        } catch (ex: Exception) {
            Log.d("mgr1", ex.message)
        }
    }
}
