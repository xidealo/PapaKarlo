package com.bunbeauty.shared.presentation.consumercart

import com.bunbeauty.shared.Constants.PERCENT
import com.bunbeauty.shared.Constants.RUBLE_CURRENCY
import com.bunbeauty.shared.Logger
import com.bunbeauty.shared.domain.feature.cart.AddCartProductUseCase
import com.bunbeauty.shared.domain.feature.cart.RemoveCartProductUseCase
import com.bunbeauty.shared.domain.interactor.cart.ICartProductInteractor
import com.bunbeauty.shared.domain.interactor.user.IUserInteractor
import com.bunbeauty.shared.domain.model.cart.ConsumerCart
import com.bunbeauty.shared.domain.model.cart.LightCartProduct
import com.bunbeauty.shared.extension.launchSafe
import com.bunbeauty.shared.extension.mapToStateFlow
import com.bunbeauty.shared.presentation.base.SharedViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class ConsumerCartViewModel(
    private val userInteractor: IUserInteractor,
    private val cartProductInteractor: ICartProductInteractor,
    private val addCartProductUseCase: AddCartProductUseCase,
    private val removeCartProductUseCase: RemoveCartProductUseCase
) : SharedViewModel() {

    private val consumerCartDataState = MutableStateFlow(
        ConsumerCartDataState()
    )
    val consumerCartState = consumerCartDataState.mapToStateFlow(sharedScope) { dataState ->
        mapState(dataState)
    }

    private var observeConsumerCartJob: Job? = null

    fun getConsumerCart() {
        observeConsumerCartJob?.cancel()
        observeConsumerCartJob =
            cartProductInteractor.observeConsumerCart().onEach { consumerCart ->
                Logger.logD("getConsumerCart", "getConsumerCart $consumerCart")
                consumerCartDataState.update { dataState ->
                    if (consumerCart == null) {
                        dataState.copy(state = ConsumerCartDataState.State.ERROR)
                    } else {
                        dataState.copy(
                            state = getConsumerCartDataState(consumerCart),
                            consumerCartData = getConsumerCartData(
                                consumerCart = consumerCart
                            )
                        )
                    }
                }
            }.launchIn(sharedScope)
    }

    fun onMenuClicked() {
        consumerCartDataState.update { dataState ->
            dataState + ConsumerCartEvent.NavigateToMenuEvent
        }
    }

    fun onCreateOrderClicked() {
        sharedScope.launchSafe(
            block = {
                val navigateEvent = if (userInteractor.isUserAuthorize()) {
                    ConsumerCartEvent.NavigateToCreateOrderEvent
                } else {
                    ConsumerCartEvent.NavigateToLoginEvent
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
        sharedScope.launchSafe(
            block = {
                addCartProductUseCase(menuProductUuid)
            },
            onError = {
                // TODO handle error
            }
        )
    }

    fun onRemoveCardProductClicked(menuProductUuid: String) {
        sharedScope.launchSafe(
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
                        consumerCartState = ConsumerCartUIState.ConsumerCartState.Success(
                            data = dataState.consumerCartData
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

    private fun getConsumerCartData(
        consumerCart: ConsumerCart
    ): ConsumerCartData? {
        return when (consumerCart) {
            is ConsumerCart.Empty -> null
            is ConsumerCart.WithProducts -> ConsumerCartData(
                forFreeDelivery = "${consumerCart.forFreeDelivery} $RUBLE_CURRENCY",
                cartProductList = consumerCart.cartProductList.map(::toItem),
                oldTotalCost = consumerCart.oldTotalCost?.let { oldTotalCost ->
                    oldTotalCost.toString() + RUBLE_CURRENCY
                },
                newTotalCost = consumerCart.newTotalCost.toString() + RUBLE_CURRENCY,
                firstOrderDiscount = consumerCart.discount?.let { discount ->
                    discount.toString() + PERCENT
                }
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
            newCost = lightCartProduct.newCost.toString() + RUBLE_CURRENCY,
            oldCost = lightCartProduct.oldCost?.let { oldCost -> oldCost.toString() + RUBLE_CURRENCY },
            photoLink = lightCartProduct.photoLink,
            count = lightCartProduct.count,
            menuProductUuid = lightCartProduct.menuProductUuid
        )
    }
}
