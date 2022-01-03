package com.bunbeauty.papakarlo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.ElementOrderProductBinding
import com.bunbeauty.papakarlo.extensions.strikeOutText
import com.bunbeauty.papakarlo.extensions.toggleVisibility
import com.bunbeauty.papakarlo.ui.adapter.base.BaseListAdapter
import com.bunbeauty.papakarlo.ui.adapter.base.BaseViewHolder
import com.bunbeauty.papakarlo.ui.adapter.diff_util.DefaultDiffCallback
import com.bunbeauty.presentation.item.OrderProductItem
import javax.inject.Inject

class OrderProductAdapter @Inject constructor() :
    BaseListAdapter<OrderProductItem, OrderProductAdapter.OrderProductViewHolder>(
        DefaultDiffCallback()
    ) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): OrderProductViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ElementOrderProductBinding.inflate(inflater, viewGroup, false)

        return OrderProductViewHolder(binding)
    }

    inner class OrderProductViewHolder(binding: ElementOrderProductBinding) :
        BaseViewHolder<OrderProductItem>(binding) {

        override fun onBind(item: OrderProductItem) {
            super.onBind(item)

            (binding as ElementOrderProductBinding).run {
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