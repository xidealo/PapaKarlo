package com.bunbeauty.papakarlo.feature.profile.order.order_list

import com.bunbeauty.papakarlo.common.DefaultDiffCallback

class OrderDiffCallback : DefaultDiffCallback<OrderItemModel>() {

    override fun getChangePayload(oldItem: OrderItemModel, newItem: OrderItemModel): Any? {
        return if (oldItem.statusName != newItem.statusName)
            true
        else
            null
    }


}