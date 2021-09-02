package com.bunbeauty.data.mapper

import com.bunbeauty.domain.mapper.IUserAddressMapper
import com.bunbeauty.domain.model.entity.address.UserAddressEntity
import com.bunbeauty.domain.model.entity.address.UserAddressWithStreet
import com.bunbeauty.domain.model.firebase.address.StreetFirebase
import com.bunbeauty.domain.model.firebase.address.UserAddressFirebase
import com.bunbeauty.domain.model.ui.address.UserAddress
import javax.inject.Inject

class UserAddressMapper @Inject constructor() : IUserAddressMapper {

    override fun toFirebaseModel(userAddress: UserAddress): UserAddressFirebase {
        return UserAddressFirebase(
            street = StreetFirebase(
                id = userAddress.streetUuid,
                name = userAddress.street
            ),
            house = userAddress.house,
            flat = userAddress.flat,
            entrance = userAddress.entrance,
            floor = userAddress.floor,
            comment = userAddress.comment,
        )
    }

    override fun toFirebaseModel(userAddress: UserAddressWithStreet): UserAddressFirebase {
        return UserAddressFirebase(
            street = StreetFirebase(
                id = userAddress.street.uuid,
                name = userAddress.street.name
            ),
            house = userAddress.userAddress.house,
            flat = userAddress.userAddress.flat,
            entrance = userAddress.userAddress.entrance,
            floor = userAddress.userAddress.floor,
            comment = userAddress.userAddress.comment,
        )
    }

    override fun toEntityModel(userAddress: UserAddress): UserAddressEntity {
        return UserAddressEntity(
            uuid = userAddress.uuid,
            house = userAddress.house,
            flat = userAddress.flat,
            entrance = userAddress.entrance,
            floor = userAddress.floor,
            comment = userAddress.comment,
            streetUuid = userAddress.streetUuid,
            userUuid = userAddress.userUuid,
        )
    }

    override fun toEntityModel(
        userAddress: UserAddressFirebase,
        userAddressUuid: String,
        userUuid: String
    ): UserAddressEntity {
        return UserAddressEntity(
            uuid = userAddressUuid,
            house = userAddress.house,
            flat = userAddress.flat,
            entrance = userAddress.entrance,
            floor = userAddress.floor,
            comment = userAddress.comment,
            streetUuid = userAddress.street.id,
            userUuid = userUuid,
        )
    }

    override fun toUIModel(userAddressWithStreet: UserAddressWithStreet): UserAddress {
        return UserAddress(
            uuid = userAddressWithStreet.userAddress.uuid,
            street = userAddressWithStreet.street.name,
            house = userAddressWithStreet.userAddress.house,
            flat = userAddressWithStreet.userAddress.flat,
            entrance = userAddressWithStreet.userAddress.entrance,
            floor = userAddressWithStreet.userAddress.floor,
            comment = userAddressWithStreet.userAddress.comment,
            streetUuid = userAddressWithStreet.userAddress.streetUuid,
            userUuid = userAddressWithStreet.userAddress.userUuid,
        )
    }
}