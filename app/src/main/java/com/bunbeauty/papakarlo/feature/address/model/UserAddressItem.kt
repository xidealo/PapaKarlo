package com.bunbeauty.papakarlo.feature.address.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class UserAddressItem(
    val uuid: String,
    val address: String,
    val isSelected: Boolean,
) : Parcelable
