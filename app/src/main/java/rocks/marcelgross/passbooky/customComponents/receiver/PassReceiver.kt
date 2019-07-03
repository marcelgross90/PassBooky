package rocks.marcelgross.passbooky.customComponents.receiver

import rocks.marcelgross.passbooky.pkpass.PKPass

interface PassReceiver {
    fun getPass(): PKPass
}