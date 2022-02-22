package com.bunbeauty.papakarlo.feature.consumer_cart

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bunbeauty.papakarlo.common.BaseListAdapter
import com.bunbeauty.papakarlo.common.BaseViewHolder
import com.bunbeauty.papakarlo.databinding.ElementCartProductBinding
import com.bunbeauty.papakarlo.extensions.setPhoto
import com.bunbeauty.papakarlo.extensions.strikeOutText
import com.bunbeauty.papakarlo.extensions.toggleVisibility

class CartProductAdapter  constructor() :
    BaseListAdapter<CartProductItem, CartProductAdapter.CartProductViewHolder>(
        CartProductDiffCallback()
    ) {

    var countChangeListener: ItemCountChangeListener? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CartProductViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ElementCartProductBinding.inflate(inflater, viewGroup, false)

        return CartProductViewHolder(binding)
    }

    inner class CartProductViewHolder(private val elementCartProductBinding: ElementCartProductBinding) :
        BaseViewHolder<CartProductItem>(elementCartProductBinding) {

        override fun onBind(item: CartProductItem) {
            super.onBind(item)

            elementCartProductBinding.run {
                elementCartProductTvName.text = item.name
                setCountAndCost(item)
                elementCartProductTvOldCost.strikeOutText()
                elementCartProductTvOldCost.toggleVisibility(item.oldCost != null)
                elementCartProductIvPhoto.setPhoto(item.photoLink)
                elementCartProductCpCount.countChangeListener = getCountChangeListener(item)
            }
        }

        override fun onBind(item: CartProductItem, payloads: List<Any>) {
            super.onBind(item, payloads)

            if (payloads.last() as Boolean) {
                setCountAndCost(item)
            }
        }

        private fun setCountAndCost(item: CartProductItem) {
            elementCartProductBinding.run {
                elementCartProductTvOldCost.text = item.oldCost
                elementCartProductTvNewCost.text = item.newCost
                elementCartProductCpCount.count = item.count
                elementCartProductClMain.setOnClickListener {
                    onItemClicked(item)
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