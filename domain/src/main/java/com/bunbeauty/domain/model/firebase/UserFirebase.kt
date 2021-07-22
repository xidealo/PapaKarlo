package com.bunbeauty.domain.model.firebase

import com.bunbeauty.domain.model.ui.order.UserOrder

data class UserFirebase(
    var phone: String = "",
    var email: String = "",
    var bonusList: MutableList<Int> = arrayListOf(),
    var orders: ArrayList<UserOrder> = arrayListOf(),
    var addresses: HashMap<String, AddressFirebase> = hashMapOf()
)
