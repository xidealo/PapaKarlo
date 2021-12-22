package com.example.domain_api.mapper

import com.bunbeauty.domain.model.address.UserAddress
import com.example.domain_api.model.entity.user.UserAddressEntity
import com.example.domain_api.model.server.AddressServer
import com.example.domain_api.model.server.UserAddressPostServer

interface IUserAddressMapper {

    fun toModel(userAddress: UserAddressEntity): UserAddress
    fun toModel(address: AddressServer): UserAddress
    fun toEntityModel(address: AddressServer): UserAddressEntity
    fun toEntityModel(userAddress: UserAddress): UserAddressEntity
    fun toServerModel(userAddress: UserAddress): AddressServer
    fun toServerModel(userAddress: UserAddressEntity): AddressServer
    fun toPostServerModel(userAddress: UserAddressEntity): UserAddressPostServer
    fun toPostServerModel(userAddress: UserAddress): UserAddressPostServer
}