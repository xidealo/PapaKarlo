package com.example.data_api.mapper

import com.bunbeauty.domain.model.address.UserAddress
import com.example.domain_api.mapper.IStreetMapper
import com.example.domain_api.mapper.IUserAddressMapper
import com.example.domain_api.model.entity.user.UserAddressEntity
import com.example.domain_api.model.server.UserAddressPostServer
import com.example.domain_api.model.server.UserAddressServer
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

    override fun toModel(userAddress: UserAddressServer): UserAddress {
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

    override fun toEntityModel(userAddress: UserAddressServer): UserAddressEntity {
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

    override fun toServerModel(userAddress: UserAddress): UserAddressServer {
        return UserAddressServer(
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

    override fun toServerModel(userAddress: UserAddressEntity): UserAddressServer {
        return UserAddressServer(
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