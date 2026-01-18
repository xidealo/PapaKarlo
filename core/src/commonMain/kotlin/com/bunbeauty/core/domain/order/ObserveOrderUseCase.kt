package com.bunbeauty.core.domain.order

import com.bunbeauty.core.model.order.Order
import com.bunbeauty.core.model.product.OrderProduct
import com.bunbeauty.core.domain.repo.OrderRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge

class ObserveOrderUseCase(
    private val orderRepo: OrderRepo,
) {
    suspend operator fun invoke(orderUuid: String): Pair<String?, Flow<Order?>> {
        val order = orderRepo.getOrderByUuid(orderUuid = orderUuid)
        return if (order == null) {
            null to flow { emit(null) }
        } else {
            val (uuid, orderUpdatesFlow) = orderRepo.observeOrderUpdates()
            uuid to
                merge(
                    flow { emit(order) },
                    orderUpdatesFlow
                        .filter { orderUpdate ->
                            orderUpdate.uuid == order.uuid
                        }.map { orderUpdate ->
                            orderUpdate.copy(
                                orderProductList = getOrderProductListWithSortedAdditions(orderUpdate),
                            )
                        },
                )
        }
    }

    private fun getOrderProductListWithSortedAdditions(order: Order) =
        order.orderProductList.map { orderProduct ->
            orderProduct.copy(
                orderAdditionList = getSortedOrderAdditions(orderProduct),
            )
        }

    private fun getSortedOrderAdditions(orderProduct: OrderProduct) =
        orderProduct.orderAdditionList.sortedBy { orderAddition ->
            orderAddition.priority
        }
}
