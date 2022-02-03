package com.bunbeauty.papakarlo.feature.profile.order.order_list

import com.bunbeauty.papakarlo.common.BaseItem

data class OrderItem(
    override val uuid: String,
    val statusName: String,
    val statusColorResource: Int,
    val code: String,
    val dateTime: String
) : BaseItem()