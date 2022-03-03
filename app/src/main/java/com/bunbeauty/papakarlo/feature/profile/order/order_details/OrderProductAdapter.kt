package com.bunbeauty.papakarlo.feature.profile.order.order_details

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseListAdapter
import com.bunbeauty.papakarlo.common.BaseViewHolder
import com.bunbeauty.papakarlo.common.DefaultDiffCallback
import com.bunbeauty.papakarlo.databinding.ElementOrderProductBinding
import com.bunbeauty.papakarlo.extensions.strikeOutText
import com.bunbeauty.papakarlo.extensions.toggleVisibility

class OrderProductAdapter :
    BaseListAdapter<OrderProductItemModel, OrderProductAdapter.OrderProductViewHolder>(
        DefaultDiffCallback()
    ) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): OrderProductViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ElementOrderProductBinding.inflate(inflater, viewGroup, false)

        return OrderProductViewHolder(binding)
    }

    inner class OrderProductViewHolder(private val elementOrderProductBinding: ElementOrderProductBinding) :
        BaseViewHolder<OrderProductItemModel>(elementOrderProductBinding) {

        override fun onBind(item: OrderProductItemModel) {
            super.onBind(item)

            elementOrderProductBinding.run {
                elementOrderProductTvName.text = item.name
                elementOrderProductTvCount.text = item.count
                elementOrderProductTvOldCost.text = item.oldCost
                elementOrderProductTvOldCost.strikeOutText()
                elementOrderProductTvOldCost.toggleVisibility(item.oldCost != null)
                elementOrderProductTvNewCost.text = item.newCost
                elementOrderProductIvPhoto.load(item.photoLink) {
                    placeholder(R.drawable.placeholder)
                }
            }
        }
    }
}