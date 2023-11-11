package com.bunbeauty.papakarlo.feature.createorder.screen.createorder.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class SelectableCafeAddressUI(
    val uuid: String,
    val address: String,
    val isSelected: Boolean,
): Parcelable
