package com.bunbeauty.shared.data.mapper.user_address

import com.bunbeauty.shared.data.network.model.AddressServer
import com.bunbeauty.shared.data.network.model.UserAddressPostServer
import com.bunbeauty.shared.data.network.model.UserAddressStreetPostServer
import com.bunbeauty.shared.db.UserAddressEntity
import com.bunbeauty.shared.domain.model.address.CreatedUserAddress
import com.bunbeauty.shared.domain.model.address.UserAddress

class UserAddressMapper {

    fun toUserAddress(userAddressEntity: UserAddressEntity): UserAddress {
        return UserAddress(
            uuid = userAddressEntity.uuid,
            street = userAddressEntity.streetName,
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
            street = addressServer.street,
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
            streetName = addressServer.street,
            cityUuid = addressServer.cityUuid,
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
            street = UserAddressStreetPostServer(
                fiasId = createdUserAddress.street.fiasId,
                name = createdUserAddress.street.street,
            ),
            house = createdUserAddress.house,
            flat = createdUserAddress.flat,
            entrance = createdUserAddress.entrance,
            floor = createdUserAddress.floor,
            comment = createdUserAddress.comment,
            isVisible = createdUserAddress.isVisible,
            cityUuid = createdUserAddress.cityUuid,
        )
    }
}