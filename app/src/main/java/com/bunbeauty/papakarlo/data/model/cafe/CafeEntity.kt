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
    var addressId: Long? = null,
    val fromTime: String = "",
    val toTime: String = "",
    val phone: String = "",
    @Embedded
    val coordinate: Coordinate = Coordinate()
) {

    fun getWorkingHoursString(): String {
        return "$fromTime - $toTime"
    }

    companion object {

        const val START_TIME = "start_time"
        const val END_TIME = "end_time"
        const val PHONE = "phone"
        const val NAME = "name"
        const val LATITUDE = "latitude"
        const val LONGITUDE = "longitude"
    }
}