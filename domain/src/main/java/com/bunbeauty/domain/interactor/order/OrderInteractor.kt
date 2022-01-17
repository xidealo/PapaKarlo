package com.bunbeauty.domain.interactor.order

import com.bunbeauty.common.Constants
import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.interactor.product.IProductInteractor
import com.bunbeauty.domain.mapFlow
import com.bunbeauty.domain.mapListFlow
import com.bunbeauty.domain.mapper.IOrderMapper
import com.bunbeauty.domain.model.order.CreatedOrder
import com.bunbeauty.domain.model.order.LightOrder
import com.bunbeauty.domain.model.order.Order
import com.bunbeauty.domain.model.order.OrderDetails
import com.bunbeauty.domain.model.product.CreatedOrderProduct
import com.bunbeauty.domain.repo.Api
import com.bunbeauty.domain.repo.CartProductRepo
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.OrderRepo
import kotlinx.coroutines.flow.Flow
import org.joda.time.DateTime
import javax.inject.Inject

class OrderInteractor @Inject constructor(
    @Api private val orderRepo: OrderRepo,
    @Api private val cartProductRepo: CartProductRepo,
    private val dataStoreRepo: DataStoreRepo,
    private val orderMapper: IOrderMapper,
    private val productInteractor: IProductInteractor,
) : IOrderInteractor {

    override suspend fun observeOrderList(): Flow<List<LightOrder>> {
        val userUuid = dataStoreRepo.getUserUuid()
        return orderRepo.observeOrderListByUserUuid(userUuid ?: "").mapListFlow { order ->
            orderMapper.toLightOrder(order)
        }
    }

    override suspend fun getOrderByUuid(orderUuid: String): OrderDetails? {
        return orderRepo.getOrderByUuid(orderUuid)?.let { order ->
            OrderDetails(
                code = order.code,
                stepCount = getOrderStepCount(order.status),
                status = order.status,
                dateTime = getTimeDDMMMMHHMM(order.time),
                isDelivery = order.isDelivery,
                deferredTime = getTimeHHMM(order.deferredTime),
                address = order.address,
                comment = order.comment,
                deliveryCost = productInteractor.getDeliveryCost(order.orderProductList),
                oldTotalCost = productInteractor.getOldTotalCost(order.orderProductList),
                newTotalCost = productInteractor.getNewTotalCost(order.orderProductList),
                orderProductList = order.orderProductList,
            )
        }
    }

    override fun observeOrderByUuid(orderUuid: String): Flow<OrderDetails?> {
        return orderRepo.observeOrderByUuid(orderUuid).mapFlow { order ->
            OrderDetails(
                code = order.code,
                stepCount = getOrderStepCount(order.status),
                status = order.status,
                dateTime = getTimeDDMMMMHHMM(order.time),
                isDelivery = order.isDelivery,
                deferredTime = getTimeHHMM(order.deferredTime),
                address = order.address,
                comment = order.comment,
                deliveryCost = productInteractor.getDeliveryCost(order.orderProductList),
                oldTotalCost = productInteractor.getOldTotalCost(order.orderProductList),
                newTotalCost = productInteractor.getNewTotalCost(order.orderProductList),
                orderProductList = order.orderProductList,
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
                    menuProductUuid = cartProduct.product.uuid,
                    count = cartProduct.count
                )
            },
        )

        return orderRepo.createOrder(token, createdOrder)
    }

    fun getOrderStepCount(status: OrderStatus): Int {
        return when (status) {
            OrderStatus.NOT_ACCEPTED -> 1
            OrderStatus.ACCEPTED -> 1
            OrderStatus.PREPARING -> 2
            OrderStatus.SENT_OUT -> 3
            OrderStatus.DONE -> 3
            OrderStatus.DELIVERED -> 4
            OrderStatus.CANCELED -> 0
        }
    }

    fun getTimeDDMMMMHHMM(millis: Long): String {
        return DateTime(millis).toString(Constants.DD_MMMM_HH_MM_PATTERN)
    }

    fun getTimeHHMM(millis: Long?): String? {
        return if (millis == null) {
            null
        } else {
            DateTime(millis).toString(Constants.HH_MM_PATTERN)
        }
    }

}