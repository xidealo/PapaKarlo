package com.bunbeauty.papakarlo.ui.adapter.diff_util

import androidx.recyclerview.widget.DiffUtil
import com.bunbeauty.presentation.view_model.base.adapter.CartProductAdapterModel

class CartProductDiffCallback : DiffUtil.ItemCallback<CartProductAdapterModel>() {

    override fun areItemsTheSame(
        oldItem: CartProductAdapterModel,
        newItem: CartProductAdapterModel
    ): Boolean {
        return oldItem.uuid == newItem.uuid
    }

    override fun areContentsTheSame(
        oldItem: CartProductAdapterModel,
        newItem: CartProductAdapterModel
    ): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(
        oldItem: CartProductAdapterModel,
        newItem: CartProductAdapterModel
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