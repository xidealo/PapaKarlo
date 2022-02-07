package com.bunbeauty.data.mapper.user_address

import com.bunbeauty.data.database.entity.user.UserAddressEntity
import com.bunbeauty.data.mapper.street.IStreetMapper
import com.bunbeauty.data.network.model.AddressServer
import com.bunbeauty.data.network.model.UserAddressPostServer
import com.bunbeauty.domain.model.address.CreatedUserAddress
import com.bunbeauty.domain.model.address.UserAddress

class UserAddressMapper  constructor(
    private val streetMapper: IStreetMapper
) : IUserAddressMapper {

    override fun toModel(userAddress: UserAddressEntity): UserAddress {
        return UserAddress(
            uuid = userAddress.uuid,
            street = streetMapper.toModel(userAddress.street),
            house = userAddress.house,
            flat = userAddress.flat,
            entrance = userAddress.entrance,
            floor = userAddress.floor,
            comment = userAddress.comment,
            userUuid = userAddress.userUuid,
        )
    }

    override fun toModel(address: AddressServer): UserAddress {
        return UserAddress(
            uuid = address.uuid,
            street = streetMapper.toModel(address.street),
            house = address.house,
            flat = address.flat,
            entrance = address.entrance,
            floor = address.floor,
            comment = address.comment,
            userUuid = address.userUuid,
        )
    }

    override fun toEntityModel(address: AddressServer): UserAddressEntity {
        return UserAddressEntity(
            uuid = address.uuid,
            street = streetMapper.toEntityModel(address.street),
            house = address.house,
            flat = address.flat,
            entrance = address.entrance,
            floor = address.floor,
            comment = address.comment,
            userUuid = address.userUuid,
        )
    }

    override fun toEntityModel(userAddress: UserAddress): UserAddressEntity {
        return UserAddressEntity(
            uuid = userAddress.uuid,
            street = streetMapper.toEntityModel(userAddress.street),
            house = userAddress.house,
            flat = userAddress.flat,
            entrance = userAddress.entrance,
            floor = userAddress.floor,
            comment = userAddress.comment,
            userUuid = userAddress.userUuid,
        )
    }

    override fun toServerModel(userAddress: UserAddress): AddressServer {
        return AddressServer(
            uuid = userAddress.uuid,
            street = streetMapper.toServerModel(userAddress.street),
            house = userAddress.house,
            flat = userAddress.flat,
            entrance = userAddress.entrance,
            floor = userAddress.floor,
            comment = userAddress.comment,
            userUuid = userAddress.userUuid
        )
    }

    override fun toServerModel(userAddress: UserAddressEntity): AddressServer {
        return AddressServer(
            uuid = userAddress.uuid,
            street = streetMapper.toServerModel(userAddress.street),
            house = userAddress.house,
            flat = userAddress.flat,
            entrance = userAddress.entrance,
            floor = userAddress.floor,
            comment = userAddress.comment,
            userUuid = userAddress.userUuid
        )
    }

    override fun toPostServerModel(userAddress: UserAddressEntity): UserAddressPostServer {
        return UserAddressPostServer(
            streetUuid = userAddress.street.uuid,
            house = userAddress.house,
            flat = userAddress.flat,
            entrance = userAddress.entrance,
            floor = userAddress.floor,
            comment = userAddress.comment,
            isVisible = true
        )
    }

    override fun toPostServerModel(userAddress: UserAddress): UserAddressPostServer {
        return UserAddressPostServer(
            streetUuid = userAddress.street.uuid,
            house = userAddress.house,
            flat = userAddress.flat,
            entrance = userAddress.entrance,
            floor = userAddress.floor,
            comment = userAddress.comment,
            isVisible = true
        )
    }

    override fun toPostServerModel(createdUserAddress: CreatedUserAddress): UserAddressPostServer {
        return UserAddressPostServer(
            house = createdUserAddress.house,
            flat = createdUserAddress.flat,
            entrance = createdUserAddress.entrance,
            floor = createdUserAddress.floor,
            comment = createdUserAddress.comment,
            streetUuid = createdUserAddress.streetUuid,
            isVisible = createdUserAddress.isVisible,
        )
    }
}