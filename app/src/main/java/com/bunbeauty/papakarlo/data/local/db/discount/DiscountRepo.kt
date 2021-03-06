package com.bunbeauty.papakarlo.data.local.db.discount

import com.bunbeauty.papakarlo.data.model.discount.DiscountEntity

interface DiscountRepo {
    suspend fun insert(discountEntity: DiscountEntity)
    suspend fun getDiscounts()
}