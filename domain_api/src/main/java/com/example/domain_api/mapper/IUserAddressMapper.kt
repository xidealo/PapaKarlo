package com.example.domain_api.mapper

import com.bunbeauty.domain.model.address.UserAddress
import com.example.domain_api.model.entity.user.UserAddressEntity
import com.example.domain_api.model.server.UserAddressServer

interface IUserAddressMapper {

    fun toModel(userAddress: UserAddressEntity): UserAddress
    fun toEntityModel(userAddress: UserAddressServer): UserAddressEntity
    fun toEntityModel(userAddress: UserAddress): UserAddressEntity
    fun toServerModel(userAddress: UserAddress, userUuid: String): UserAddressServer
    fun toServerModel(userAddress: UserAddressEntity, userUuid: String): UserAddressServer
}