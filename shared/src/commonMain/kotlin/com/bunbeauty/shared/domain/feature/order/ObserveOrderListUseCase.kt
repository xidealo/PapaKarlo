package com.bunbeauty.shared.domain.feature.order

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.mapListFlow
import com.bunbeauty.shared.domain.model.order.LightOrder
import com.bunbeauty.shared.domain.repo.OrderRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge

class ObserveOrderListUseCase(
    private val dataStoreRepo: DataStoreRepo,
    private val orderRepo: OrderRepo,
    private val lightOrderMapper: LightOrderMapper,
) {

    suspend operator fun invoke(): Flow<List<LightOrder>> {
        val token = dataStoreRepo.getToken() ?: return flow {}
        val userUuid = dataStoreRepo.getUserUuid() ?: return flow {}
        val orderList = orderRepo.getOrderListByUserUuid(token = token, userUuid = userUuid)

        return merge(
            flow { emit(orderList) },
            orderRepo.observeOrderListUpdates(token, userUuid)
                .mapListFlow(lightOrderMapper::toLightOrder)
        )
    }
}