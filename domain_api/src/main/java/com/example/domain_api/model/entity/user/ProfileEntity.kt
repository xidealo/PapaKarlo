package com.example.domain_api.model.entity.user

import androidx.room.Embedded
import androidx.room.Relation
import com.example.domain_api.model.entity.user.order.OrderEntity
import com.example.domain_api.model.entity.user.order.OrderWithProducts

data class ProfileEntity(

    @Embedded
    val user: UserEntity,

    @Relation(parentColumn = "uuid", entityColumn = "userUuid")
    val userAddressList: List<UserAddressEntity>,

    @Relation(parentColumn = "uuid", entityColumn = "userUuid", entity = OrderEntity::class)
    val orderList: List<OrderWithProducts>
)