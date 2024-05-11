package com.bunbeauty.shared.presentation.cafe_address_list

import com.bunbeauty.shared.domain.model.cafe.SelectableCafe

object SelectableCafeAddressItemMapper {

    fun toSelectableCafeAddressItem(selectableCafe: SelectableCafe) = SelectableCafeAddressItem(
        uuid = selectableCafe.cafe.uuid,
        address = selectableCafe.cafe.address,
        isSelected = selectableCafe.isSelected
    )
}
