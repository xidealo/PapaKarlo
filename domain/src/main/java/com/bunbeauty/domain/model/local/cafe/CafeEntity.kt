package com.bunbeauty.domain.model.local.cafe

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bunbeauty.domain.model.local.cafe.Coordinate
import kotlinx.parcelize.Parcelize

/**
 * Company information
 */
@Parcelize
@Entity
data class CafeEntity(
    @PrimaryKey
    var id: String = "",
    val fromTime: String = "",
    val toTime: String = "",
    val phone: String = "",
    @Embedded
    val coordinate: Coordinate = Coordinate(),
    val visible: Boolean = false
) : Parcelable