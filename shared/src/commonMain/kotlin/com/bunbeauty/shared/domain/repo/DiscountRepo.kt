package com.bunbeauty.shared.domain.repo

import com.bunbeauty.shared.domain.model.Discount

interface DiscountRepo {
    suspend fun getDiscount(): Discount?
}
