package com.bunbeauty.papakarlo.ui.adapter.diff_util

import com.bunbeauty.presentation.item.CartProductItem

class CartProductDiffCallback : DefaultDiffCallback<CartProductItem>() {

    override fun getChangePayload(
        oldItem: CartProductItem,
        newItem: CartProductItem
    ): Any? {
        return if (oldItem.count != newItem.count)
            true
        else
            null
    }

}