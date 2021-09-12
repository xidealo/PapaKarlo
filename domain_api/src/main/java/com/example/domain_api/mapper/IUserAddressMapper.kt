package com.example.domain_api.mapper

import com.bunbeauty.domain.model.address.UserAddress
import com.example.domain_api.model.entity.user.UserAddressEntity

interface IUserAddressMapper {

    fun toModel(userAddress: UserAddressEntity): UserAddress
}