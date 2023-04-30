package com.bunbeauty.shared.presentation.cafe_address_list

import com.bunbeauty.shared.domain.model.cafe.SelectableCafe

object SelectableCafeAddressItemMapper {

    fun toSelectableCafeAddressItem(cafe: SelectableCafe) = SelectableCafeAddressItem(
        uuid = cafe.uuid,
        address = cafe.address,
        isSelected = cafe.isSelected
    )
}
