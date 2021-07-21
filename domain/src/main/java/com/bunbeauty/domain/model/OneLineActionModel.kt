package com.bunbeauty.domain.model

import android.os.Parcelable
import com.bunbeauty.domain.enums.OneLineActionType
import kotlinx.parcelize.Parcelize

@Parcelize
data class OneLineActionModel(
    val title: String,
    val infoText: String?,
    val hint: String,
    val type: OneLineActionType,
    val inputText: String,
    val buttonText: String,
    val requestKey: String,
    val resultKey: String,
) : Parcelable