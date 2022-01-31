package com.bunbeauty.papakarlo.feature.profile.order.order_details

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.interactor.order.IOrderInteractor
import com.bunbeauty.domain.interactor.product.IProductInteractor
import com.bunbeauty.papakarlo.common.mapper.order.IOrderUIMapper
import com.bunbeauty.papakarlo.common.state.State
import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import com.bunbeauty.papakarlo.extensions.toSuccessOrEmpty
import com.bunbeauty.papakarlo.util.string.IStringUtil
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class OrderDetailsViewModel @Inject constructor(
    private val orderInteractor: IOrderInteractor,
    private val productInteractor: IProductInteractor,
    private val stringUtil: IStringUtil,
    private val orderUIMapper: IOrderUIMapper
) : BaseViewModel() {

    private val mutableOrderState: MutableStateFlow<State<OrderUI>> =
        MutableStateFlow(State.Loading())
    val orderState: StateFlow<State<OrderUI>> = mutableOrderState.asStateFlow()

    private val mutableOrderStatus: MutableStateFlow<OrderStatusUI?> = MutableStateFlow(null)
    val orderStatus: StateFlow<OrderStatusUI?> = mutableOrderStatus.asStateFlow()

    private val mutableOldAmountToPay: MutableStateFlow<String?> = MutableStateFlow(null)
    val oldAmountToPay: StateFlow<String?> = mutableOldAmountToPay.asStateFlow()

    private val mutableNewAmountToPay: MutableStateFlow<String?> = MutableStateFlow(null)
    val newAmountToPay: StateFlow<String?> = mutableNewAmountToPay.asStateFlow()

    var isOrderLoaded = false

    fun observeOrder(orderUuid: String) {
        if (isOrderLoaded) {
            return
        }
        isOrderLoaded = true

        viewModelScope.launch {
            mutableOrderState.value =
                orderInteractor.getOrderByUuid(orderUuid)?.let { orderDetails ->
                    mutableOldAmountToPay.value = stringUtil.getCostString(
                        productInteractor.getOldAmountToPay(
                            orderDetails.orderProductList,
                            orderDetails.deliveryCost
                        )
                    )
                    mutableNewAmountToPay.value = stringUtil.getCostString(
                        productInteractor.getNewAmountToPay(
                            orderDetails.orderProductList,
                            orderDetails.deliveryCost
                        )
                    )
                    orderUIMapper.toOrderUI(orderDetails)
                }.toSuccessOrEmpty()
        }
        orderInteractor.observeOrderStatusByUuid(orderUuid).onEach { orderStatus ->
            orderStatus?.let { status ->
                mutableOrderStatus.value = orderUIMapper.toOrderStatusUI(status)
            }
        }.launchIn(viewModelScope)
    }
}