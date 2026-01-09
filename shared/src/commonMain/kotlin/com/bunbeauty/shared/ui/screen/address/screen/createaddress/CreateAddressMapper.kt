package com.bunbeauty.shared.ui.screen.address.screen.createaddress

import com.bunbeauty.shared.presentation.create_address.CreateAddress
import kotlinx.collections.immutable.toPersistentList
import papakarlo.designsystem.generated.resources.Res
import papakarlo.designsystem.generated.resources.error_create_address_house
import papakarlo.designsystem.generated.resources.error_create_address_street

val mapCreateAddressState: CreateAddress.DataState.() -> CreateAddressViewState = {
    CreateAddressViewState(
        street = street,
        streetErrorStringId =
            if (hasStreetError) {
                Res.string.error_create_address_street
            } else {
                null
            },
        streetSuggestionList = streetSuggestionList.toPersistentList(),
        isSuggestionLoading = isSuggestionLoading,
        house = house,
        houseErrorStringId =
            if (hasHouseError) {
                Res.string.error_create_address_house
            } else {
                null
            },
        flat = flat,
        entrance = entrance,
        floor = floor,
        comment = comment,
        isCreateLoading = isCreateLoading,
    )
}
