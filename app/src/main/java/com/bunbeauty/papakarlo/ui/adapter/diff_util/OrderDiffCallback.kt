package com.bunbeauty.papakarlo.ui.adapter.diff_util

import androidx.recyclerview.widget.DiffUtil
import com.bunbeauty.domain.model.adapter.CartProductAdapterModel
import com.bunbeauty.domain.model.adapter.MenuProductAdapterModel
import com.bunbeauty.domain.model.adapter.OrderAdapterModel
import com.bunbeauty.domain.model.local.BaseModel

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