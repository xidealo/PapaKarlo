package com.bunbeauty.shared.presentation.create_order

import com.bunbeauty.shared.domain.model.address.SelectableUserAddress
import com.bunbeauty.shared.domain.model.address.UserAddress
import com.bunbeauty.shared.domain.model.cafe.Cafe
import com.bunbeauty.shared.domain.model.cafe.SelectableCafe
import com.bunbeauty.shared.domain.model.date_time.Time

data class OrderCreationData(
    val selectedUserAddress: SelectableUserAddress? = null,
    val selectedCafe: SelectableCafe? = null,
    val userAddressList: List<SelectableUserAddress> = emptyList(),
    val cafeList: List<SelectableCafe> = emptyList(),
    val selectedDeferredTime: Time? = null,
)
