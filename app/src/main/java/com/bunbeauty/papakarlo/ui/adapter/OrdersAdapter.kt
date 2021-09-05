package com.bunbeauty.papakarlo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.bunbeauty.presentation.item.OrderItem
import com.bunbeauty.papakarlo.databinding.ElementOrderBinding
import com.bunbeauty.papakarlo.ui.adapter.base.BaseViewHolder
import com.bunbeauty.papakarlo.ui.adapter.diff_util.OrderDiffCallback
import javax.inject.Inject

class OrdersAdapter @Inject constructor() :
    ListAdapter<OrderItem, BaseViewHolder<ViewBinding, OrderItem>>(
        OrderDiffCallback()
    ) {

    var onItemClickListener: ((OrderItem) -> Unit)? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): OrderViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ElementOrderBinding.inflate(inflater, viewGroup, false)

        return OrderViewHolder(binding.root)
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<ViewBinding, OrderItem>,
        position: Int
    ) {
        holder.onBind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<ViewBinding, OrderItem>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNullOrEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            holder.onBind(getItem(position), payloads)
        }
    }

    inner class OrderViewHolder(view: View) :
        BaseViewHolder<ElementOrderBinding, OrderItem>(DataBindingUtil.bind(view)!!) {

        override fun onBind(item: OrderItem) {
            super.onBind(item)
            with(binding) {
                elementOrderTvCode.text = item.code
                elementOrderTvDeferred.text = item.deferredTime
                elementOrderTvTime.text = item.time
                elementOrderChipStatus.text = item.orderStatus
                elementOrderChipStatus.setChipBackgroundColorResource(item.orderColor)

                elementOrderMvcMain.setOnClickListener {
                    onItemClickListener?.invoke(item)
                }
            }
        }

        override fun onBind(item: OrderItem, payloads: List<Any>) {
            super.onBind(item, payloads)
            if (payloads.last() as Boolean) {
                with(binding) {
                    elementOrderChipStatus.text = item.orderStatus
                    elementOrderChipStatus.setChipBackgroundColorResource(item.orderColor)
                    elementOrderMvcMain.setOnClickListener {
                        onItemClickListener?.invoke(item)
                    }
                }
            }
        }
    }
}