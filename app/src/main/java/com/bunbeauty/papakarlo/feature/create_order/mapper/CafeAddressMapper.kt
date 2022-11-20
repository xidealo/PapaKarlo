package com.bunbeauty.papakarlo.feature.create_order.mapper

import com.bunbeauty.papakarlo.feature.create_order.screen.cafe_address_list.CafeAddressItem
import com.bunbeauty.shared.domain.model.cafe.Cafe

object CafeAddressMapper {

    fun toCafeAddressItem(cafe: Cafe) = CafeAddressItem(
        uuid = cafe.uuid,
        address = cafe.address
    )
}