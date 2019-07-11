package rocks.marcelgross.passbooky

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File

fun shareUri(context: Context, uri: Uri) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.putExtra(Intent.EXTRA_STREAM, uri)
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    intent.type = "application/vnd.apple.pkpass"
    execShareIntent(context, intent)
}

fun execShareIntent(context: Context, intent: Intent) {
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    } else {
        Toast.makeText(
            context,
            R.string.cannot_resolve_action,
            Toast.LENGTH_SHORT
        ).show()
    }
}

fun shareFile(context: Context, file: File) {
    shareUri(context, getUriForFile(context, file))
}

fun getUriForFile(context: Context, file: File): Uri {
    return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
        Uri.fromFile(file)
    } else {
        FileProvider.getUriForFile(
            context,
            BuildConfig.APPLICATION_ID + ".provider",
            file
        )
    }
}
