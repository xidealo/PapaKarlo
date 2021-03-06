package com.bunbeauty.papakarlo.data.local.db.discount_product

import com.bunbeauty.papakarlo.data.model.discount.DiscountProduct

interface DiscountProductRepo {
    suspend fun insert(discountProduct: DiscountProduct)
}