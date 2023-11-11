package com.bunbeauty.papakarlo.feature.createorder.screen.useraddresslist.model

import com.bunbeauty.papakarlo.feature.address.model.UserAddressItem

sealed interface UserAddressListResult {
    data object AddNewAddress : UserAddressListResult
    class AddressSelected(val userAddressItem: UserAddressItem) : UserAddressListResult
}