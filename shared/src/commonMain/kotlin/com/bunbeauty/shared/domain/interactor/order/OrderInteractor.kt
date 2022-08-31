package com.bunbeauty.shared.domain.interactor.order

import com.bunbeauty.shared.domain.interactor.product.IProductInteractor
import com.bunbeauty.shared.domain.mapFlow
import com.bunbeauty.shared.domain.model.order.CreatedOrder
import com.bunbeauty.shared.domain.model.order.LightOrder
import com.bunbeauty.shared.domain.model.order.OrderCode
import com.bunbeauty.shared.domain.model.order.OrderWithAmounts
import com.bunbeauty.shared.domain.model.product.CreatedOrderProduct
import com.bunbeauty.shared.domain.model.product.OrderProductWithCosts
import com.bunbeauty.shared.domain.repo.CartProductRepo
import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.CommonFlow
import com.bunbeauty.shared.domain.asCommonFlow
import com.bunbeauty.shared.domain.repo.OrderRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest

class OrderInteractor(
    private val orderRepo: OrderRepo,
    private val cartProductRepo: CartProductRepo,
    private val dataStoreRepo: DataStoreRepo,
    private val productInteractor: IProductInteractor
) : IOrderInteractor {

    override suspend fun observeOrderList(): Flow<List<LightOrder>> {
        val userUuid = dataStoreRepo.getUserUuid()
        return orderRepo.observeOrderListByUserUuid(userUuid ?: "")
    }

    override fun observeOrderListSwift(): CommonFlow<List<LightOrder>> {
        return dataStoreRepo.userUuid.flatMapLatest { userUuid ->
            orderRepo.observeOrderListByUserUuid(userUuid ?: "")
        }.asCommonFlow()
    }

    override fun observeOrderByUuid(orderUuid: String): Flow<OrderWithAmounts?> {
        return orderRepo.observeOrderByUuid(orderUuid).mapFlow { order ->
            OrderWithAmounts(
                uuid = order.uuid,
                code = order.code,
                status = order.status,
                dateTime = order.dateTime,
                isDelivery = order.isDelivery,
                deferredTime = order.deferredTime,
                address = order.address,
                comment = order.comment,
                deliveryCost = order.deliveryCost,
                orderProductList = order.orderProductList.map { orderProduct ->
                    OrderProductWithCosts(
                        uuid = orderProduct.uuid,
                        count = orderProduct.count,
                        newCost = productInteractor.getProductPositionNewCost(orderProduct),
                        oldCost = productInteractor.getProductPositionOldCost(orderProduct),
                        product = orderProduct.product
                    )
                },
                oldAmountToPay = productInteractor.getOldAmountToPay(
                    order.orderProductList,
                    order.deliveryCost
                ),
                newAmountToPay = productInteractor.getNewAmountToPay(
                    order.orderProductList,
                    order.deliveryCost
                ),
            )
        }
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