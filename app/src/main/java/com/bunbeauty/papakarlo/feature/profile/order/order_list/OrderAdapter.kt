package com.bunbeauty.papakarlo.feature.profile.order.order_list

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bunbeauty.papakarlo.common.BaseListAdapter
import com.bunbeauty.papakarlo.common.BaseViewHolder
import com.bunbeauty.papakarlo.databinding.ElementOrderBinding
import com.bunbeauty.papakarlo.util.resources.IResourcesProvider
import javax.inject.Inject

class OrderAdapter @Inject constructor(private val resourcesProvider: IResourcesProvider) :
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
                setStatus(item)
            }
        }

        override fun onBind(item: OrderItem, payloads: List<Any>) {
            super.onBind(item, payloads)

            if (payloads.last() as Boolean) {
                setStatus(item)
            }
        }

        private fun setStatus(item: OrderItem) {
            elementOrderBinding.run {
                elementOrderChipStatus.text = item.statusName
                elementOrderChipStatus.chipBackgroundColor =
                    resourcesProvider.getColorStateListByAttr(item.statusColorResource)

                elementOrderMvcMain.setOnClickListener {
                    onItemClicked(item)
                }
            }
        }
    }
}