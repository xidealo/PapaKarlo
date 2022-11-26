package com.bunbeauty.papakarlo.feature.create_order.mapper

import com.bunbeauty.papakarlo.feature.create_order.model.UserAddressUi
import com.bunbeauty.shared.domain.model.address.UserAddress

class UserAddressMapper {

    fun toUiModel(userAddress: UserAddress?): UserAddressUi? {
        return userAddress?.let {
            UserAddressUi(
                uuid = userAddress.uuid,
                street = userAddress.street.name,
                house = userAddress.house,
                flat = userAddress.flat,
                entrance = userAddress.entrance,
                floor = userAddress.floor,
                comment = userAddress.comment,
            )
        }
    }
}
