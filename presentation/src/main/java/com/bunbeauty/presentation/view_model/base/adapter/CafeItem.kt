package com.bunbeauty.presentation.view_model.base.adapter

import android.os.Parcelable
import com.bunbeauty.domain.model.ui.BaseItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class CafeItem(
    override var uuid: String,
    val address: String,
    val workingHours: String,
    val workingTimeMessage: String,
    val workingTimeMessageColor: Int
) : Parcelable, BaseItem()