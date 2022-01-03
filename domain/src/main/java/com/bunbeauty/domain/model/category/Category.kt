package com.bunbeauty.domain.model.category

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val uuid: String,
    val name: String
) : Parcelable
