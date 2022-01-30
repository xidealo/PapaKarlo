package com.bunbeauty.data.database.entity.user

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bunbeauty.data.database.entity.StreetEntity

@Entity
data class UserAddressEntity(
    @PrimaryKey
    val uuid: String,
    @Embedded(prefix = "street_")
    val street: StreetEntity,
    val house: String,
    val flat: String?,
    val entrance: String?,
    val floor: String?,
    val comment: String?,
    val userUuid: String,
)