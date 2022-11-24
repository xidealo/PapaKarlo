package com.bunbeauty.papakarlo.feature.edit_text.model

import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class EditTextSettings(
    @StringRes val titleStringId: Int,
    val infoText: String?,
    @StringRes val labelStringId: Int,
    val type: EditTextType,
    val inputText: String?,
    @StringRes val buttonStringId: Int,
    val requestKey: String,
    val resultKey: String,
) : Parcelable
