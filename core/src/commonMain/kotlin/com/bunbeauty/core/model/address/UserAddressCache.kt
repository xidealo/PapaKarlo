package com.bunbeauty.core.model.address

data class UserAddressCache(
    val userAddressList: List<UserAddress>,
    val userUuid: String,
    val cityUuid: String,
)
