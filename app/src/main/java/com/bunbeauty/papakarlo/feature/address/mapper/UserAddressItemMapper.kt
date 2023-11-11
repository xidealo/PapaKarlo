package com.bunbeauty.papakarlo.feature.address.mapper

import com.bunbeauty.papakarlo.feature.address.model.UserAddressItem
import com.bunbeauty.papakarlo.util.string.IStringUtil
import com.bunbeauty.shared.domain.model.address.SelectableUserAddress

class UserAddressItemMapper(private val stringUtil: IStringUtil) {

    fun toItem(userAddress: SelectableUserAddress): UserAddressItem {
        return UserAddressItem(
            uuid = userAddress.uuid,
            address = stringUtil.getUserAddressString(userAddress) ?: "",
            isSelected = userAddress.isSelected
        )
    }

}
