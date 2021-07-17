package com.bunbeauty.domain.model.firebase

import com.bunbeauty.domain.enums.OrderStatus
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
    var deferredTime: String? = null,
    var bonus: Int? = null,
    var userId: String? = null
)
