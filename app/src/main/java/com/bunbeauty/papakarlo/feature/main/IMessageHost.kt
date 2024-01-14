package com.bunbeauty.papakarlo.feature.main

interface IMessageHost {

    fun showInfoMessage(text: String, photoLink: String? = null)
    fun showErrorMessage(text: String)
}
