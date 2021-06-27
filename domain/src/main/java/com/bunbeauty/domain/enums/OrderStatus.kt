package com.bunbeauty.domain.enums

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class OrderStatus : Parcelable {
    NOT_ACCEPTED,
    ACCEPTED,
    PREPARING,
    SENT_OUT,
    DELIVERED,
    DONE,
    CANCELED
}

