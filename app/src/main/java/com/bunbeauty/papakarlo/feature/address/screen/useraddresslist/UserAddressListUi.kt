package com.bunbeauty.papakarlo.feature.address.screen.useraddresslist

import com.bunbeauty.papakarlo.feature.address.model.UserAddressItem
import com.bunbeauty.shared.presentation.user_address_list.UserAddressListState

data class UserAddressListUi(
    val userAddressItems: List<UserAddressItem> = emptyList(),
    val state: UserAddressListState.State,
    val eventList: List<UserAddressListState.Event> = emptyList()
)
