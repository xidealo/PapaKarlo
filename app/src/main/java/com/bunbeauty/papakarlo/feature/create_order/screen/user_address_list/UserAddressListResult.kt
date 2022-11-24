package com.bunbeauty.papakarlo.feature.create_order.screen.user_address_list

sealed interface UserAddressListResult {
    object AddNewAddress : UserAddressListResult
    class AddressSelected(val addressItem: UserAddressItem) : UserAddressListResult
}
