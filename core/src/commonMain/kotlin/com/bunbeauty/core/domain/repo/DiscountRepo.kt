package com.bunbeauty.core.domain.repo

import com.bunbeauty.core.model.Discount

interface DiscountRepo {
    suspend fun getDiscount(): Discount?
}
