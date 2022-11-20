package com.bunbeauty.papakarlo.feature.create_order.mapper

import com.bunbeauty.papakarlo.feature.create_order.screen.user_address_list.UserAddressItem
import com.bunbeauty.papakarlo.util.string.IStringUtil
import com.bunbeauty.shared.domain.model.address.UserAddress

class UserAddressMapper(private val stringUtil: IStringUtil) {

    fun toUserAddressItem(userAddress: UserAddress) = UserAddressItem(
        uuid = userAddress.uuid,
        address = stringUtil.getUserAddressString(userAddress) ?: ""
    )
}