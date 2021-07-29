package com.bunbeauty.data.mapper.user_address

import com.bunbeauty.domain.model.firebase.address.UserAddressFirebase
import com.bunbeauty.domain.model.ui.address.UserAddress

interface IUserAddressMapper {

    fun toFirebaseModel(userAddress: UserAddress?): UserAddressFirebase?
}