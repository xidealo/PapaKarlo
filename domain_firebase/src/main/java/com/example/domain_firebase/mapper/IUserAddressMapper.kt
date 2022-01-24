package com.example.domain_firebase.mapper

import com.bunbeauty.domain.model.address.UserAddress
import com.example.domain_firebase.model.entity.address.UserAddressEntity
import com.example.domain_firebase.model.entity.address.UserAddressWithStreet
import com.example.domain_firebase.model.firebase.address.UserAddressFirebase

interface IUserAddressMapper {

    fun toFirebaseModel(userAddress: UserAddress): UserAddressFirebase
    fun toFirebaseModel(userAddress: UserAddressWithStreet): UserAddressFirebase
    fun toEntityModel(userAddress: UserAddress): UserAddressEntity
    fun toEntityModel(
        userAddress: UserAddressFirebase,
        userAddressUuid: String,
        userUuid: String
    ): UserAddressEntity

    fun toUIModel(userAddressWithStreet: UserAddressWithStreet): UserAddress
}