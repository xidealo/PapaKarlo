package com.example.domain_api.mapper

import com.bunbeauty.domain.model.address.UserAddress
import com.example.domain_api.model.entity.user.UserAddressEntity
import com.example.domain_api.model.server.UserAddressPostServer
import com.example.domain_api.model.server.UserAddressServer

interface IUserAddressMapper {

    fun toModel(userAddress: UserAddressEntity): UserAddress
    fun toModel(userAddress: UserAddressServer): UserAddress
    fun toEntityModel(userAddress: UserAddressServer): UserAddressEntity
    fun toEntityModel(userAddress: UserAddress): UserAddressEntity
    fun toServerModel(userAddress: UserAddress): UserAddressServer
    fun toServerModel(userAddress: UserAddressEntity): UserAddressServer
    fun toPostServerModel(userAddress: UserAddressEntity): UserAddressPostServer
    fun toPostServerModel(userAddress: UserAddress): UserAddressPostServer
}