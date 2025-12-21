package com.bunbeauty.shared.data.mapper.user_address

import com.bunbeauty.shared.data.network.model.AddressServer
import com.bunbeauty.shared.data.network.model.UserAddressPostServer
import com.bunbeauty.shared.data.network.model.UserAddressStreetPostServer
import com.bunbeauty.shared.db.UserAddressEntity
import com.bunbeauty.shared.domain.model.address.CreatedUserAddress
import com.bunbeauty.shared.domain.model.address.UserAddress

class UserAddressMapper {
    fun toUserAddress(userAddressEntity: UserAddressEntity): UserAddress =
        userAddressEntity.run {
            UserAddress(
                uuid = uuid,
                street = streetName,
                house = house,
                flat = flat,
                entrance = entrance,
                floor = floor,
                comment = comment,
                minOrderCost = minOrderCost,
                normalDeliveryCost = normalDeliveryCost,
                forLowDeliveryCost = forLowDeliveryCost,
                lowDeliveryCost = lowDeliveryCost,
                userUuid = userUuid,
                cafeUuid = cafeUuid,
            )
        }

    fun toUserAddress(addressServer: AddressServer): UserAddress =
        addressServer.run {
            UserAddress(
                uuid = uuid,
                street = street,
                house = house,
                flat = flat,
                entrance = entrance,
                floor = floor,
                comment = comment,
                minOrderCost = minOrderCost,
                normalDeliveryCost = normalDeliveryCost,
                forLowDeliveryCost = forLowDeliveryCost,
                lowDeliveryCost = lowDeliveryCost,
                userUuid = userUuid,
                cafeUuid = cafeUuid,
            )
        }

    fun toUserAddressEntity(addressServer: AddressServer): UserAddressEntity =
        addressServer.run {
            UserAddressEntity(
                uuid = uuid,
                streetName = street,
                cityUuid = cityUuid,
                house = house,
                flat = flat,
                entrance = entrance,
                floor = floor,
                comment = comment,
                minOrderCost = minOrderCost,
                normalDeliveryCost = normalDeliveryCost,
                forLowDeliveryCost = forLowDeliveryCost,
                lowDeliveryCost = lowDeliveryCost,
                userUuid = userUuid,
                cafeUuid = cafeUuid,
            )
        }

    fun toUserAddressPostServer(createdUserAddress: CreatedUserAddress): UserAddressPostServer =
        UserAddressPostServer(
            street =
                UserAddressStreetPostServer(
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
