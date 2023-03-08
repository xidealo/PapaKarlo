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
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragmentWithSharedViewModel
import com.bunbeauty.papakarlo.common.ui.screen.EmptyScreen
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.databinding.FragmentOrderListBinding
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import com.bunbeauty.papakarlo.feature.order.screen.order_list.OrderListFragmentDirections.toOrderDetailsFragment
import com.bunbeauty.papakarlo.feature.order.ui.OrderItem
import com.bunbeauty.papakarlo.mapper.OrderItemMapper
import com.bunbeauty.shared.domain.model.date_time.Date
import com.bunbeauty.shared.domain.model.date_time.DateTime
import com.bunbeauty.shared.domain.model.date_time.Time
import com.bunbeauty.shared.domain.model.order.LightOrder
import com.bunbeauty.shared.domain.model.order.OrderStatus
import com.bunbeauty.shared.presentation.order_list.OrderListState
import com.bunbeauty.shared.presentation.order_list.OrderListViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderListFragment : BaseFragmentWithSharedViewModel(R.layout.fragment_order_list) {

    override val viewBinding by viewBinding(FragmentOrderListBinding::bind)
    private val viewModel: OrderListViewModel by viewModel()

    private val orderItemMapper: OrderItemMapper by inject()

    @OptIn(ExperimentalLifecycleComposeApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.fragmentOrderListCvMain.setContentWithTheme {
            val orderListState by viewModel.orderListState.collectAsStateWithLifecycle()
            OrderListScreen(orderListState)
            LaunchedEffect(orderListState.eventList) {
                handleEventList(orderListState.eventList)
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
    private fun OrderListScreen(orderListState: OrderListState) {
        when (orderListState.state) {
            OrderListState.State.SUCCESS -> {
                OrderListScreenSuccess(orderListState.orderList)
            }
            OrderListState.State.EMPTY -> {
                EmptyScreen(
                    imageId = R.drawable.empty_orders,
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

    @Composable
    private fun OrderListScreenSuccess(orderItemList: List<LightOrder>) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(FoodDeliveryTheme.colors.background)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(FoodDeliveryTheme.dimensions.mediumSpace)
            ) {
                itemsIndexed(orderItemList) { i, lightOrder ->
                    OrderItem(
                        modifier = Modifier.padding(
                            top = FoodDeliveryTheme.dimensions.getItemSpaceByIndex(i)
                        ),
                        orderItem = orderItemMapper.toItem(lightOrder)
                    ) {
                        viewModel.onOrderClicked(lightOrder)
                    }
                }
            }
        }
    }

    private fun handleEventList(eventList: List<OrderListState.Event>) {
        eventList.forEach { event ->
            when (event) {
                is OrderListState.OpenOrderDetailsEvent -> {
                    findNavController().navigate(
                        toOrderDetailsFragment(event.orderUuid, event.orderCode)
                    )
                }
            }
        }
        viewModel.consumeEvents(eventList)
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun OrderListScreenSuccessPreview() {
        OrderListScreen(
            OrderListState(
                orderList = listOf(
                    LightOrder(
                        uuid = "",
                        status = OrderStatus.NOT_ACCEPTED,
                        code = "А-01",
                        dateTime = DateTime(
                            Date(27, 1, 2022),
                            Time(10, 0)
                        )
                    ),
                    LightOrder(
                        uuid = "",
                        status = OrderStatus.ACCEPTED,
                        code = "Б-02",
                        dateTime = DateTime(
                            Date(2, 2, 2022),
                            Time(11, 5)
                        )
                    ),
                    LightOrder(
                        uuid = "",
                        status = OrderStatus.PREPARING,
                        code = "В-03",
                        dateTime = DateTime(
                            Date(10, 2, 2022),
                            Time(12, 59)
                        )
                    ),
                    LightOrder(
                        uuid = "",
                        status = OrderStatus.DONE,
                        code = "Г-04",
                        dateTime = DateTime(
                            Date(10, 2, 2022),
                            Time(13, 0)
                        )
                    ),
                    LightOrder(
                        uuid = "",
                        status = OrderStatus.SENT_OUT,
                        code = "Д-05",
                        dateTime = DateTime(
                            Date(11, 2, 2022),
                            Time(14, 30)
                        )
                    ),
                    LightOrder(
                        uuid = "",
                        status = OrderStatus.DELIVERED,
                        code = "Е-06",
                        dateTime = DateTime(
                            Date(11, 2, 2022),
                            Time(15, 35)
                        )
                    ),
                    LightOrder(
                        uuid = "",
                        status = OrderStatus.CANCELED,
                        code = "Ж-07",
                        dateTime = DateTime(
                            Date(1, 3, 2022),
                            Time(0, 0)
                        )
                    )
                ),
                state = OrderListState.State.SUCCESS
            )
        )
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun OrderListScreenEmptyPreview() {
        OrderListScreen(OrderListState(state = OrderListState.State.EMPTY))
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun OrderListScreenLoadingPreview() {
        OrderListScreen(OrderListState(state = OrderListState.State.LOADING))
    }
}
