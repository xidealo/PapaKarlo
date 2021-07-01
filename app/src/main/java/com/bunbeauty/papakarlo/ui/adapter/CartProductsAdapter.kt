package com.bunbeauty.papakarlo.ui.adapter

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bunbeauty.domain.model.adapter.CartProductAdapterModel
import com.bunbeauty.papakarlo.extensions.toggleVisibility
import com.bunbeauty.papakarlo.databinding.ElementCartProductBinding
import com.bunbeauty.papakarlo.ui.view.CountPicker
import com.bunbeauty.papakarlo.R
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import javax.inject.Inject

class CartProductsAdapter @Inject constructor() :
    BaseAdapter<CartProductsAdapter.CartProductViewHolder, CartProductAdapterModel, MyDiffCallback>() {

    var canBeChanged: Boolean = true

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CartProductViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ElementCartProductBinding.inflate(inflater, viewGroup, false)

        return CartProductViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: CartProductViewHolder, i: Int) {
        holder.onBind(itemList[i])
    }

    inner class CartProductViewHolder(view: View) :
        BaseViewHolder<ElementCartProductBinding, CartProductAdapterModel>(DataBindingUtil.bind(view)!!) {

        fun setCountChangeListener(cartProduct: CartProductAdapterModel) {
            binding.elementCartProductCpCount.countChangeListener =
                object : CountPicker.CountChangeListener {
                    override fun onCountIncreased() {
                        //val updatedProduct = cartProduct.copy(count = cartProduct.count + 1)
                        //consumerCartViewModel.updateCartProduct(updatedProduct)
                    }

                    override fun onCountDecreased() {
                        //val updatedProduct = cartProduct.copy(count = cartProduct.count - 1)
                        //consumerCartViewModel.updateCartProduct(updatedProduct)
                    }
                }
        }

        override fun onBind(item: CartProductAdapterModel) {
            super.onBind(item)
            with(binding) {
                elementCartProductTvTitle.text = item.name
                elementCartProductTvCost.text = item.discountCost
                elementCartProductTvOldCost.text = item.cost

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

                if (canBeChanged) { }
            }
        }

        override fun onBind(item: CartProductAdapterModel, payloads: List<Any>) {
            super.onBind(item, payloads)
            /*   val isSaved = payloads.last() as Boolean
               binding.tbLike.setChecked(isSaved)*/
        }
    }
}