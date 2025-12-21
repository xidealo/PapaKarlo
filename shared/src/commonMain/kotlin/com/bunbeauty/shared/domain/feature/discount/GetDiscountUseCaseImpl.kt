package com.bunbeauty.shared.domain.feature.discount

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.model.Discount
import com.bunbeauty.shared.domain.repo.DiscountRepo
import com.bunbeauty.shared.domain.repo.OrderRepo

interface GetDiscountUseCase {
    suspend operator fun invoke(): Discount?
}

class GetDiscountUseCaseImpl(
    private val discountRepository: DiscountRepo,
    private val orderRepository: OrderRepo,
    private val dataStoreRepo: DataStoreRepo,
) : GetDiscountUseCase {
    override suspend operator fun invoke(): Discount? {
        val userUuid = dataStoreRepo.getUserUuid()
        val token = dataStoreRepo.getToken()
        val lastOrder =
            if (userUuid != null && token != null) {
                orderRepository.getLastOrderByUserUuidLocalFirst(
                    token = token,
                    userUuid = userUuid,
                )
            } else {
                null
            }

        return if (lastOrder == null) {
            discountRepository.getDiscount()
        } else {
            null
        }
    }
}
