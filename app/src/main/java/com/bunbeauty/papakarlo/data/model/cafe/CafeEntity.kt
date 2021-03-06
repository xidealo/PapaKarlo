package com.bunbeauty.papakarlo.data.model.cafe

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Company information
 */
@Entity
data class CafeEntity(
    @PrimaryKey
    var id: String = "",
    val fromTime: String = "",
    val toTime: String = "",
    val phone: String = "",
    @Embedded
    val coordinate: Coordinate = Coordinate()
)