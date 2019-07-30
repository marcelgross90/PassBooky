package rocks.marcelgross.passbooky

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(state: Bundle?) {
        super.onCreate(state)

        // it's important _not_ to inflate a layout file here
        // because that would happen after the app is fully
        // initialized what is too late

        startActivity(Intent(applicationContext, MainActivity::class.java))
        finish()
    }
}
