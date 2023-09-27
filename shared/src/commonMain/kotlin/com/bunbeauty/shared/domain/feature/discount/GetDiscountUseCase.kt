package com.bunbeauty.shared.domain.feature.discount

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.data.repository.DiscountRepository
import com.bunbeauty.shared.domain.model.Discount
import com.bunbeauty.shared.domain.repo.OrderRepo

class GetDiscountUseCase(
    private val discountRepository: DiscountRepository,
    private val orderRepository: OrderRepo,
    private val dataStoreRepo: DataStoreRepo,
) {
    suspend operator fun invoke(): Discount? {
        val userUuid = dataStoreRepo.getUserUuid()
        val token = dataStoreRepo.getToken()
        val lastOrder = if (userUuid != null && token != null) {
            orderRepository.getLastOrderByUserUuidLocalFirst(
                token = token,
                userUuid = userUuid
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