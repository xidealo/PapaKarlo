package com.bunbeauty.papakarlo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.ElementCartProductBinding
import com.bunbeauty.papakarlo.extensions.strikeOutText
import com.bunbeauty.papakarlo.extensions.toggleVisibility
import com.bunbeauty.papakarlo.ui.adapter.base.BaseListAdapter
import com.bunbeauty.papakarlo.ui.adapter.base.BaseViewHolder
import com.bunbeauty.papakarlo.ui.adapter.diff_util.CartProductDiffCallback
import com.bunbeauty.papakarlo.ui.custom.CountPicker
import com.bunbeauty.presentation.item.CartProductItem
import javax.inject.Inject

class CartProductAdapter @Inject constructor() :
    BaseListAdapter<CartProductItem, ElementCartProductBinding, CartProductAdapter.CartProductViewHolder>(
        CartProductDiffCallback()
    ) {

    var countChangeListener: ItemCountChangeListener? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CartProductViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ElementCartProductBinding.inflate(inflater, viewGroup, false)

        return CartProductViewHolder(binding)
    }

    inner class CartProductViewHolder(binding: ElementCartProductBinding) :
        BaseViewHolder<ElementCartProductBinding, CartProductItem>(binding) {

        override fun onBind(item: CartProductItem) {
            super.onBind(item)

            binding.run {
                elementCartProductTvName.text = item.name
                elementCartProductTvOldCost.text = item.oldCost
                elementCartProductTvOldCost.strikeOutText()
                elementCartProductTvOldCost.toggleVisibility(item.oldCost != null)
                elementCartProductTvNewCost.text = item.newCost
                elementCartProductCpCount.count = item.count
                elementCartProductIvPhoto.load(item.photoLink) {
                    placeholder(R.drawable.default_product)
                }
                elementCartProductCpCount.countChangeListener = getCountChangeListener(item)
            }
        }

        override fun onBind(item: CartProductItem, payloads: List<Any>) {
            super.onBind(item, payloads)

            if (payloads.last() as Boolean) {
                binding.run {
                    elementCartProductTvOldCost.text = item.oldCost
                    elementCartProductTvNewCost.text = item.newCost
                    elementCartProductCpCount.count = item.count
                }
            }
        }

        private fun getCountChangeListener(item: CartProductItem): CountPicker.CountChangeListener {
            return object : CountPicker.CountChangeListener {
                override fun onCountIncreased() {
                    countChangeListener?.onItemCountIncreased(item)
                }

                override fun onCountDecreased() {
                    countChangeListener?.onItemCountDecreased(item)
                }
            }
        }
    }

    interface ItemCountChangeListener {
        fun onItemCountIncreased(item: CartProductItem)
        fun onItemCountDecreased(item: CartProductItem)
    }
}