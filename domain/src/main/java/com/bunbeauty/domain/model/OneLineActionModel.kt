package com.bunbeauty.domain.model

import android.os.Parcelable
import com.bunbeauty.domain.enums.OneLineActionType
import kotlinx.parcelize.Parcelize

@Parcelize
data class OneLineActionModel(
    val title: String,
    val type: OneLineActionType,
    val placeholder: String,
    val buttonText: String,
    val data: String
) : Parcelable