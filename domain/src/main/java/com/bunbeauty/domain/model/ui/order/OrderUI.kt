package com.bunbeauty.domain.model.ui.order

import android.os.Parcelable
import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.model.ui.CartProduct
import com.bunbeauty.domain.model.ui.address.Address
import com.bunbeauty.domain.model.ui.address.CafeAddress
import com.bunbeauty.domain.model.ui.address.UserAddress
import kotlinx.parcelize.Parcelize

@Parcelize
class OrderUI(
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
    val cartProducts: List<CartProduct>,
    val cafeUuid: String
) : Parcelable