package com.bunbeauty.papakarlo.feature.createorder.screen.useraddresslist

import com.bunbeauty.papakarlo.feature.address.model.UserAddressItem

sealed interface UserAddressListResult {
    object AddNewAddress : UserAddressListResult
    class AddressSelected(val addressItem: UserAddressItem) : UserAddressListResult
}
