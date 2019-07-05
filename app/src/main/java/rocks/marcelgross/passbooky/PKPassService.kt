package rocks.marcelgross.passbooky

import android.content.Context
import android.graphics.drawable.Drawable
import com.google.gson.Gson
import rocks.marcelgross.passbooky.PKPassService.copy
import rocks.marcelgross.passbooky.pkpass.PKPass
import rocks.marcelgross.passbooky.pkpass.PassContent
import rocks.marcelgross.passbooky.pkpass.PassType
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader
import java.util.Date
import java.util.zip.ZipInputStream

const val baseDirName = "passes"
const val storeCardDirName = "storeCards"
const val eventTicketsDirName = "eventTickets"
const val unknownDirName = "unknown"

fun savePass(passFile: InputStream, context: Context): PKPass {
    val copy = passFile.copy()
    val pass = PKPassService.load(copy.copy())
    val baseDir = context.getDir(baseDirName, Context.MODE_PRIVATE)
    createInitialFolders(baseDir)

    when (pass.getPassType()) {
        PassType.EVENT_TICKET -> {
            val dir = baseDir.listFiles().filter { it.name == eventTicketsDirName }[0]
            File(dir, "${Date().time}-eventTicket.pkpass").outputStream().use {
                it.write(copy.readBytes())
            }
        }
        PassType.STORE_CARD -> {
            val dir = baseDir.listFiles().filter { it.name == storeCardDirName }[0]
            File(dir, "${Date().time}-storeCard.pkpass").outputStream().use {
                it.write(copy.readBytes())
            }
        }
        PassType.UNKNOWN -> {
            val dir = baseDir.listFiles().filter { it.name == unknownDirName }[0]
            File(dir, "${Date().time}-unknown.pkpass").outputStream().use {
                it.write(copy.readBytes())
            }
        }
    }

    return pass
}

private fun createInitialFolders(baseDir: File) {
    File(baseDir.path, eventTicketsDirName).mkdir()
    File(baseDir.path, storeCardDirName).mkdir()
    File(baseDir.path, unknownDirName).mkdir()
}

fun getTempPasses(context: Context): List<Pair<String, PKPass>> {
    return listOf(
        Pair("cbr-businesscard.pkpass", PKPassService.load(context.assets.open("cbr-businesscard.pkpass"))),
        Pair("pass.pkpass", PKPassService.load(context.assets.open("pass.pkpass"))),
        Pair("cine.pkpass", PKPassService.load(context.assets.open("cine.pkpass")))
    )
}

fun getPasses(context: Context, passType: PassType? = null): List<PKPass> {
    val baseDir = context.getDir(baseDirName, Context.MODE_PRIVATE)
    createInitialFolders(baseDir)
    val passesDir = baseDir.listFiles()
    when (passType) {
        PassType.STORE_CARD -> {
            val storeCards = passesDir.filter { it.name == storeCardDirName }[0].listFiles()
            return storeCards.map { PKPassService.load(it.inputStream()) }
        }
        PassType.EVENT_TICKET -> {
            val eventTickets = passesDir.filter { it.name == eventTicketsDirName }[0].listFiles()
            return eventTickets.map { PKPassService.load(it.inputStream()) }
        }
        PassType.UNKNOWN -> {
            val unknownPasses = passesDir.filter { it.name == unknownDirName }[0].listFiles()
            return unknownPasses.map { PKPassService.load(it.inputStream()) }
        }
        else -> {
            val storeCards = passesDir.filter { it.name == storeCardDirName }[0].listFiles()
            val eventTickets = passesDir.filter { it.name == eventTicketsDirName }[0].listFiles()
            val unknownPasses = passesDir.filter { it.name == unknownDirName }[0].listFiles()

            val passes = mutableListOf<PKPass>()

            passes.addAll(storeCards.map { PKPassService.load(it.inputStream()) })
            passes.addAll(eventTickets.map { PKPassService.load(it.inputStream()) })
            passes.addAll(unknownPasses.map { PKPassService.load(it.inputStream()) })

            return passes
        }
    }
}


object PKPassService {


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

    fun InputStream.copy(): InputStream {
        val byteArrayOutputStream = ByteArrayOutputStream()
        this.use { input ->
            byteArrayOutputStream.use { output ->
                input.copyTo(output)
            }
        }

        return ByteArrayInputStream(byteArrayOutputStream.toByteArray())
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