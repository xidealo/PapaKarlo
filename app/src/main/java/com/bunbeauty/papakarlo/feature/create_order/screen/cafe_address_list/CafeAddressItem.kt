package com.bunbeauty.papakarlo.feature.create_order.screen.cafe_address_list

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class CafeAddressItem(
    val uuid: String,
    val address: String,
): Parcelable