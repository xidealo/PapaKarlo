package com.example.domain_api.model.entity.user

import androidx.room.Embedded
import androidx.room.Relation

data class ProfileEntity(

    @Embedded
    val user: UserEntity,

    @Relation(parentColumn = "uuid", entityColumn = "userUuid")
    val userAddressList: List<UserAddressEntity>,

    @Relation(parentColumn = "uuid", entityColumn = "userUuid")
    val orderList: List<OrderEntity>
)