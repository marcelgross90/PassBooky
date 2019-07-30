package rocks.marcelgross.passbooky.pkpass

import android.content.Context
import android.graphics.drawable.Drawable
import com.google.gson.Gson
import java.io.ByteArrayInputStream
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader
import java.util.Date
import java.util.zip.ZipInputStream

const val baseDirName = "passes"
const val storeCardDirName = "storeCards"
const val eventTicketsDirName = "eventTickets"
const val unknownDirName = "unknown"

fun getPassFields(pkPass: PKPass): PassFields? {
    val type = pkPass.getPassType()
    val content = pkPass.passContent
    return when (type) {
        PassType.EVENT_TICKET -> content.eventTicket
        PassType.STORE_CARD -> content.storeCard
        PassType.UNKNOWN -> null
    }
}

fun save(passFile: InputStream, context: Context) {
    val tempFile = saveToTemp(passFile, context)
    val pass = load(tempFile.inputStream())
    val baseDir = context.getDir(baseDirName, Context.MODE_PRIVATE)

    when (pass.getPassType()) {
        PassType.EVENT_TICKET -> {
            writeFile(
                baseDir,
                tempFile,
                eventTicketsDirName
            )
        }
        PassType.STORE_CARD -> {
            writeFile(
                baseDir,
                tempFile,
                storeCardDirName
            )
        }
        PassType.UNKNOWN -> {
            writeFile(
                baseDir,
                tempFile,
                unknownDirName
            )
        }
    }
    tempFile.delete()
}

private fun saveToTemp(passFile: InputStream, context: Context): File {
    val tempFile = File.createTempFile("temp", null, context.cacheDir)
    tempFile.outputStream().use {
        it.write(passFile.readBytes())
    }

    return tempFile
}

private fun writeFile(baseDir: File, tempFile: File, folderName: String) {
    val timeStamp = Date().time
    val typeFolder = getOrCreateTypeFolder(baseDir, folderName)

    File(typeFolder, "$timeStamp.pkpass").outputStream().use {
        it.write(tempFile.readBytes())
    }
}

private fun getOrCreateTypeFolder(baseDir: File, folderName: String): File {
    val foldersInBaseDir = baseDir.listFiles()
    if (foldersInBaseDir.isEmpty() || foldersInBaseDir.none { it.name == folderName }) {
        createFolder(baseDir, folderName)
    }
    return baseDir.listFiles().filter { it.name == folderName }[0]
}

private fun createFolder(baseDir: File, folderName: String) {
    if (baseDir.listFiles().none { it.name == folderName }) {
        val created = File(baseDir, folderName).mkdir()
        if (!created) {
            throw Exception("Folder $folderName could not be created")
        }
    }
}

private fun createInitialFolders(baseDir: File) {
    createFolder(
        baseDir,
        eventTicketsDirName
    )
    createFolder(
        baseDir,
        storeCardDirName
    )
    createFolder(
        baseDir,
        unknownDirName
    )
}

fun getPassFile(context: Context, passName: String): File? {
    for (file in context.getDir(baseDirName, Context.MODE_PRIVATE).listFiles()) {
        for (listFile in file.listFiles()) {
            if (listFile.name == passName) {
                return listFile
            }
        }
    }
    return null
}

fun getPass(context: Context, passName: String): PKPass? {
    val file = getPassFile(context, passName) ?: return null

    return load(file.inputStream())
}

fun getPasses(context: Context, passType: PassType? = null): List<Pair<String, PKPass>> {
    val baseDir = context.getDir(baseDirName, Context.MODE_PRIVATE)
    createInitialFolders(baseDir)
    val passesDir = baseDir.listFiles()
    when (passType) {
        PassType.STORE_CARD -> {
            val storeCards = passesDir.filter { it.name == storeCardDirName }[0].listFiles()
            return storeCards.filter { !it.isDirectory }
                .map { Pair(it.name, load(it.inputStream())) }
        }
        PassType.EVENT_TICKET -> {
            val eventTickets =
                passesDir.filter { it.name == eventTicketsDirName }[0].listFiles()
            return eventTickets.filter { !it.isDirectory }
                .map { Pair(it.name, load(it.inputStream())) }
        }
        PassType.UNKNOWN -> {
            val unknownPasses =
                passesDir.filter { it.name == unknownDirName }[0].listFiles()
            return unknownPasses.filter { !it.isDirectory }
                .map { Pair(it.name, load(it.inputStream())) }
        }
        else -> {
            val storeCards = passesDir.filter { it.name == storeCardDirName }[0].listFiles()
            val eventTickets =
                passesDir.filter { it.name == eventTicketsDirName }[0].listFiles()
            val unknownPasses =
                passesDir.filter { it.name == unknownDirName }[0].listFiles()

            val passes = mutableListOf<Pair<String, PKPass>>()

            passes.addAll(
                storeCards.filter { !it.isDirectory }
                    .map { Pair(it.name, load(it.inputStream())) }
            )
            passes.addAll(
                eventTickets.filter { !it.isDirectory }
                    .map { Pair(it.name, load(it.inputStream())) }
            )
            passes.addAll(
                unknownPasses.filter { !it.isDirectory }
                    .map { Pair(it.name, load(it.inputStream())) }
            )

            return passes
        }
    }
}

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