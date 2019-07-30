package rocks.marcelgross.passbooky.components.receiver

import rocks.marcelgross.passbooky.pkpass.PKPass

interface PassReceiver {
    fun getPass(): PKPass
}