package com.bunbeauty.shared.presentation.cafe_address_list

import com.bunbeauty.shared.domain.model.cafe.Cafe

object CafeAddressMapper {

    fun toCafeAddressItem(cafe: Cafe) = CafeAddressItem(
        uuid = cafe.uuid,
        address = cafe.address
    )
}
