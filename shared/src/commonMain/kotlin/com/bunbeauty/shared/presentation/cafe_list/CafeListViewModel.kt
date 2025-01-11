package com.bunbeauty.shared.presentation.cafe_list

import com.bunbeauty.shared.Constants.RUBLE_CURRENCY
import com.bunbeauty.shared.Constants.WORKING_HOURS_DIVIDER
import com.bunbeauty.shared.domain.feature.cafe.ObserveCafeWithOpenStateListUseCase
import com.bunbeauty.shared.domain.feature.cart.ObserveCartUseCase
import com.bunbeauty.shared.domain.interactor.cafe.ICafeInteractor
import com.bunbeauty.shared.domain.model.cafe.CafeWithOpenState
import com.bunbeauty.shared.extension.launchSafe
import com.bunbeauty.shared.presentation.base.SharedStateViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest

class CafeListViewModel(
    private val cafeInteractor: ICafeInteractor,
    private val observeCafeWithOpenStateListUseCase: ObserveCafeWithOpenStateListUseCase,
    private val observeCartUseCase: ObserveCartUseCase
) : SharedStateViewModel<CafeList.DataState, CafeList.Action, CafeList.Event>(
    initDataState = CafeList.DataState(
        cafeList = listOf(),
        cartCostAndCount = null,
        isLoading = true
    )
) {

    private var observeCafeListJob: Job? = null
    private var observeCartJob: Job? = null

    override fun reduce(action: CafeList.Action, dataState: CafeList.DataState) {
        when (action) {
            CafeList.Action.Init -> {
                observeCart()
                observeCafeList()
            }

            is CafeList.Action.OnCafeClicked -> addEvent {
                CafeList.Event.OpenCafeOptionsBottomSheet(uuid = action.cafeUuid)
            }

            CafeList.Action.OnCartClicked -> {
                addEvent {
                    CafeList.Event.OpenConsumerCartProduct
                }
            }

            CafeList.Action.OnRefreshClicked -> {
                observeCafeList()
            }
        }
    }

    private fun observeCart() {
        observeCartJob?.cancel()
        observeCartJob = sharedScope.launchSafe(
            block = {
                observeCartUseCase().collectLatest { cartTotalAndCount ->
                    val cartTotalWithCurrency = cartTotalAndCount.copy(
                        cost = "${cartTotalAndCount.cost} $RUBLE_CURRENCY"
                    )
                    setState {
                        copy(
                            cartCostAndCount = cartTotalWithCurrency
                        )
                    }
                }
            },
            onError = {
            }
        )
    }

    private fun observeCafeList() {
        observeCafeListJob?.cancel()
        observeCafeListJob = sharedScope.launchSafe(
            block = {
                observeCafeWithOpenStateListUseCase()
                    .collectLatest { cafeWithOpenStateList ->
                        setState {
                            copy(
                                cafeList = cafeWithOpenStateList.mapIndexed { index, cafeWithOpenState ->
                                    cafeWithOpenState.toCafeItem(
                                        isLast = index == cafeWithOpenStateList.lastIndex
                                    )
                                },
                                isLoading = false
                            )
                        }
                    }
            },
            onError = {
            }
        )
    }

    private fun CafeWithOpenState.toCafeItem(isLast: Boolean): CafeItem {
        val fromTime = cafeInteractor.getCafeTime(cafe.fromTime)
        val toTime = cafeInteractor.getCafeTime(cafe.toTime)

        return CafeItem(
            uuid = cafe.uuid,
            address = cafe.address,
            phone = cafe.phone,
            workingHours = "$fromTime$WORKING_HOURS_DIVIDER$toTime",
            cafeOpenState = openState,
            isLast = isLast
        )
    }
}
