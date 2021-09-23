package com.bunbeauty.papakarlo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bunbeauty.papakarlo.databinding.ElementOrderBinding
import com.bunbeauty.papakarlo.ui.adapter.base.BaseListAdapter
import com.bunbeauty.papakarlo.ui.adapter.base.BaseViewHolder
import com.bunbeauty.papakarlo.ui.adapter.diff_util.OrderDiffCallback
import com.bunbeauty.presentation.item.OrderItem
import javax.inject.Inject

class OrderAdapter @Inject constructor() :
    BaseListAdapter<OrderItem, ElementOrderBinding, OrderAdapter.OrderViewHolder>(OrderDiffCallback()) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): OrderViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ElementOrderBinding.inflate(inflater, viewGroup, false)

        return OrderViewHolder(binding)
    }

    inner class OrderViewHolder(binding: ElementOrderBinding) :
        BaseViewHolder<ElementOrderBinding, OrderItem>(binding) {

        override fun onBind(item: OrderItem) {
            super.onBind(item)

            binding.run {
                elementOrderTvCode.text = item.code
                elementOrderTvTime.text = item.dateTime
                elementOrderChipStatus.text = item.orderStatus
                elementOrderChipStatus.setChipBackgroundColorResource(item.orderColorResource)

                elementOrderMvcMain.setOnClickListener {
                    onItemClicked(item)
                }
            }
        }

        override fun onBind(item: OrderItem, payloads: List<Any>) {
            super.onBind(item, payloads)

            if (payloads.last() as Boolean) {
                binding.run {
                    elementOrderChipStatus.text = item.orderStatus
                    elementOrderChipStatus.setChipBackgroundColorResource(item.orderColorResource)

                    elementOrderMvcMain.setOnClickListener {
                        onItemClicked(item)
                    }
                }
            }
        }
    }
}