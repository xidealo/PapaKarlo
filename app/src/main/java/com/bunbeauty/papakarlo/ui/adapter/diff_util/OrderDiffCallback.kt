package com.bunbeauty.papakarlo.ui.adapter.diff_util

import com.bunbeauty.presentation.view_model.base.adapter.OrderItem

class OrderDiffCallback : DefaultDiffCallback<OrderItem>() {

    override fun getChangePayload(oldItem: OrderItem, newItem: OrderItem): Any? {
        return if (oldItem.orderStatus != newItem.orderStatus)
            true
        else
            null
    }


}