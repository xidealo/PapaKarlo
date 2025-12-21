package com.bunbeauty.shared.domain.model.address

data class UserAddressCache(
    val userAddressList: List<UserAddress>,
    val userUuid: String,
    val cityUuid: String,
)
