package com.bunbeauty.papakarlo.feature.profile.order.order_list

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bunbeauty.papakarlo.common.BaseListAdapter
import com.bunbeauty.papakarlo.common.BaseViewHolder
import com.bunbeauty.papakarlo.databinding.ElementOrderBinding
import javax.inject.Inject

class OrderAdapter @Inject constructor() :
    BaseListAdapter<OrderItem, OrderAdapter.OrderViewHolder>(OrderDiffCallback()) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): OrderViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ElementOrderBinding.inflate(inflater, viewGroup, false)

        return OrderViewHolder(binding)
    }

    inner class OrderViewHolder(private val elementOrderBinding: ElementOrderBinding) :
        BaseViewHolder<OrderItem>(elementOrderBinding) {

        override fun onBind(item: OrderItem) {
            super.onBind(item)

            elementOrderBinding.run {
                elementOrderTvCode.text = item.code
                elementOrderTvTime.text = item.dateTime
                elementOrderChipStatus.text = item.statusName
                elementOrderChipStatus.setChipBackgroundColorResource(item.statusColorResource)

                elementOrderMvcMain.setOnClickListener {
                    onItemClicked(item)
                }
            }
        }

        override fun onBind(item: OrderItem, payloads: List<Any>) {
            super.onBind(item, payloads)

            if (payloads.last() as Boolean) {
                elementOrderBinding.run {
                    elementOrderChipStatus.text = item.statusName
                    elementOrderChipStatus.setChipBackgroundColorResource(item.statusColorResource)

                    elementOrderMvcMain.setOnClickListener {
                        onItemClicked(item)
                    }
                }
            }
        }
    }
}