package com.bunbeauty.papakarlo.feature.consumercart

import androidx.lifecycle.viewModelScope
import com.bunbeauty.papakarlo.common.model.SuccessLoginDirection
import com.bunbeauty.papakarlo.common.viewmodel.BaseViewModel
import com.bunbeauty.papakarlo.feature.consumercart.model.CartProductItem
import com.bunbeauty.shared.domain.feature.cart.AddCartProductUseCase
import com.bunbeauty.shared.domain.feature.cart.RemoveCartProductUseCase
import com.bunbeauty.shared.domain.feature.discount.GetDiscountUseCase
import com.bunbeauty.shared.domain.interactor.cart.ICartProductInteractor
import com.bunbeauty.shared.domain.interactor.user.IUserInteractor
import com.bunbeauty.shared.domain.model.cart.ConsumerCart
import com.bunbeauty.shared.domain.model.cart.LightCartProduct
import com.bunbeauty.shared.extension.launchSafe
import com.bunbeauty.shared.extension.mapToStateFlow
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class ConsumerCartViewModel(
    private val userInteractor: IUserInteractor,
    private val cartProductInteractor: ICartProductInteractor,
    private val addCartProductUseCase: AddCartProductUseCase,
    private val removeCartProductUseCase: RemoveCartProductUseCase,
) : BaseViewModel() {

    private val consumerCartDataState = MutableStateFlow(
        ConsumerCartDataState()
    )
    val consumerCartState = consumerCartDataState.mapToStateFlow(viewModelScope) { dataState ->
        mapState(dataState)
    }

    private var observeConsumerCartJob: Job? = null

    fun getConsumerCart() {
        viewModelScope.launchSafe(
            block = {
                observeConsumerCartJob?.cancel()
                observeConsumerCartJob =
                    cartProductInteractor.observeConsumerCart().onEach { consumerCart ->
                        consumerCartDataState.update { dataState ->
                            if (consumerCart == null) {
                                dataState.copy(state = ConsumerCartDataState.State.ERROR)
                            } else {
                                dataState.copy(
                                    state = getConsumerCartDataState(consumerCart),
                                    consumerCartData = getConsumerCartData(
                                        consumerCart = consumerCart,
                                    ),
                                )
                            }
                        }
                    }.launchIn(viewModelScope)
            },
            onError = {
                consumerCartDataState.update { dataState ->
                    dataState.copy(state = ConsumerCartDataState.State.ERROR)
                }
            }
        )
    }

    fun onMenuClicked() {
        consumerCartDataState.update { dataState ->
            dataState + ConsumerCartEvent.NavigateToMenuEvent
        }
    }

    fun onCreateOrderClicked() {
        viewModelScope.launchSafe(
            block = {
                val navigateEvent = if (userInteractor.isUserAuthorize()) {
                    ConsumerCartEvent.NavigateToCreateOrderEvent
                } else {
                    ConsumerCartEvent.NavigateToLoginEvent(SuccessLoginDirection.TO_CREATE_ORDER)
                }
                consumerCartDataState.update { dataState ->
                    dataState + navigateEvent
                }
            },
            onError = {
                // TODO handle error
            }
        )
    }

    fun onProductClicked(cartProductItem: CartProductItem) {
        consumerCartDataState.update { dataState ->
            dataState + ConsumerCartEvent.NavigateToProductEvent(cartProductItem)
        }
    }

    fun onAddCardProductClicked(menuProductUuid: String) {
        viewModelScope.launchSafe(
            block = {
                addCartProductUseCase(menuProductUuid)
            },
            onError = {
                // TODO handle error
            }
        )
    }

    fun onRemoveCardProductClicked(menuProductUuid: String) {
        viewModelScope.launchSafe(
            block = {
                removeCartProductUseCase(menuProductUuid)
            },
            onError = {
                // TODO handle error
            }
        )
    }

    fun consumeEventList(eventList: List<ConsumerCartEvent>) {
        consumerCartDataState.update { state ->
            state - eventList
        }
    }

    private fun mapState(dataState: ConsumerCartDataState): ConsumerCartUIState {
        return when (dataState.state) {
            ConsumerCartDataState.State.LOADING -> ConsumerCartUIState(
                consumerCartState = ConsumerCartUIState.ConsumerCartState.Loading,
                eventList = dataState.eventList
            )

            ConsumerCartDataState.State.SUCCESS -> {
                if (dataState.consumerCartData == null) {
                    ConsumerCartUIState(ConsumerCartUIState.ConsumerCartState.Error)
                } else {
                    ConsumerCartUIState(
                        consumerCartState = ConsumerCartUIState
                            .ConsumerCartState
                            .Success(
                                data = dataState.consumerCartData,
                            ),
                        eventList = dataState.eventList
                    )
                }
            }

            ConsumerCartDataState.State.EMPTY -> ConsumerCartUIState(
                consumerCartState = ConsumerCartUIState.ConsumerCartState.Empty,
                eventList = dataState.eventList
            )

            ConsumerCartDataState.State.ERROR -> ConsumerCartUIState(
                consumerCartState = ConsumerCartUIState.ConsumerCartState.Error,
                eventList = dataState.eventList
            )
        }
    }

    private suspend fun getConsumerCartData(
        consumerCart: ConsumerCart,
    ): ConsumerCartData? {
        return when (consumerCart) {
            is ConsumerCart.Empty -> null
            is ConsumerCart.WithProducts -> ConsumerCartData(
                forFreeDelivery = consumerCart.forFreeDelivery.toString(),
                cartProductList = consumerCart.cartProductList.map(::toItem),
                oldTotalCost = consumerCart.oldTotalCost?.toString(),
                newTotalCost = consumerCart.newTotalCost.toString(),
                firstOrderDiscount = consumerCart.discount
            )
        }
    }

    private fun getConsumerCartDataState(consumerCart: ConsumerCart): ConsumerCartDataState.State {
        return when (consumerCart) {
            is ConsumerCart.Empty -> ConsumerCartDataState.State.EMPTY
            is ConsumerCart.WithProducts -> ConsumerCartDataState.State.SUCCESS
        }
    }

    private fun toItem(lightCartProduct: LightCartProduct): CartProductItem {
        return CartProductItem(
            uuid = lightCartProduct.uuid,
            name = lightCartProduct.name,
            newCost = lightCartProduct.newCost.toString(),
            oldCost = lightCartProduct.oldCost?.toString(),
            photoLink = lightCartProduct.photoLink,
            count = lightCartProduct.count,
            menuProductUuid = lightCartProduct.menuProductUuid
        )
    }
}
