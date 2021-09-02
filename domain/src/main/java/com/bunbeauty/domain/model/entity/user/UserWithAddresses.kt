package com.bunbeauty.domain.model.entity.user

import androidx.room.Embedded
import androidx.room.Relation
import com.bunbeauty.domain.model.entity.address.UserAddressEntity

data class UserWithAddresses(
    @Embedded
    val user: UserEntity,

    @Relation(parentColumn = "uuid", entityColumn = "userUuid")
    val userAddressList: List<UserAddressEntity>
)