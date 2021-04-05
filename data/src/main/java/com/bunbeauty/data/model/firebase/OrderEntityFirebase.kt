package com.bunbeauty.data.model.firebase

import com.bunbeauty.data.enums.OrderStatus
import org.joda.time.DateTime

data class OrderEntityFirebase(
    var address: AddressFirebase = AddressFirebase(),
    var comment: String? = null,
    var phone: String = "",
    var time: Long = DateTime.now().millis,
    var orderStatus: OrderStatus = OrderStatus.NOT_ACCEPTED,
    var isDelivery: Boolean = true,
    var code: String = "",
    var email: String? = null,
    var deferred: String? = null
)
