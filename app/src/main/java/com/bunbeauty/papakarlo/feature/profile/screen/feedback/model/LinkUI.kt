package com.bunbeauty.papakarlo.feature.profile.screen.feedback.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class LinkUI(
    val uuid: String,
    @StringRes val labelId: Int?,
    @DrawableRes val iconId: Int,
    val value: String,
) : Parcelable
