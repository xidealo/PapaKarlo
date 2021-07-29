package com.bunbeauty.data.mapper.user_address

import com.bunbeauty.domain.model.firebase.address.UserAddressFirebase
import com.bunbeauty.domain.model.ui.address.UserAddress
import javax.inject.Inject

class UserAddressMapper @Inject constructor() : IUserAddressMapper {

    override fun toFirebaseModel(userAddress: UserAddress?): UserAddressFirebase? {
        return userAddress?.let { address ->
            UserAddressFirebase(
                street = address.street?.name ?: "",
                house = address.house,
                flat = address.flat,
                entrance = address.entrance,
                floor = address.floor,
                comment = address.comment,
            )
        }
    }
}