package com.bunbeauty.domain.model.profile

import com.bunbeauty.domain.model.address.UserAddress
import com.bunbeauty.domain.model.order.Order

data class Profile(
    val user: User,
    val addressList: List<UserAddress>,
    val orderList: List<Order>,
)
