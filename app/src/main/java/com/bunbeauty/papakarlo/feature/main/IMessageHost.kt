package com.bunbeauty.papakarlo.feature.main

interface IMessageHost {

    fun showInfoMessage(text: String, isFabVisible: Boolean)
    fun showErrorMessage(text: String)
}
