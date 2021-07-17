package com.bunbeauty.domain.model.adapter

import android.os.Parcelable
import com.bunbeauty.domain.model.local.BaseModel
import com.bunbeauty.domain.model.local.cafe.Coordinate
import kotlinx.parcelize.Parcelize

@Parcelize
data class CafeAdapterModel(
    override var uuid: String,
    val address: String,
    val workingHours: String,
    val workingTimeMessage: String,
    val workingTimeMessageColor: Int,
    val phone: String,
    val coordinate: Coordinate
) : Parcelable, BaseModel
