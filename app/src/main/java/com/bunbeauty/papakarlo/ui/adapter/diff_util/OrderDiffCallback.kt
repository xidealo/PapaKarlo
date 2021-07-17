package com.bunbeauty.papakarlo.ui.adapter.diff_util

import androidx.recyclerview.widget.DiffUtil
import com.bunbeauty.presentation.view_model.base.adapter.OrderAdapterModel

class OrderDiffCallback : DiffUtil.ItemCallback<OrderAdapterModel>() {

    override fun areItemsTheSame(
        oldItem: OrderAdapterModel,
        newItem: OrderAdapterModel
    ): Boolean {
        return oldItem.uuid == newItem.uuid
    }

    override fun areContentsTheSame(
        oldItem: OrderAdapterModel,
        newItem: OrderAdapterModel
    ): Boolean {
        return oldItem == newItem
    }


    override fun getChangePayload(
        oldItem: OrderAdapterModel,
        newItem: OrderAdapterModel
    ): Any? {
        return if (oldItem.orderStatus != newItem.orderStatus)
            true
        else
            null
    }


}