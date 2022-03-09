package com.bunbeauty.papakarlo.feature.consumer_cart

import com.bunbeauty.papakarlo.common.DefaultDiffCallback

class CartProductDiffCallback : DefaultDiffCallback<CartProductItemModel>() {

    override fun getChangePayload(
        oldItem: CartProductItemModel,
        newItem: CartProductItemModel
    ): Any? {
        return if (oldItem.count != newItem.count)
            true
        else
            null
    }

}