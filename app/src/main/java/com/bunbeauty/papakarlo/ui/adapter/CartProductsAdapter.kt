package com.bunbeauty.papakarlo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bunbeauty.papakarlo.data.model.CartProduct
import com.bunbeauty.papakarlo.databinding.ElementCartProductBinding
import com.bunbeauty.papakarlo.ui.view.CountPicker
import com.bunbeauty.papakarlo.view_model.ConsumerCartViewModel
import javax.inject.Inject

class CartProductsAdapter @Inject constructor() :
    BaseAdapter<CartProductsAdapter.CartProductViewHolder, CartProduct>() {

    lateinit var consumerCartViewModel: ConsumerCartViewModel

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CartProductViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ElementCartProductBinding.inflate(inflater, viewGroup, false)

        return CartProductViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: CartProductViewHolder, i: Int) {
        holder.setListener(itemList[i])
        holder.binding?.cartProduct = itemList[i]
        holder.binding?.elementCartProductCpCount?.countChangeCallback = object : CountPicker.CountChangeCallback {
            override fun onCountIncreased() {
                itemList[i].count++
                consumerCartViewModel.updateCartProduct(itemList[i])
            }

            override fun onCountDecreased() {
                itemList[i].count--
                consumerCartViewModel.updateCartProduct(itemList[i])
            }
        }
    }

    inner class CartProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = DataBindingUtil.bind<ElementCartProductBinding>(view)

        fun setListener(cartProduct: CartProduct) {

        }
    }
}