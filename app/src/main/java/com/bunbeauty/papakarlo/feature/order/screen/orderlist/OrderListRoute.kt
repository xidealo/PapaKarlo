package com.bunbeauty.papakarlo.feature.order.screen.orderlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.element.FoodDeliveryScaffold
import com.bunbeauty.papakarlo.common.ui.screen.EmptyScreen
import com.bunbeauty.papakarlo.common.ui.screen.ErrorScreen
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.feature.order.model.OrderItem
import com.bunbeauty.papakarlo.feature.order.ui.OrderItem
import com.bunbeauty.papakarlo.mapper.OrderItemMapper
import com.bunbeauty.shared.domain.model.order.OrderStatus
import com.bunbeauty.shared.presentation.order_list.OrderListState
import com.bunbeauty.shared.presentation.order_list.OrderListViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun OrderListState.DataState.mapState(): OrderListViewState {
    val orderItemMapper = koinInject<OrderItemMapper>()
    return OrderListViewState(
        state =
            when (state) {
                OrderListState.DataState.State.SUCCESS -> OrderListViewState.State.Success
                OrderListState.DataState.State.LOADING -> OrderListViewState.State.Loading
                OrderListState.DataState.State.ERROR -> OrderListViewState.State.Error
                OrderListState.DataState.State.EMPTY -> OrderListViewState.State.Empty
            },
        orderList = orderList.map(orderItemMapper::toItem),
    )
}

@Composable
fun OrderListRoute(
    viewModel: OrderListViewModel = koinViewModel(),
    back: () -> Unit,
    goToOrderDetails: (String) -> Unit,
) {
    val viewState by viewModel.dataState.collectAsStateWithLifecycle()

    val onAction =
        remember {
            { event: OrderListState.Action ->
                viewModel.onAction(event)
            }
        }

    LifecycleStartEffect(Unit) {
        viewModel.onAction(OrderListState.Action.StartObserveOrder)
        onStopOrDispose {
            viewModel.onAction(OrderListState.Action.StopObserveOrder)
        }
    }

    val effects by viewModel.events.collectAsStateWithLifecycle()

    val consumeEffects =
        remember {
            {
                viewModel.consumeEvents(effects)
            }
        }

    OrderListEffect(
        effectList = effects,
        goToOrderDetails = goToOrderDetails,
        consumeEffects = consumeEffects,
        back = back,
    )

    OrderListScreen(
        onAction = onAction,
        viewState = viewState.mapState(),
    )
}

@Composable
private fun OrderListScreen(
    onAction: (OrderListState.Action) -> Unit,
    viewState: OrderListViewState,
) {
    FoodDeliveryScaffold(
        title = stringResource(id = R.string.title_my_orders),
        backActionClick = { onAction(OrderListState.Action.BackClicked) },
    ) {
        when (viewState.state) {
            OrderListViewState.State.Success -> {
                OrderListScreenSuccess(
                    orderItemList = viewState.orderList,
                    onAction = onAction,
                )
            }

            OrderListViewState.State.Loading -> {
                LoadingScreen()
            }

            OrderListViewState.State.Error ->
                ErrorScreen(
                    mainTextId = R.string.error_order_list_loading,
                    onClick = {
                        onAction(OrderListState.Action.OnRefreshClicked)
                    },
                )

            OrderListViewState.State.Empty ->
                EmptyScreen(
                    imageId = R.drawable.ic_history,
                    imageDescriptionId = R.string.description_cafe_addresses_empty,
                    mainTextId = R.string.title_order_list_empty,
                    extraTextId = R.string.msg_order_list_empty,
                )
        }
    }
}

@Composable
private fun OrderListScreenSuccess(
    orderItemList: List<OrderItem>,
    onAction: (OrderListState.Action) -> Unit,
) {
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(FoodDeliveryTheme.colors.mainColors.background),
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(FoodDeliveryTheme.dimensions.mediumSpace),
            verticalArrangement = spacedBy(8.dp),
        ) {
            items(orderItemList) { orderItem ->
                OrderItem(
                    orderItem = orderItem,
                    onClick = {
                        onAction(OrderListState.Action.OnOrderClicked(orderItem.uuid))
                    },
                )
            }
        }
    }
}

@Composable
private fun OrderListEffect(
    effectList: List<OrderListState.Event>,
    goToOrderDetails: (String) -> Unit,
    consumeEffects: () -> Unit,
    back: () -> Unit,
) {
    LaunchedEffect(effectList) {
        effectList.forEach { event ->
            when (event) {
                is OrderListState.Event.OpenOrderDetailsEvent -> {
                    goToOrderDetails(event.orderUuid)
                }

                OrderListState.Event.GoBackEvent -> back()
            }
            consumeEffects()
        }
    }
}

val orderListViewStateMock =
    OrderListViewState(
        state = OrderListViewState.State.Loading,
        orderList = emptyList(),
    )

@Preview(showSystemUi = true)
@Composable
private fun OrderListScreenSuccessPreview() {
    FoodDeliveryTheme {
        OrderListScreen(
            viewState =
                orderListViewStateMock.copy(
                    state = OrderListViewState.State.Success,
                    orderList =
                        listOf(
                            OrderItem(
                                uuid = "",
                                status = OrderStatus.NOT_ACCEPTED,
                                code = "А-01",
                                dateTime = "18.03.2023 11:53",
                                statusName = OrderStatus.NOT_ACCEPTED.name,
                            ),
                            OrderItem(
                                uuid = "",
                                status = OrderStatus.ACCEPTED,
                                code = "Б-02",
                                dateTime = "18.03.2023 11:53",
                                statusName = OrderStatus.ACCEPTED.name,
                            ),
                            OrderItem(
                                uuid = "",
                                status = OrderStatus.PREPARING,
                                code = "В-03",
                                dateTime = "18.03.2023 11:53",
                                statusName = OrderStatus.PREPARING.name,
                            ),
                            OrderItem(
                                uuid = "",
                                status = OrderStatus.DONE,
                                code = "Г-04",
                                dateTime = "18.03.2023 11:53",
                                statusName = OrderStatus.DONE.name,
                            ),
                            OrderItem(
                                uuid = "",
                                status = OrderStatus.SENT_OUT,
                                code = "Д-05",
                                dateTime = "18.03.2023 11:53",
                                statusName = OrderStatus.SENT_OUT.name,
                            ),
                            OrderItem(
                                uuid = "",
                                status = OrderStatus.DELIVERED,
                                code = "Е-06",
                                dateTime = "18.03.2023 11:53",
                                statusName = OrderStatus.DELIVERED.name,
                            ),
                            OrderItem(
                                uuid = "",
                                status = OrderStatus.CANCELED,
                                code = "Ж-07",
                                dateTime = "18.03.2023 11:53",
                                statusName = OrderStatus.CANCELED.name,
                            ),
                        ),
                ),
            onAction = {},
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun OrderListScreenErrorPreview() {
    FoodDeliveryTheme {
        OrderListScreen(
            onAction = { },
            viewState =
                orderListViewStateMock.copy(
                    state = OrderListViewState.State.Error,
                ),
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun OrderListScreenLoadingPreview() {
    FoodDeliveryTheme {
        OrderListScreen(
            onAction = { },
            viewState =
                orderListViewStateMock.copy(
                    state = OrderListViewState.State.Loading,
                ),
        )
    }
}
