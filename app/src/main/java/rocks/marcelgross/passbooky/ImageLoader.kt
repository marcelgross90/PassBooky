package rocks.marcelgross.passbooky

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import com.google.gson.Gson
import rocks.marcelgross.passbooky.pkpass.PKPass
import rocks.marcelgross.passbooky.pkpass.PKPassFile
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStreamReader
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream


object ImageLoader {

    fun pkPassLoader(context: Context?, fileName: String): PKPassFile {
        val gson = Gson()
        var pkpassFile = PKPassFile(null, null, null, null, null, null)
        try {
            context?.let {
                val inputStream = it.assets.open(fileName)
                val zis = ZipInputStream(inputStream)

                try {
                    var count: Int
                    val buffer = ByteArray(8192)
                    var ze: ZipEntry?
                    var con = true
                    while (con) {
                        ze = zis.nextEntry
                        if (ze == null) {
                            con = false
                            continue
                        }
                        if (ze.isDirectory) {
                            continue
                        }

                        val byteArrayOutputStream = ByteArrayOutputStream()

                        try {
                            var foutCounter = true
                            while (foutCounter) {
                                count = zis.read(buffer)
                                if (count == -1) {
                                    foutCounter = false
                                } else {
                                    byteArrayOutputStream.write(buffer, 0, count)
                                }
                            }
                        } finally {
                            byteArrayOutputStream.close()
                        }

                        when {
                            ze.name.contains("pass") ->
                                pkpassFile = pkpassFile.copy(
                                    pass = gson.fromJson(
                                        InputStreamReader(ByteArrayInputStream(byteArrayOutputStream.toByteArray())),
                                        PKPass::class.java
                                    )
                                )
                            ze.name.contains("strip") ->
                                pkpassFile =
                                    pkpassFile.copy(
                                        strip = Drawable.createFromStream(
                                            ByteArrayInputStream(byteArrayOutputStream.toByteArray()),
                                            null
                                        )
                                    )
                            ze.name.contains("logo") ->
                                pkpassFile =
                                    pkpassFile.copy(
                                        logo = Drawable.createFromStream(
                                            ByteArrayInputStream(byteArrayOutputStream.toByteArray()),
                                            null
                                        )
                                    )
                            ze.name.contains("icon") ->
                                pkpassFile =
                                    pkpassFile.copy(
                                        icon = Drawable.createFromStream(
                                            ByteArrayInputStream(byteArrayOutputStream.toByteArray()),
                                            null
                                        )
                                    )
                            ze.name.contains("thumbnail") ->
                                pkpassFile =
                                    pkpassFile.copy(
                                        thumbnail = Drawable.createFromStream(
                                            ByteArrayInputStream(byteArrayOutputStream.toByteArray()),
                                            null
                                        )
                                    )
                            ze.name.contains("background") ->
                                pkpassFile =
                                    pkpassFile.copy(background = Drawable.createFromStream(
                                        ByteArrayInputStream(byteArrayOutputStream.toByteArray()),
                                        null
                                    )
                                    )
                        }

                        Log.d("mgr", ze.name)
                    }
                } finally {
                    zis.close()
                }
            }

        } catch (ex: Exception) {
            Log.d("mgr1", ex.message)
        }

        return pkpassFile
    }

    fun addImagesToEventTicket(eventTicket: PKPass, context: Context?) {
        try {
            context?.let {
                val ims = it.assets.open("event_ticket_logo.png")
                val logo = Drawable.createFromStream(ims, null)
                eventTicket.logo = logo

                val ims1 = it.assets.open("event_ticket_background.png")
                val background = Drawable.createFromStream(ims1, null)
                eventTicket.background = background

                val ims2 = it.assets.open("event_ticket_thumbnail.png")
                val thumbnail = Drawable.createFromStream(ims2, null)
                eventTicket.thumbnail = thumbnail

            }
        } catch (ex: Exception) {
            Log.d("mgr1", ex.message)
        }
    }

    fun addImagesToStoreCard(storeCard: PKPass, context: Context?) {
        try {
            context?.let {
                val ims = it.assets.open("logo_store_card.png")
                val logo = Drawable.createFromStream(ims, null)
                storeCard.logo = logo

                val ims1 = it.assets.open("strip_store_card.png")
                val strip = Drawable.createFromStream(ims1, null)
                storeCard.strip = strip
            }
        } catch (ex: Exception) {
            Log.d("mgr1", ex.message)
        }
    }
}