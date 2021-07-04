package com.bunbeauty.papakarlo.ui.adapter

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.bunbeauty.domain.model.adapter.CartProductAdapterModel
import com.bunbeauty.papakarlo.extensions.toggleVisibility
import com.bunbeauty.papakarlo.databinding.ElementCartProductBinding
import com.bunbeauty.papakarlo.ui.view.CountPicker
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.presentation.ConsumerCartViewModel
import com.bunbeauty.papakarlo.ui.adapter.diff_util.CartProductDiffCallback
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import javax.inject.Inject

class CartProductsAdapter @Inject constructor() :
    ListAdapter<CartProductAdapterModel, BaseViewHolder<ViewBinding, CartProductAdapterModel>>(
        CartProductDiffCallback()
    ) {

    var canBeChanged: Boolean = true
    lateinit var consumerCartViewModel: ConsumerCartViewModel

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CartProductViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ElementCartProductBinding.inflate(inflater, viewGroup, false)

        return CartProductViewHolder(binding.root)
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<ViewBinding, CartProductAdapterModel>,
        position: Int
    ) {
        holder.onBind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<ViewBinding, CartProductAdapterModel>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNullOrEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            holder.onBind(getItem(position), payloads)
        }
    }

    inner class CartProductViewHolder(view: View) :
        BaseViewHolder<ElementCartProductBinding, CartProductAdapterModel>(DataBindingUtil.bind(view)!!) {

        override fun onBind(item: CartProductAdapterModel) {
            super.onBind(item)
            with(binding) {
                elementCartProductTvTitle.text = item.name
                elementCartProductTvCost.text = item.discountCost
                elementCartProductTvOldCost.text = item.cost
                elementCartProductCpCount.count = item.count
                Picasso.get()
                    .load(item.photoLink)
                    .fit()
                    .placeholder(R.drawable.default_product)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(elementCartProductIvPhoto)

                elementCartProductCpCount.toggleVisibility(canBeChanged)
                if (item.discountCost.isNotEmpty()) {
                    elementCartProductTvOldCost.paintFlags =
                        elementCartProductTvOldCost.paintFlags or STRIKE_THRU_TEXT_FLAG
                }

                elementCartProductCpCount.countChangeListener =
                    object : CountPicker.CountChangeListener {
                        override fun onCountIncreased() {
                            consumerCartViewModel.updateCartProduct(
                                item.uuid,
                                item.count + 1
                            )
                        }

                        override fun onCountDecreased() {
                            consumerCartViewModel.updateCartProduct(
                                item.uuid,
                                item.count - 1
                            )
                        }
                    }
            }
        }

        override fun onBind(item: CartProductAdapterModel, payloads: List<Any>) {
            super.onBind(item, payloads)
            if (payloads.last() as Boolean) {
                with(binding) {
                    elementCartProductTvCost.text = item.discountCost
                    elementCartProductTvOldCost.text = item.cost
                    elementCartProductCpCount.count = item.count

                    elementCartProductCpCount.countChangeListener =
                        object : CountPicker.CountChangeListener {
                            override fun onCountIncreased() {
                                consumerCartViewModel.updateCartProduct(
                                    item.uuid,
                                    item.count + 1
                                )
                            }

                            override fun onCountDecreased() {
                                consumerCartViewModel.updateCartProduct(
                                    item.uuid,
                                    item.count - 1
                                )
                            }
                        }
                }
            }
        }
    }
}