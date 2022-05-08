package com.bunbeauty.shared.data.mapper.user_address

import com.bunbeauty.shared.data.mapper.street.IStreetMapper
import com.bunbeauty.shared.data.network.model.AddressServer
import com.bunbeauty.shared.data.network.model.UserAddressPostServer
import com.bunbeauty.shared.domain.model.address.CreatedUserAddress
import com.bunbeauty.shared.domain.model.address.UserAddress
import database.UserAddressEntity

class UserAddressMapper(
    private val streetMapper: IStreetMapper
) : IUserAddressMapper {

    override fun toUserAddress(userAddressEntity: UserAddressEntity): UserAddress {
        return UserAddress(
            uuid = userAddressEntity.uuid,
            street = streetMapper.toStreet(userAddressEntity),
            house = userAddressEntity.house,
            flat = userAddressEntity.flat,
            entrance = userAddressEntity.entrance,
            floor = userAddressEntity.floor,
            comment = userAddressEntity.comment,
            userUuid = userAddressEntity.userUuid,
        )
    }

    override fun toUserAddress(addressServer: AddressServer): UserAddress {
        return UserAddress(
            uuid = addressServer.uuid,
            street = streetMapper.toStreet(addressServer.street),
            house = addressServer.house,
            flat = addressServer.flat,
            entrance = addressServer.entrance,
            floor = addressServer.floor,
            comment = addressServer.comment,
            userUuid = addressServer.userUuid,
        )
    }

    override fun toUserAddressEntity(addressServer: AddressServer): UserAddressEntity {
        return UserAddressEntity(
            uuid = addressServer.uuid,
            streetUuid = addressServer.street.uuid,
            streetName = addressServer.street.name,
            cityUuid = addressServer.street.cityUuid,
            house = addressServer.house,
            flat = addressServer.flat,
            entrance = addressServer.entrance,
            floor = addressServer.floor,
            comment = addressServer.comment,
            userUuid = addressServer.userUuid,
        )
    }

    override fun toUserAddressEntity(userAddress: UserAddress): UserAddressEntity {
        return UserAddressEntity(
            uuid = userAddress.uuid,
            streetUuid = userAddress.street.uuid,
            streetName = userAddress.street.name,
            cityUuid = userAddress.street.cityUuid,
            house = userAddress.house,
            flat = userAddress.flat,
            entrance = userAddress.entrance,
            floor = userAddress.floor,
            comment = userAddress.comment,
            userUuid = userAddress.userUuid,
        )
    }

    override fun toAddressServer(userAddress: UserAddress): AddressServer {
        return AddressServer(
            uuid = userAddress.uuid,
            street = streetMapper.toStreetServer(userAddress.street),
            house = userAddress.house,
            flat = userAddress.flat,
            entrance = userAddress.entrance,
            floor = userAddress.floor,
            comment = userAddress.comment,
            userUuid = userAddress.userUuid
        )
    }

    override fun toAddressServer(userAddressEntity: UserAddressEntity): AddressServer {
        return AddressServer(
            uuid = userAddressEntity.uuid,
            street = streetMapper.toStreetServer(userAddressEntity),
            house = userAddressEntity.house,
            flat = userAddressEntity.flat,
            entrance = userAddressEntity.entrance,
            floor = userAddressEntity.floor,
            comment = userAddressEntity.comment,
            userUuid = userAddressEntity.userUuid
        )
    }

    override fun toUserAddressPostServer(userAddressEntity: UserAddressEntity): UserAddressPostServer {
        return UserAddressPostServer(
            streetUuid = userAddressEntity.streetUuid,
            house = userAddressEntity.house,
            flat = userAddressEntity.flat,
            entrance = userAddressEntity.entrance,
            floor = userAddressEntity.floor,
            comment = userAddressEntity.comment,
            isVisible = true
        )
    }

    override fun toUserAddressPostServer(userAddress: UserAddress): UserAddressPostServer {
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

    override fun toUserAddressPostServer(createdUserAddress: CreatedUserAddress): UserAddressPostServer {
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