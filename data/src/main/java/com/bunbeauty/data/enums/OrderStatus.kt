package com.bunbeauty.data.enums

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class OrderStatus : Parcelable {
    NOT_ACCEPTED,
    PREPARING,
    SENT_OUT,
    DONE
}