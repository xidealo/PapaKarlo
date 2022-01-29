package com.bunbeauty.data.mapper.user_address

import com.bunbeauty.data.database.entity.user.UserAddressEntity
import com.bunbeauty.data.network.model.AddressServer
import com.bunbeauty.data.network.model.UserAddressPostServer
import com.bunbeauty.domain.model.address.CreatedUserAddress
import com.bunbeauty.domain.model.address.UserAddress

interface IUserAddressMapper {

    fun toModel(userAddress: UserAddressEntity): UserAddress
    fun toModel(address: AddressServer): UserAddress
    fun toEntityModel(address: AddressServer): UserAddressEntity
    fun toEntityModel(userAddress: UserAddress): UserAddressEntity
    fun toServerModel(userAddress: UserAddress): AddressServer
    fun toServerModel(userAddress: UserAddressEntity): AddressServer
    fun toPostServerModel(userAddress: UserAddressEntity): UserAddressPostServer
    fun toPostServerModel(userAddress: UserAddress): UserAddressPostServer
    fun toPostServerModel(createdUserAddress: CreatedUserAddress): UserAddressPostServer
}