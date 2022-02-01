package com.bunbeauty.papakarlo.feature.profile.order.order_list

import com.bunbeauty.papakarlo.common.DefaultDiffCallback

class OrderDiffCallback : DefaultDiffCallback<OrderItem>() {

    override fun getChangePayload(oldItem: OrderItem, newItem: OrderItem): Any? {
        return if (oldItem.statusName != newItem.statusName)
            true
        else
            null
    }


}