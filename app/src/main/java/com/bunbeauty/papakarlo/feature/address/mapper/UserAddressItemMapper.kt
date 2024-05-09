package com.bunbeauty.papakarlo.feature.address.mapper

import com.bunbeauty.papakarlo.feature.address.model.UserAddressItem
import com.bunbeauty.papakarlo.util.string.IStringUtil
import com.bunbeauty.shared.domain.model.address.SelectableUserAddress

class UserAddressItemMapper(private val stringUtil: IStringUtil) {

    fun toItem(selectableUserAddress: SelectableUserAddress): UserAddressItem {
        return UserAddressItem(
            uuid = selectableUserAddress.address.uuid,
            address = stringUtil.getUserAddressString(selectableUserAddress) ?: "",
            isSelected = selectableUserAddress.isSelected
        )
    }
}
