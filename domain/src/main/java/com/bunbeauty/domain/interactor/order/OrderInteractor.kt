package com.bunbeauty.domain.interactor.order

import com.bunbeauty.domain.interactor.user.IUserInteractor
import com.bunbeauty.domain.mapListFlow
import com.bunbeauty.domain.mapper.IOrderMapper
import com.bunbeauty.domain.model.order.LightOrder
import com.bunbeauty.domain.repo.Api
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.OrderRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OrderInteractor @Inject constructor(
    @Api private val orderRepo: OrderRepo,
    private val dataStoreRepo: DataStoreRepo,
    private val userInteractor: IUserInteractor,
    private val orderMapper: IOrderMapper
) : IOrderInteractor {

    override suspend fun observeOrderList(): Flow<List<LightOrder>> {
        return if (userInteractor.isUserAuthorize()) {
            val userUuid = dataStoreRepo.getUserUuid()
            orderRepo.observeOrderListByUserUuid(userUuid ?: "").mapListFlow { order ->
                orderMapper.toLightOrder(order)
            }
        } else {
            flow { emit(emptyList<LightOrder>()) }
        }
    }

}