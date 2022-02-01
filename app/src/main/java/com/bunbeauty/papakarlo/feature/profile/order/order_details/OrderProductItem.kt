package com.bunbeauty.papakarlo.feature.profile.order.order_details

import com.bunbeauty.papakarlo.common.BaseItem

class OrderProductItem(
    override val uuid: String,
    val name: String,
    val newCost: String,
    val oldCost: String?,
    val photoLink: String,
    val count: String
) : BaseItem()