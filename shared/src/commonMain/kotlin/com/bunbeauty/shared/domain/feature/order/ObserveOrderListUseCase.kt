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

    suspend operator fun invoke(tag: String): Pair<String?, Flow<List<LightOrder>>> {
        val token = dataStoreRepo.getToken() ?: return null to flow {}
        val userUuid = dataStoreRepo.getUserUuid() ?: return null to flow {}
        val orderList = orderRepo.getOrderListByUserUuid(token = token, userUuid = userUuid)

        val (uuid, orderListUpdatesFlow) = orderRepo.observeOrderListUpdates(token, userUuid)
        return uuid to merge(
            flow { emit(orderList) },
            orderListUpdatesFlow.mapListFlow(lightOrderMapper::toLightOrder)
        )
    }
}