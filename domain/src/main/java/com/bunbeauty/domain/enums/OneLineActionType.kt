package com.bunbeauty.domain.enums

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class OneLineActionType : Parcelable {
    EMAIL,
    COMMENT,
    BONUSES
}