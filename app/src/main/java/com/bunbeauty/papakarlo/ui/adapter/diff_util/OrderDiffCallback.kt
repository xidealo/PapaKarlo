package com.bunbeauty.papakarlo.ui.adapter.diff_util

import androidx.recyclerview.widget.DiffUtil
import com.bunbeauty.presentation.view_model.base.adapter.OrderItem

class OrderDiffCallback : DiffUtil.ItemCallback<OrderItem>() {

    override fun areItemsTheSame(
        oldItem: OrderItem,
        newItem: OrderItem
    ): Boolean {
        return oldItem.uuid == newItem.uuid
    }

    override fun areContentsTheSame(
        oldItem: OrderItem,
        newItem: OrderItem
    ): Boolean {
        return oldItem == newItem
    }


    override fun getChangePayload(
        oldItem: OrderItem,
        newItem: OrderItem
    ): Any? {
        return if (oldItem.orderStatus != newItem.orderStatus)
            true
        else
            null
    }


}