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

// TODO need refactoring
@Composable
fun OrderListRoute(
    viewModel: OrderListViewModel = koinViewModel(),
    back: () -> Unit,
    goToOrderDetails: (String) -> Unit
) {
    val orderItemMapper = koinInject<OrderItemMapper>()

    LifecycleStartEffect(Unit) {
        viewModel.observeOrders()
        onStopOrDispose {
            viewModel.stopObserveOrders()
        }
    }
    val viewState by viewModel.orderListState.collectAsStateWithLifecycle()

    val consumeEffects = remember {
        {
            viewModel.consumeEventList(viewState.eventList)
        }
    }

    OrderListEffect(
        effectList = viewState.eventList,
        goToOrderDetails = goToOrderDetails,
        consumeEffects = consumeEffects
    )

    OrderListScreen(
        viewState = OrderListUi(
            orderList = viewState.orderList.map(orderItemMapper::toItem),
            state = viewState.state
        ),
        back = back,
        onOrderClicked = viewModel::onOrderClicked
    )
}

@Composable
private fun OrderListScreen(
    viewState: OrderListUi,
    back: () -> Unit,
    onOrderClicked: (String) -> Unit
) {
    FoodDeliveryScaffold(
        title = stringResource(id = R.string.title_my_orders),
        backActionClick = back
    ) {
        when (viewState.state) {
            OrderListState.State.SUCCESS -> {
                OrderListScreenSuccess(
                    orderItemList = viewState.orderList,
                    onOrderClicked = onOrderClicked
                )
            }

            OrderListState.State.EMPTY -> {
                EmptyScreen(
                    imageId = R.drawable.ic_history,
                    imageDescriptionId = R.string.description_cafe_addresses_empty,
                    mainTextId = R.string.title_order_list_empty,
                    extraTextId = R.string.msg_order_list_empty
                )
            }

            OrderListState.State.LOADING -> {
                LoadingScreen()
            }
        }
    }
}

@Composable
private fun OrderListScreenSuccess(
    orderItemList: List<OrderItem>,
    onOrderClicked: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FoodDeliveryTheme.colors.mainColors.background)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(FoodDeliveryTheme.dimensions.mediumSpace),
            verticalArrangement = spacedBy(8.dp)
        ) {
            items(orderItemList) { orderItem ->
                OrderItem(
                    orderItem = orderItem,
                    onClick = {
                        onOrderClicked(orderItem.uuid)
                    }
                )
            }
        }
    }
}

@Composable
private fun OrderListEffect(
    effectList: List<OrderListState.Event>,
    goToOrderDetails: (String) -> Unit,
    consumeEffects: () -> Unit
) {
    LaunchedEffect(effectList) {
        effectList.forEach { event ->
            when (event) {
                is OrderListState.OpenOrderDetailsEvent -> {
                    goToOrderDetails(event.orderUuid)
                }
            }
        }
        consumeEffects()
    }
}

@Preview(showSystemUi = true)
@Composable
private fun OrderListScreenSuccessPreview() {
    FoodDeliveryTheme {
        OrderListScreen(
            OrderListUi(
                orderList = listOf(
                    OrderItem(
                        uuid = "",
                        status = OrderStatus.NOT_ACCEPTED,
                        code = "А-01",
                        dateTime = "18.03.2023 11:53",
                        statusName = OrderStatus.NOT_ACCEPTED.name
                    ),
                    OrderItem(
                        uuid = "",
                        status = OrderStatus.ACCEPTED,
                        code = "Б-02",
                        dateTime = "18.03.2023 11:53",
                        statusName = OrderStatus.ACCEPTED.name
                    ),
                    OrderItem(
                        uuid = "",
                        status = OrderStatus.PREPARING,
                        code = "В-03",
                        dateTime = "18.03.2023 11:53",
                        statusName = OrderStatus.PREPARING.name
                    ),
                    OrderItem(
                        uuid = "",
                        status = OrderStatus.DONE,
                        code = "Г-04",
                        dateTime = "18.03.2023 11:53",
                        statusName = OrderStatus.DONE.name
                    ),
                    OrderItem(
                        uuid = "",
                        status = OrderStatus.SENT_OUT,
                        code = "Д-05",
                        dateTime = "18.03.2023 11:53",
                        statusName = OrderStatus.SENT_OUT.name
                    ),
                    OrderItem(
                        uuid = "",
                        status = OrderStatus.DELIVERED,
                        code = "Е-06",
                        dateTime = "18.03.2023 11:53",
                        statusName = OrderStatus.DELIVERED.name
                    ),
                    OrderItem(
                        uuid = "",
                        status = OrderStatus.CANCELED,
                        code = "Ж-07",
                        dateTime = "18.03.2023 11:53",
                        statusName = OrderStatus.CANCELED.name
                    )
                ),
                state = OrderListState.State.SUCCESS
            ),
            back = {
            },
            onOrderClicked = {
            }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun OrderListScreenEmptyPreview() {
    FoodDeliveryTheme {
        OrderListScreen(
            OrderListUi(state = OrderListState.State.EMPTY),
            back = {
            },
            onOrderClicked = {
            }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun OrderListScreenLoadingPreview() {
    FoodDeliveryTheme {
        OrderListScreen(
            OrderListUi(state = OrderListState.State.LOADING),
            back = {
            },
            onOrderClicked = {
            }
        )
    }
}
