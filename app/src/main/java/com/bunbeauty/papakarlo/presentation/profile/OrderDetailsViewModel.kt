package com.bunbeauty.papakarlo.presentation.profile

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.model.Delivery
import com.bunbeauty.domain.model.order.Order
import com.bunbeauty.domain.model.product.OrderProduct
import com.bunbeauty.domain.repo.Api
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.OrderRepo
import com.bunbeauty.domain.util.date_time.IDateTimeUtil
import com.bunbeauty.domain.util.order.IOrderUtil
import com.bunbeauty.domain.util.product.IProductHelper
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.presentation.state.State
import com.bunbeauty.papakarlo.presentation.state.toSuccessOrEmpty
import com.bunbeauty.presentation.item.OrderProductItem
import com.bunbeauty.presentation.model.OrderUI
import com.bunbeauty.presentation.util.string.IStringUtil
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class OrderDetailsViewModel @Inject constructor(
    @Api private val orderRepo: OrderRepo,
    private val dataStoreRepo: DataStoreRepo,
    private val stringUtil: IStringUtil,
    private val productHelper: IProductHelper,
    private val orderUtil: IOrderUtil,
    private val dateTimeUtil: IDateTimeUtil
) : BaseViewModel() {

    private val mutableOrderState: MutableStateFlow<State<OrderUI>> =
        MutableStateFlow(State.Loading())
    val orderState: StateFlow<State<OrderUI>> = mutableOrderState.asStateFlow()

    fun getOrder(orderUuid: String) {
        orderRepo.observeOrderByUuid(orderUuid).onEach { order ->
            val delivery = dataStoreRepo.getDelivery()
            mutableOrderState.value = order?.toUI(delivery).toSuccessOrEmpty()
        }.launchIn(viewModelScope)
    }

    private fun Order.toUI(delivery: Delivery): OrderUI {
        val deferredTime = deferredTime?.let { time ->
            dateTimeUtil.getTimeHHMM(time)
        }
        val deliveryCost = stringUtil.getDeliveryCostString(
            orderUtil.getDeliveryCost(this, delivery)
        )
        val orderProductList = orderProductList.map { orderProduct ->
            orderProduct.toItem()
        }

        return OrderUI(
            code = code,
            stepCount = orderUtil.getOrderStepCount(status),
            status = stringUtil.getOrderStatusString(status),
            orderStatusBackground = orderUtil.getBackgroundColor(status),
            dateTime = dateTimeUtil.getTimeDDMMMMHHMM(time),
            pickupMethod = stringUtil.getPickupMethodString(isDelivery),
            deferredTime = deferredTime,
            address = address,
            comment = comment,
            deliveryCost = deliveryCost,
            oldTotalCost = stringUtil.getCostString(orderUtil.getOldOrderCost(this, delivery)),
            newTotalCost = stringUtil.getCostString(orderUtil.getNewOrderCost(this, delivery)),
            orderProductList = orderProductList,
            isDelivery = isDelivery,
        )
    }

    private fun OrderProduct.toItem(): OrderProductItem {
        val newCost = productHelper.getProductPositionNewCost(this)
        val oldCost = productHelper.getProductPositionOldCost(this)
        val newCostString = stringUtil.getCostString(newCost)
        val oldCostString = stringUtil.getCostString(oldCost)
        val countString = stringUtil.getCountString(count)

        return OrderProductItem(
            uuid = uuid,
            name = menuProduct.name,
            newCost = newCostString,
            oldCost = oldCostString,
            photoLink = menuProduct.photoLink,
            count = countString
        )
    }
}