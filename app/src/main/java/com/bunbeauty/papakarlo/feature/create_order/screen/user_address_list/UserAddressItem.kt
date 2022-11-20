package com.bunbeauty.papakarlo.feature.create_order.screen.user_address_list

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class UserAddressItem(
    val uuid: String,
    val address: String,
): Parcelable