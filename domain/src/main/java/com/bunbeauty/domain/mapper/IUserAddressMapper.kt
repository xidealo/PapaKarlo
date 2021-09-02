package com.bunbeauty.domain.mapper

import com.bunbeauty.domain.model.entity.address.UserAddressEntity
import com.bunbeauty.domain.model.entity.address.UserAddressWithStreet
import com.bunbeauty.domain.model.firebase.address.UserAddressFirebase
import com.bunbeauty.domain.model.ui.address.UserAddress

interface IUserAddressMapper {

    fun toFirebaseModel(userAddress: UserAddress): UserAddressFirebase
    fun toFirebaseModel(userAddress: UserAddressWithStreet): UserAddressFirebase
    fun toEntityModel(userAddress: UserAddress): UserAddressEntity
    fun toEntityModel(userAddress: UserAddressFirebase, userAddressUuid: String, userUuid: String): UserAddressEntity
    fun toUIModel(userAddressWithStreet: UserAddressWithStreet): UserAddress
}