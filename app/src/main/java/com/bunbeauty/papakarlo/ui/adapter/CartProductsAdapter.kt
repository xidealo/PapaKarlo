package com.bunbeauty.papakarlo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bunbeauty.papakarlo.data.model.CartProduct
import com.bunbeauty.papakarlo.databinding.ElementCartProductBinding
import javax.inject.Inject

class CartProductsAdapter @Inject constructor() :
    BaseAdapter<CartProductsAdapter.CartProductViewHolder, CartProduct>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CartProductViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ElementCartProductBinding.inflate(inflater, viewGroup, false)

        return CartProductViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: CartProductViewHolder, i: Int) {
        holder.setListener(itemList[i])
        holder.binding?.cartProduct = itemList[i]
    }

    inner class CartProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = DataBindingUtil.bind<ElementCartProductBinding>(view)

        fun setListener(cartProduct: CartProduct) {

        }
    }
}