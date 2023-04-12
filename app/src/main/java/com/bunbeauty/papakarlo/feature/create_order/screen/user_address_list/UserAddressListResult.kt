package com.bunbeauty.papakarlo.feature.create_order.screen.user_address_list

import com.bunbeauty.papakarlo.feature.address.model.UserAddressItem

sealed interface UserAddressListResult {
    object AddNewAddress : UserAddressListResult
    class AddressSelected(val addressItem: UserAddressItem) : UserAddressListResult
}
