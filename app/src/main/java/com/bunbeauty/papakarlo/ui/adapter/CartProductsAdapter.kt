package com.bunbeauty.papakarlo.ui.adapter

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bunbeauty.papakarlo.presentation.extensions.toggleVisibility
import com.bunbeauty.domain.model.CartProduct
import com.bunbeauty.papakarlo.databinding.ElementCartProductBinding
import com.bunbeauty.papakarlo.ui.view.CountPicker
import com.bunbeauty.domain.util.product.IProductHelper
import com.bunbeauty.domain.util.string_helper.IStringHelper
import com.bunbeauty.papakarlo.presentation.ConsumerCartViewModel
import javax.inject.Inject

class CartProductsAdapter @Inject constructor(
    private val stringHelper: IStringHelper,
    private val productHelper: IProductHelper
) : BaseAdapter<CartProductsAdapter.CartProductViewHolder, CartProduct>() {

    lateinit var consumerCartViewModel: ConsumerCartViewModel
    var canBeChanged: Boolean = true

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CartProductViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ElementCartProductBinding.inflate(inflater, viewGroup, false)

        return CartProductViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: CartProductViewHolder, i: Int) {
        holder.binding?.cartProduct = itemList[i]
        holder.binding?.stringHelper = stringHelper
        holder.binding?.productHelper = productHelper
        holder.binding?.elementCartProductCpCount?.toggleVisibility(canBeChanged)

        if (canBeChanged) {
            holder.setCountChangeListener(itemList[i])
            if (holder.binding?.elementCartProductTvOldPrice != null) {
                holder.binding.elementCartProductTvOldPrice.paintFlags = holder.binding.elementCartProductTvOldPrice.paintFlags or STRIKE_THRU_TEXT_FLAG
            }
        }
    }

    inner class CartProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = DataBindingUtil.bind<ElementCartProductBinding>(view)

        fun setCountChangeListener(cartProduct: CartProduct) {
            binding?.elementCartProductCpCount?.countChangeListener =
                object : CountPicker.CountChangeListener {
                    override fun onCountIncreased() {
                        val updatedProduct = cartProduct.copy(count = cartProduct.count + 1)
                        consumerCartViewModel.updateCartProduct(updatedProduct)
                    }

                    override fun onCountDecreased() {
                        val updatedProduct = cartProduct.copy(count = cartProduct.count - 1)
                        consumerCartViewModel.updateCartProduct(updatedProduct)
                    }
                }
        }
    }
}