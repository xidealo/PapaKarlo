package com.bunbeauty.core.domain.discount

import com.bunbeauty.core.domain.repo.DiscountRepo
import com.bunbeauty.core.domain.repo.OrderRepo
import com.bunbeauty.core.domain.repo.SettingsRepo
import com.bunbeauty.core.model.Discount
import com.bunbeauty.core.model.DiscountSource

interface GetDiscountUseCase {
    suspend operator fun invoke(): Discount?
}

class GetDiscountUseCaseImpl(
    private val discountRepository: DiscountRepo,
    private val orderRepository: OrderRepo,
    private val settingsRepo: SettingsRepo,
) : GetDiscountUseCase {
    override suspend operator fun invoke(): Discount? {
        val personalDiscountPercent = settingsRepo.getSettings()?.personalDiscountPercent
        if (personalDiscountPercent != null) {
            return Discount(
                firstOrderDiscount = personalDiscountPercent,
                source = DiscountSource.PERSONAL,
            )
        }

        val lastOrder = orderRepository.getLastOrderByUserUuidLocalFirst()
        return if (lastOrder == null) {
            discountRepository.getDiscount()?.copy(source = DiscountSource.FIRST_ORDER)
        } else {
            null
        }
    }
}
