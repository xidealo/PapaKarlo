package com.bunbeauty.shared.data.mapper.user_address

import com.bunbeauty.shared.data.mapper.street.IStreetMapper
import com.bunbeauty.shared.data.network.model.AddressServer
import com.bunbeauty.shared.data.network.model.UserAddressPostServer
import com.bunbeauty.shared.db.UserAddressEntity
import com.bunbeauty.shared.domain.model.address.CreatedUserAddress
import com.bunbeauty.shared.domain.model.address.UserAddress

class UserAddressMapper(
    private val streetMapper: IStreetMapper,
) {

    fun toUserAddress(userAddressEntity: UserAddressEntity): UserAddress {
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

    fun toUserAddress(addressServer: AddressServer): UserAddress {
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

    fun toUserAddressEntity(addressServer: AddressServer): UserAddressEntity {
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

    fun toUserAddressPostServer(createdUserAddress: CreatedUserAddress): UserAddressPostServer {
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