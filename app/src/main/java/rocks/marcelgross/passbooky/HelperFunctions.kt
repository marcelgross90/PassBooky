package rocks.marcelgross.passbooky

import android.content.Context
import android.content.Intent
import android.net.Uri
import rocks.marcelgross.passbooky.pkpass.PKPass
import rocks.marcelgross.passbooky.pkpass.PassContent
import rocks.marcelgross.passbooky.pkpass.PassStructure
import rocks.marcelgross.passbooky.pkpass.PassType

fun getPass(pkPass: PKPass): PassStructure? {
    val type = pkPass.getPassType()
    val content = pkPass.passContent
    return when (type) {
        PassType.EVENT_TICKET -> content.eventTicket
        PassType.STORE_CARD -> content.storeCard
        PassType.UNKNOWN -> null
    }
}

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

fun getContentForType(passContent: PassContent, passType: PassType): PassStructure? {
    return when (passType) {
        PassType.EVENT_TICKET -> passContent.eventTicket
        PassType.STORE_CARD -> passContent.storeCard
        PassType.UNKNOWN -> null
    }
}