package com.bunbeauty.domain.model.entity.address

import androidx.room.Embedded
import androidx.room.Relation

data class UserAddressWithStreet(
    @Embedded
    val userAddress: UserAddressEntity,

    @Relation(parentColumn = "streetUuid", entityColumn = "uuid")
    val street: StreetEntity,

//    @Embedded
//    val street: StreetEntity,
//
//    @Relation(parentColumn = "uuid", entityColumn = "streetUuid")
//    val userAddress: UserAddressEntity,
)
