package com.example.domain_api.model.entity.user

import androidx.room.Embedded
import androidx.room.Relation
import com.example.domain_api.model.entity.AddressEntity

data class UserWithAddresses(
    @Embedded
    val user: UserEntity,

    @Relation(parentColumn = "uuid", entityColumn = "userUuid")
    val userAddressList: List<AddressEntity>
)