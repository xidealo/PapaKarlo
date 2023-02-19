package com.bunbeauty.shared.domain.feature.order

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.model.order.LightOrder
import com.bunbeauty.shared.domain.repo.OrderRepo

class GetLastOrderUseCase(
    private val orderRepo: OrderRepo,
    private val dataStoreRepo: DataStoreRepo,
) {
    suspend operator fun invoke(): LightOrder? {
        val token = dataStoreRepo.getToken() ?: return null
        val userUuid = dataStoreRepo.getUserUuid() ?: return null
        return orderRepo.getLastOrderByUserUuid(token = token, userUuid = userUuid)
    }
}