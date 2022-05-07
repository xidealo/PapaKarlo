package com.bunbeauty.data.mapper.user_address

import com.bunbeauty.data.network.model.AddressServer
import com.bunbeauty.data.network.model.UserAddressPostServer
import com.bunbeauty.shared.domain.model.address.CreatedUserAddress
import com.bunbeauty.shared.domain.model.address.UserAddress
import database.UserAddressEntity

interface IUserAddressMapper {

    fun toUserAddress(userAddressEntity: UserAddressEntity): UserAddress
    fun toUserAddress(addressServer: AddressServer): UserAddress
    fun toUserAddressEntity(addressServer: AddressServer): UserAddressEntity
    fun toUserAddressEntity(userAddress: UserAddress): UserAddressEntity
    fun toAddressServer(userAddress: UserAddress): AddressServer
    fun toAddressServer(userAddressEntity: UserAddressEntity): AddressServer
    fun toUserAddressPostServer(userAddressEntity: UserAddressEntity): UserAddressPostServer
    fun toUserAddressPostServer(userAddress: UserAddress): UserAddressPostServer
    fun toUserAddressPostServer(createdUserAddress: CreatedUserAddress): UserAddressPostServer
}