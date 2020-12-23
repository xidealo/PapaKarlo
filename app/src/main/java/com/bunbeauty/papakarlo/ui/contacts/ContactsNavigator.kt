package com.bunbeauty.papakarlo.ui.contacts

import com.bunbeauty.papakarlo.ui.base.BaseNavigator

interface ContactsNavigator {
    fun goToAddress(longitude: Double, latitude: Double)
    fun goToTime()
    fun goToPhone(phone: String)
}