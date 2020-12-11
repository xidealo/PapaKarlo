package com.bunbeauty.papakarlo.ui.contacts

interface ContactsNavigator {
    fun goToAddress(longitude: Double, latitude: Double)
    fun goToTime()
    fun goToPhone(phone: String)
}