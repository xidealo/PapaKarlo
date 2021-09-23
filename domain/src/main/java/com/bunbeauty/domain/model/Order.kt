package com.bunbeauty.domain.model

import android.os.Parcelable
import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.model.product.OrderProduct
import kotlinx.parcelize.Parcelize

@Parcelize
class Order(
    var uuid: String = "",
    val isDelivery: Boolean,
    val address: String,
    var comment: String?,
    val deferredTime: Long?,
    val time: Long,
    val code: String,
    val orderStatus: OrderStatus,
    val orderProductList: List<OrderProduct>,
    val userUuid: String,
    val addressUuid: String?,
) : Parcelable