package com.bunbeauty.domain.interactor.order

import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.mapFlow
import com.bunbeauty.domain.model.order.CreatedOrder
import com.bunbeauty.domain.model.order.LightOrder
import com.bunbeauty.domain.model.order.Order
import com.bunbeauty.domain.model.order.OrderCode
import com.bunbeauty.domain.model.product.CreatedOrderProduct
import com.bunbeauty.domain.repo.CartProductRepo
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.OrderRepo
import kotlinx.coroutines.flow.Flow

class OrderInteractor  constructor(
    private val orderRepo: OrderRepo,
    private val cartProductRepo: CartProductRepo,
    private val dataStoreRepo: DataStoreRepo
) : IOrderInteractor {

    override suspend fun observeOrderList(): Flow<List<LightOrder>> {
        val userUuid = dataStoreRepo.getUserUuid()
        return orderRepo.observeOrderListByUserUuid(userUuid ?: "")
    }

    override fun observeOrderByUuid(orderUuid: String): Flow<Order?> {
        return orderRepo.observeOrderByUuid(orderUuid)
    }

    override fun observeOrderStatusByUuid(orderUuid: String): Flow<OrderStatus?> {
        return orderRepo.observeOrderByUuid(orderUuid).mapFlow { orderDetails ->
            orderDetails.status
        }
    }

    override suspend fun getOrderByUuid(orderUuid: String): Order? {
        return orderRepo.getOrderByUuid(orderUuid)
    }

    override suspend fun createOrder(
        isDelivery: Boolean,
        userAddressUuid: String?,
        cafeUuid: String?,
        addressDescription: String,
        comment: String?,
        deferredTime: Long?
    ): OrderCode? {
        val token = dataStoreRepo.getToken() ?: return null
        val cartProductList = cartProductRepo.getCartProductList()
        val createdOrder = CreatedOrder(
            isDelivery = isDelivery,
            userAddressUuid = userAddressUuid,
            cafeUuid = cafeUuid,
            addressDescription = addressDescription,
            comment = comment,
            deferredTime = deferredTime,
            orderProducts = cartProductList.map { cartProduct ->
                CreatedOrderProduct(
                    menuProductUuid = cartProduct.product.uuid,
                    count = cartProduct.count
                )
            },
        )

        return orderRepo.createOrder(token, createdOrder)
    }

}