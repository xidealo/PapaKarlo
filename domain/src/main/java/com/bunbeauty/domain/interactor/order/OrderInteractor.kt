package com.bunbeauty.domain.interactor.order

import com.bunbeauty.domain.mapListFlow
import com.bunbeauty.domain.mapper.IOrderMapper
import com.bunbeauty.domain.model.order.CreatedOrder
import com.bunbeauty.domain.model.order.LightOrder
import com.bunbeauty.domain.model.order.Order
import com.bunbeauty.domain.model.product.CreatedOrderProduct
import com.bunbeauty.domain.repo.Api
import com.bunbeauty.domain.repo.CartProductRepo
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.OrderRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OrderInteractor @Inject constructor(
    @Api private val orderRepo: OrderRepo,
    @Api private val cartProductRepo: CartProductRepo,
    private val dataStoreRepo: DataStoreRepo,
    private val orderMapper: IOrderMapper
) : IOrderInteractor {

    override suspend fun observeOrderList(): Flow<List<LightOrder>> {
        val userUuid = dataStoreRepo.getUserUuid()
        return orderRepo.observeOrderListByUserUuid(userUuid ?: "").mapListFlow { order ->
            orderMapper.toLightOrder(order)
        }
    }

    override suspend fun createOrder(
        isDelivery: Boolean,
        userAddressUuid: String?,
        cafeUuid: String?,
        addressDescription: String,
        comment: String?,
        deferredTime: Long?
    ): Order? {
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
                    menuProductUuid = cartProduct.menuProduct.uuid,
                    count = cartProduct.count
                )
            },
        )

        return orderRepo.createOrder(token, createdOrder)
    }

}