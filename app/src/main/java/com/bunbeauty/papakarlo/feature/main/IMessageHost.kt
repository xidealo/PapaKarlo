package com.bunbeauty.papakarlo.feature.main

interface IMessageHost {

    fun showInfoMessage(text: String, paddingBottom: Int = 0)
    fun showErrorMessage(text: String)
}
