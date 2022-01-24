package com.example.domain_firebase.model.firebase.order

import com.bunbeauty.domain.enums.OrderStatus
import com.example.domain_firebase.model.firebase.address.UserAddressFirebase

data class OrderEntityFirebase(
    var isDelivery: Boolean = true,
    var phone: String = "",
    var address: UserAddressFirebase? = null,
    var comment: String? = null,
    var deferredTime: String? = null,
    var spentBonuses: Int? = null,
    var accruedBonuses: Int? = null,
    var orderStatus: OrderStatus = OrderStatus.NOT_ACCEPTED,
    var code: String = "",
    var time: Long = 0,
    var userUuid: String? = null
)