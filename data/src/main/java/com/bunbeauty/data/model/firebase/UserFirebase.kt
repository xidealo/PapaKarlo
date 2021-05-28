package com.bunbeauty.data.model.firebase

data class UserFirebase(
    var phone: String = "",
    var email: String = "",
    var bonusList: ArrayList<Int> = arrayListOf()
)
