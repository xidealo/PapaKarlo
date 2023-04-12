package com.bunbeauty.papakarlo.feature.create_order.mapper

import com.bunbeauty.papakarlo.feature.address.model.UserAddressItem
import com.bunbeauty.papakarlo.util.string.IStringUtil
import com.bunbeauty.shared.domain.model.address.UserAddress
import com.bunbeauty.shared.presentation.create_order.model.UserAddressUi

class UserAddressItemMapper(private val stringUtil: IStringUtil) {

    fun toItem(userAddress: UserAddressUi): UserAddressItem {
        return UserAddressItem(
            uuid = userAddress.uuid,
            address = stringUtil.getUserAddressString(userAddress) ?: ""
        )
    }

    fun toItem(userAddress: UserAddress): UserAddressItem {
        return UserAddressItem(
            uuid = userAddress.uuid,
            address = stringUtil.getUserAddressString(userAddress) ?: ""
        )
    }
}
