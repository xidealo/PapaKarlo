package com.bunbeauty.papakarlo.feature.create_order.screen.create_order

import com.bunbeauty.shared.domain.model.address.UserAddress
import com.bunbeauty.shared.domain.model.cafe.Cafe
import com.bunbeauty.shared.domain.model.date_time.Time

data class OrderCreationData(
    val selectedUserAddress: UserAddress? = null,
    val selectedCafe: Cafe? = null,
    val userAddressList: List<UserAddress> = emptyList(),
    val cafeList: List<Cafe> = emptyList(),
    val selectedDeferredTime: Time? = null,
)
