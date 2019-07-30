package rocks.marcelgross.passbooky

import android.content.Context
import android.content.Intent
import android.net.Uri
import rocks.marcelgross.passbooky.pkpass.PassContent

fun prepareCalendarIntent(passContent: PassContent, context: Context) {
    val intent = Intent(Intent.ACTION_EDIT)
    intent.type = "vnd.android.cursor.item/event"
    intent.putExtra("beginTime", passContent.relevantDate?.time)
    intent.putExtra("title", passContent.description)
    context.startActivity(intent)
}

fun prepareNavigationIntent(passContent: PassContent, context: Context) {
    val position = passContent.locations[0]
    val uri =
        "geo:${position.latitude},${position.longitude}?q=${position.latitude},${position.longitude}"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
    context.startActivity(intent)
}