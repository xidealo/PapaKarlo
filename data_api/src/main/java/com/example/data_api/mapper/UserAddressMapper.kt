package com.example.data_api.mapper

import com.bunbeauty.domain.model.address.UserAddress
import com.example.domain_api.mapper.IUserAddressMapper
import com.example.domain_api.model.entity.user.UserAddressEntity
import com.example.domain_api.model.server.UserAddressServer
import javax.inject.Inject

class UserAddressMapper @Inject constructor() : IUserAddressMapper {

    override fun toModel(userAddress: UserAddressEntity): UserAddress {
        return UserAddress(
            uuid = userAddress.uuid,
            street = userAddress.street,
            house = userAddress.house,
            flat = userAddress.flat,
            entrance = userAddress.entrance,
            floor = userAddress.floor,
            comment = userAddress.comment,
            streetUuid = userAddress.streetUuid,
            userUuid = userAddress.userUuid,
        )
    }

    override fun toEntity(userAddress: UserAddressServer): UserAddressEntity {
        return UserAddressEntity(
            uuid = userAddress.uuid,
            street = userAddress.street,
            house = userAddress.house,
            flat = userAddress.flat,
            entrance = userAddress.entrance,
            floor = userAddress.floor,
            comment = userAddress.comment,
            streetUuid = userAddress.streetUuid,
            userUuid = userAddress.userUuid,
        )
    }
}