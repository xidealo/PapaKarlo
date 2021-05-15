package com.bunbeauty.domain.model

import com.bunbeauty.domain.model.address.UserAddress

data class Profile(
    val uuid: String,
    val phone: String,
    val email: String?,
    val addressList: List<UserAddress>,
    val orderList: List<Order>,
)
