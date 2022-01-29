package com.bunbeauty.papakarlo.feature.edit_text

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EditTextSettings(
    val title: String,
    val infoText: String?,
    val hint: String,
    val type: EditTextType,
    val inputText: String?,
    val buttonText: String,
    val requestKey: String,
    val resultKey: String,
) : Parcelable