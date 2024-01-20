package com.bunbeauty.papakarlo.feature.address.screen.createaddress

import com.bunbeauty.papakarlo.R
import com.bunbeauty.shared.presentation.create_address.CreateAddress
import kotlinx.collections.immutable.toPersistentList

val mapCreateAddressState: CreateAddress.DataState.() -> CreateAddressViewState = {
    CreateAddressViewState(
        street = street,
        streetErrorStringId = if (hasStreetError) {
            R.string.error_create_address_street
        } else {
            null
        },
        streetSuggestionList = streetSuggestionList.toPersistentList(),
        isSuggestionLoading = isSuggestionLoading,
        house = house,
        houseErrorStringId = if (hasHouseError) {
            R.string.error_create_address_house
        } else {
            null
        },
        flat = flat,
        entrance = entrance,
        floor = floor,
        comment = comment,
        isCreateLoading = isCreateLoading
    )
}
