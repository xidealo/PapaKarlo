package com.example.data_api.mapper

import com.bunbeauty.domain.model.address.UserAddress
import com.example.domain_api.mapper.IStreetMapper
import com.example.domain_api.mapper.IUserAddressMapper
import com.example.domain_api.model.entity.user.UserAddressEntity
import com.example.domain_api.model.server.AddressServer
import com.example.domain_api.model.server.UserAddressPostServer
import javax.inject.Inject

class UserAddressMapper @Inject constructor(
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
            userUuid = userAddress.userUuid ?: "-"
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
            userUuid = userAddress.userUuid ?: "-"
        )
    }

    override fun toPostServerModel(userAddress: UserAddressEntity): UserAddressPostServer {
        return UserAddressPostServer(
            uuid = userAddress.uuid,
            streetUuid = userAddress.street.uuid,
            house = userAddress.house,
            flat = userAddress.flat,
            entrance = userAddress.entrance,
            floor = userAddress.floor,
            comment = userAddress.comment,
            userUuid = userAddress.userUuid ?: "-"
        )
    }

    override fun toPostServerModel(userAddress: UserAddress): UserAddressPostServer {
        return UserAddressPostServer(
            uuid = userAddress.uuid,
            streetUuid = userAddress.street.uuid,
            house = userAddress.house,
            flat = userAddress.flat,
            entrance = userAddress.entrance,
            floor = userAddress.floor,
            comment = userAddress.comment,
            userUuid = userAddress.userUuid ?: "-"
        )
    }
}