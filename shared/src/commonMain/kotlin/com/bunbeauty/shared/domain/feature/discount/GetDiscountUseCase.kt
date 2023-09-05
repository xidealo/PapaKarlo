package com.bunbeauty.shared.domain.feature.discount

import com.bunbeauty.shared.data.repository.DiscountRepository
import com.bunbeauty.shared.domain.model.Discount

class GetDiscountUseCase(
    private val discountRepository: DiscountRepository,
) {
    suspend operator fun invoke(): Discount? {
        return discountRepository.getDiscount()
    }
}