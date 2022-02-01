package com.bunbeauty.papakarlo.feature.consumer_cart

import com.bunbeauty.papakarlo.common.DefaultDiffCallback

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