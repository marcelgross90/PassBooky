package rocks.marcelgross.passbooky.pkpass

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import rocks.marcelgross.passbooky.app.db
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

const val baseDirName = "passes"

enum class ImageTypes(val type: String) {
    STRIP("strip"),
    THUMBNAIL("thumbnail"),
    ICON("icon"),
    BACKGROUND("background"),
    LOGO("logo")
}

fun getUriForImage(path: String, image: ImageTypes): Uri? {
    val files = File(path).listFiles().filter { it.name.contains(image.type) }.sortedDescending()
    if (files.isEmpty()) {
        return null
    }

    return try {
        Uri.parse(files[0].absolutePath)
    } catch (ex: Exception) {
        null
    }
}

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

    var id: Long = 0
    GlobalScope.launch {
        id = db.insertPass(pass)
    }

    if (id < 1) {
        // todo throw exception?!?
        return
    }

    val baseDir = context.getDir(baseDirName, Context.MODE_PRIVATE)
    val folder = getOrCreateFolderForPass(baseDir, id.toString())
    ZipInputStream(tempFile.inputStream()).use { zis ->
        while (true) {
            val zipEntry = zis.nextEntry ?: break
            if (zipEntry.isDirectory) {
                continue
            }
            File(folder, zipEntry.name).outputStream().use {
                it.write(zis.readBytes())
            }
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

private fun getOrCreateFolderForPass(baseDir: File, folderName: String): File {
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

 fun buildZip(path: String, context: Context): File {
    val passFiles = File(path).listFiles()
    val tempFile = File.createTempFile("temp", "test", context.cacheDir)

    ZipOutputStream(BufferedOutputStream(FileOutputStream(tempFile))).use { out ->
        for (file in passFiles) {
            FileInputStream(file).use { fi ->
                BufferedInputStream(fi).use { origin ->
                    val entry = ZipEntry(file.name)
                    out.putNextEntry(entry)
                    origin.copyTo(out, 1024)
                }
            }
        }
    }

    return tempFile
}

fun getPass(path: String): PKPass? {
    val passFiles = File(path).listFiles()

    val pass = PKPass()

    val passContent = passFiles.filter { it.name.startsWith("pass") }[0].readBytes()

    pass.passContent = createPass(passContent)
    pass.strip = Drawable.createFromPath(getUriForImage(path, ImageTypes.STRIP)?.path)
    pass.logo = Drawable.createFromPath(getUriForImage(path, ImageTypes.LOGO)?.path)
    pass.icon = Drawable.createFromPath(getUriForImage(path, ImageTypes.ICON)?.path)
    pass.thumbnail = Drawable.createFromPath(getUriForImage(path, ImageTypes.THUMBNAIL)?.path)
    pass.background = Drawable.createFromPath(getUriForImage(path, ImageTypes.BACKGROUND)?.path)

    return pass
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