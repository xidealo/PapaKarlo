package com.bunbeauty.domain.model.firebase

import com.bunbeauty.domain.model.firebase.address.UserAddressFirebase
import com.bunbeauty.domain.model.firebase.cafe.CafeAddressFirebase
import com.bunbeauty.domain.model.firebase.order.UserOrderFirebase

data class UserFirebase(
    var phone: String = "",
    var email: String = "",
    var orders: HashMap<String, UserOrderFirebase> = hashMapOf(),
    var addresses: HashMap<String, UserAddressFirebase> = hashMapOf()
)