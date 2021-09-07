package com.bunbeauty.domain.model

import android.os.Parcelable
import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.model.address.CafeAddress
import com.bunbeauty.domain.model.address.UserAddress
import com.bunbeauty.domain.model.product.OrderProduct
import kotlinx.parcelize.Parcelize

@Parcelize
class Order(
    var uuid: String = "",
    val isDelivery: Boolean,
    val userUuid: String,
    val phone: String,
    val userAddress: UserAddress?,
    val cafeAddress: CafeAddress?,
    var comment: String?,
    val deferredTime: String?,
    val time: Long,
    val code: String,
    val orderStatus: OrderStatus,
    val orderProductList: List<OrderProduct>,
    val cafeUuid: String
) : Parcelable