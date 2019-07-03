package rocks.marcelgross.passbooky

import android.graphics.drawable.Drawable
import com.google.gson.Gson
import rocks.marcelgross.passbooky.pkpass.PKPass
import rocks.marcelgross.passbooky.pkpass.PassContent
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.util.zip.ZipInputStream

object PKPassLoader {

    fun load(passFile: InputStream): PKPass {
        val pass = PKPass()
        ZipInputStream(passFile).use { zis ->
            while (true) {
                val zipEntry = zis.nextEntry ?: break
                if (zipEntry.isDirectory) {
                    continue
                }
                val bytes = zis.readBytes()
                with(zipEntry.name) {
                    when {
                        startsWith("pass") ->
                            pass.passContent = createPass(bytes)
                        startsWith("strip") ->
                            pass.strip = createDrawable(bytes)
                        startsWith("logo") ->
                            pass.logo = createDrawable(bytes)
                        startsWith("icon") ->
                            pass.icon = createDrawable(bytes)
                        startsWith("thumbnail") ->
                            pass.thumbnail = createDrawable(bytes)
                        startsWith("background") ->
                            pass.background = createDrawable(bytes)
                    }
                }
            }
        }
        return pass
    }

    private fun createPass(bytes: ByteArray): PassContent {
        val gson = Gson()
        return gson.fromJson(
            InputStreamReader(ByteArrayInputStream(bytes)),
            PassContent::class.java
        )
    }

    private fun createDrawable(bytes: ByteArray): Drawable {
        return Drawable.createFromStream(
            ByteArrayInputStream(bytes),
            null
        )
    }
}