package com.bunbeauty.domain.model.firebase.order

import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.model.firebase.address.UserAddressFirebase
import org.joda.time.DateTime

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
    var time: Long = DateTime.now().millis,
    var userUuid: String? = null
)
