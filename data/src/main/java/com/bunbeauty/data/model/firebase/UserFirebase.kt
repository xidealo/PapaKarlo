package com.bunbeauty.data.model.firebase

import com.bunbeauty.data.model.order.UserOrder

data class UserFirebase(
    var phone: String = "",
    var email: String = "",
    var bonusList: ArrayList<Int> = arrayListOf(),
    var orders: ArrayList<UserOrder> = arrayListOf(),
    var addresses: HashMap<String, AddressFirebase> = hashMapOf()
)
