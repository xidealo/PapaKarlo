package com.bunbeauty.papakarlo.feature.order.screen.order_list

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragmentWithSharedViewModel
import com.bunbeauty.papakarlo.common.extension.navigateSafe
import com.bunbeauty.papakarlo.common.ui.element.FoodDeliveryScaffold
import com.bunbeauty.papakarlo.common.ui.screen.EmptyScreen
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.databinding.LayoutComposeBinding
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import com.bunbeauty.papakarlo.feature.order.model.OrderItem
import com.bunbeauty.papakarlo.feature.order.screen.order_list.OrderListFragmentDirections.toOrderDetailsFragment
import com.bunbeauty.papakarlo.feature.order.ui.OrderItem
import com.bunbeauty.papakarlo.mapper.OrderItemMapper
import com.bunbeauty.shared.domain.model.order.OrderStatus
import com.bunbeauty.shared.presentation.order_list.OrderListState
import com.bunbeauty.shared.presentation.order_list.OrderListViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderListFragment : BaseFragmentWithSharedViewModel(R.layout.layout_compose) {

    override val viewBinding by viewBinding(LayoutComposeBinding::bind)
    private val viewModel: OrderListViewModel by viewModel()

    private val orderItemMapper: OrderItemMapper by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.root.setContentWithTheme {
            val orderListState by viewModel.orderListState.collectAsStateWithLifecycle()
            OrderListScreen(
                orderListUi = OrderListUi(
                    orderList = orderListState.orderList.map(orderItemMapper::toItem),
                    state = orderListState.state,
                )
            )
            LaunchedEffect(orderListState.eventList) {
                handleEventList(eventList = orderListState.eventList)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.observeOrders()
    }

    override fun onStop() {
        viewModel.stopObserveOrders()
        super.onStop()
    }

    @Composable
    private fun OrderListScreen(orderListUi: OrderListUi) {
        FoodDeliveryScaffold(
            title = stringResource(id = R.string.title_my_orders),
            backActionClick = {
                findNavController().popBackStack()
            }
        ) {
            when (orderListUi.state) {
                OrderListState.State.SUCCESS -> {
                    OrderListScreenSuccess(orderListUi.orderList)
                }
                OrderListState.State.EMPTY -> {
                    EmptyScreen(
                        imageId = R.drawable.ic_history,
                        imageDescriptionId = R.string.description_cafe_addresses_empty,
                        mainTextId = R.string.title_order_list_empty,
                        extraTextId = R.string.msg_order_list_empty,
                    )
                }
                OrderListState.State.LOADING -> {
                    LoadingScreen()
                }
            }
        }
    }

    @Composable
    private fun OrderListScreenSuccess(orderItemList: List<OrderItem>) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(FoodDeliveryTheme.colors.mainColors.background)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(FoodDeliveryTheme.dimensions.mediumSpace)
            ) {
                itemsIndexed(orderItemList) { i, orderItem ->
                    OrderItem(
                        modifier = Modifier.padding(
                            top = FoodDeliveryTheme.dimensions.getItemSpaceByIndex(i)
                        ),
                        orderItem = orderItem
                    ) {
                        viewModel.onOrderClicked(orderItem.uuid, orderItem.code)
                    }
                }
            }
        }
    }

    private fun handleEventList(eventList: List<OrderListState.Event>) {
        eventList.forEach { event ->
            when (event) {
                is OrderListState.OpenOrderDetailsEvent -> {
                    findNavController().navigateSafe(
                        toOrderDetailsFragment(event.orderUuid, event.orderCode)
                    )
                }
            }
        }
        viewModel.consumeEventList(eventList)
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
                )
            )
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun OrderListScreenEmptyPreview() {
        FoodDeliveryTheme {
            OrderListScreen(OrderListUi(state = OrderListState.State.EMPTY))
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun OrderListScreenLoadingPreview() {
        FoodDeliveryTheme {
            OrderListScreen(OrderListUi(state = OrderListState.State.LOADING))
        }
    }
}
