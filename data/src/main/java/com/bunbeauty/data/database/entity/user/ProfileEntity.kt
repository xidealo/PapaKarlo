package com.bunbeauty.data.database.entity.user

import androidx.room.Embedded
import androidx.room.Relation
import com.bunbeauty.data.database.entity.user.order.OrderEntity
import com.bunbeauty.data.database.entity.user.order.OrderWithProducts

data class ProfileEntity(

    @Embedded
    val user: UserEntity,

    @Relation(parentColumn = "uuid", entityColumn = "userUuid")
    val userAddressList: List<UserAddressEntity>,

    @Relation(parentColumn = "uuid", entityColumn = "userUuid", entity = OrderEntity::class)
    val orderList: List<OrderWithProducts>
)