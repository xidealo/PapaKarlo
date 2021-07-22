package com.bunbeauty.presentation.view_model.base.adapter

import android.os.Parcelable
import com.bunbeauty.domain.model.ui.BaseModel
import com.bunbeauty.domain.model.ui.cafe.Coordinate
import kotlinx.parcelize.Parcelize

@Parcelize
data class CafeItem(
    override var uuid: String,
    val address: String,
    val workingHours: String,
    val workingTimeMessage: String,
    val workingTimeMessageColor: Int,
    val phone: String,
    val coordinate: Coordinate
) : Parcelable, BaseModel
