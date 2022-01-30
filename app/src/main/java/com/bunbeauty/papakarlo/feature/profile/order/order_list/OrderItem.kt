package com.bunbeauty.papakarlo.feature.profile.order.order_list

import com.bunbeauty.papakarlo.common.BaseItem

data class OrderItem(
    override var uuid: String,
    val orderStatus: String,
    val orderColorResource: Int,
    val code: String,
    val dateTime: String
) : BaseItem()