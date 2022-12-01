package com.bunbeauty.papakarlo.feature.create_order.mapper

import com.bunbeauty.papakarlo.feature.create_order.screen.user_address_list.UserAddressItem
import com.bunbeauty.papakarlo.util.string.IStringUtil
import com.bunbeauty.shared.ui.create_order.model.UserAddressUi

class UserAddressItemMapper(private val stringUtil: IStringUtil) {

    fun toItem(userAddress: UserAddressUi): UserAddressItem {
        return UserAddressItem(
            uuid = userAddress.uuid,
            address = stringUtil.getUserAddressString(userAddress) ?: ""
        )
    }
}
