package com.bunbeauty.domain.model.ui.order

import android.os.Parcelable
import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.model.ui.address.Address
import kotlinx.parcelize.Parcelize

@Parcelize
class OrderUI(
    val uuid: String = "",
    val isDelivery: Boolean,
    val userUuid: String,
    val phone: String,
    val address: Address,
    var comment: String?,
    val deferredTime: String?,
    val time: Long,
    val code: String,
    val orderStatus: OrderStatus,
) : Parcelable