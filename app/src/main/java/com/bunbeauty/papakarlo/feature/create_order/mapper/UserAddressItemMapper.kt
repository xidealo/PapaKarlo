package com.bunbeauty.papakarlo.feature.create_order.mapper

import com.bunbeauty.papakarlo.feature.address.model.UserAddressItem
import com.bunbeauty.papakarlo.util.string.IStringUtil
import com.bunbeauty.shared.domain.model.address.SelectableUserAddress
import com.bunbeauty.shared.domain.model.address.UserAddress
import com.bunbeauty.shared.presentation.create_order.model.SelectableUserAddressUi

class UserAddressItemMapper(private val stringUtil: IStringUtil) {

    fun toItem(userAddress: UserAddress): UserAddressItem {
        return UserAddressItem(
            uuid = userAddress.uuid,
            address = stringUtil.getUserAddressString(userAddress) ?: "",
            isSelected = false
        )
    }

    fun toItem(userAddress: SelectableUserAddress): UserAddressItem {
        return UserAddressItem(
            uuid = userAddress.uuid,
            address = stringUtil.getUserAddressString(userAddress) ?: "",
            isSelected = userAddress.isSelected
        )
    }

    fun toItem(userAddress: SelectableUserAddressUi): UserAddressItem {
        return UserAddressItem(
            uuid = userAddress.uuid,
            address = stringUtil.getUserAddressString(userAddress) ?: "",
            isSelected = userAddress.isSelected
        )
    }
}
