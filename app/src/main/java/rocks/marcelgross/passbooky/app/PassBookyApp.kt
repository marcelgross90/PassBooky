package rocks.marcelgross.passbooky

import rocks.marcelgross.passbooky.database.Database

import android.app.Application

val db = Database()

class PassBookyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        db.open(this)
    }
}
