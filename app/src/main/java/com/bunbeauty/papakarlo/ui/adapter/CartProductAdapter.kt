package com.bunbeauty.papakarlo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.ElementCartProductBinding
import com.bunbeauty.papakarlo.extensions.strikeOutText
import com.bunbeauty.papakarlo.extensions.toggleVisibility
import com.bunbeauty.papakarlo.ui.adapter.diff_util.CartProductDiffCallback
import com.bunbeauty.papakarlo.ui.view.CountPicker
import com.bunbeauty.presentation.view_model.base.adapter.CartProductItem
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import javax.inject.Inject

class CartProductAdapter @Inject constructor() :
    ListAdapter<CartProductItem, CartProductAdapter.CartProductViewHolder>(
        CartProductDiffCallback()
    ) {

    var canBeChanged: Boolean = true
    var countChangeListener: ItemCountChangeListener? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CartProductViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ElementCartProductBinding.inflate(inflater, viewGroup, false)

        return CartProductViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CartProductAdapter.CartProductViewHolder,
        position: Int
    ) {
        holder.onBind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: CartProductAdapter.CartProductViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNullOrEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            holder.onBind(getItem(position), payloads)
        }
    }

    inner class CartProductViewHolder(binding: ElementCartProductBinding) :
        BaseViewHolder<ElementCartProductBinding, CartProductItem>(binding) {

        override fun onBind(item: CartProductItem) {
            super.onBind(item)

            binding.run {
                elementCartProductTvTitle.text = item.name
                elementCartProductTvOldCost.text = item.oldCost
                elementCartProductTvOldCost.strikeOutText()
                elementCartProductTvOldCost.toggleVisibility(item.oldCost == null)
                elementCartProductTvNewCost.text = item.newCost
                elementCartProductCpCount.count = item.count
                Picasso.get()
                    .load(item.photoLink)
                    .fit()
                    .placeholder(R.drawable.default_product)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(elementCartProductIvPhoto)
                elementCartProductCpCount.toggleVisibility(canBeChanged)
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
            return object: CountPicker.CountChangeListener {
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