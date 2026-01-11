package com.bunbeauty.core.domain.discount

import com.bunbeauty.core.domain.repo.DiscountRepo
import com.bunbeauty.core.domain.repo.OrderRepo
import com.bunbeauty.core.model.Discount

interface GetDiscountUseCase {
    suspend operator fun invoke(): Discount?
}

class GetDiscountUseCaseImpl(
    private val discountRepository: DiscountRepo,
    private val orderRepository: OrderRepo,
) : GetDiscountUseCase {
    override suspend operator fun invoke(): Discount? {
        val lastOrder = orderRepository.getLastOrderByUserUuidLocalFirst()


        return if (lastOrder == null) {
            discountRepository.getDiscount()
        } else {
            null
        }
    }
}
