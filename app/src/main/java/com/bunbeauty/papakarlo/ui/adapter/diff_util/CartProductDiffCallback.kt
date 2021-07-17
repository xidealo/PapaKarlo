package com.bunbeauty.papakarlo.ui.adapter.diff_util

import androidx.recyclerview.widget.DiffUtil
import com.bunbeauty.presentation.view_model.base.adapter.CartProductItem

class CartProductDiffCallback : DiffUtil.ItemCallback<CartProductItem>() {

    override fun areItemsTheSame(
        oldItem: CartProductItem,
        newItem: CartProductItem
    ): Boolean {
        return oldItem.uuid == newItem.uuid
    }

    override fun areContentsTheSame(
        oldItem: CartProductItem,
        newItem: CartProductItem
    ): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(
        oldItem: CartProductItem,
        newItem: CartProductItem
    ): Any? {
        return if (oldItem.count != newItem.count)
            true
        else
            null
    }

    /* private fun getItemCallback(
         cartProductAdapterModel: CartProductAdapterModel
     ): DiffUtil.ItemCallback<CartProductAdapterModel> = cartProductAdapterModelList.find { it.isRelativeItem(cartProductAdapterModel) }
         ?.getDiffUtil()
         ?.let { it as DiffUtil.ItemCallback<CartProductAdapterModel> }
         ?: throw IllegalStateException("DiffUtil not found for $cartProductAdapterModel")
 */


}