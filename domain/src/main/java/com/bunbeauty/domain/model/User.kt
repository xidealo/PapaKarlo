package com.bunbeauty.domain.model

import com.bunbeauty.domain.model.address.UserAddress

data class User(
    val uuid: String,
    val phone: String,
    val email: String?,
    val addressList: List<UserAddress>
)
