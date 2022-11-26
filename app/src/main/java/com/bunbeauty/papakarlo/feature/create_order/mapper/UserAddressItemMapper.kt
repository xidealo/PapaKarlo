package com.bunbeauty.papakarlo.feature.create_order.mapper

import com.bunbeauty.papakarlo.feature.create_order.model.UserAddressUi
import com.bunbeauty.papakarlo.feature.create_order.screen.user_address_list.UserAddressItem
import com.bunbeauty.papakarlo.util.string.IStringUtil

class UserAddressItemMapper(private val stringUtil: IStringUtil) {

    fun toItem(userAddress: UserAddressUi): UserAddressItem {
        return UserAddressItem(
            uuid = userAddress.uuid,
            address = stringUtil.getUserAddressString(userAddress) ?: ""
        )
    }
}
