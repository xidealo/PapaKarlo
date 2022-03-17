package com.bunbeauty.papakarlo.feature.profile.order.order_list

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.common.state.State
import com.bunbeauty.papakarlo.compose.element.CircularProgressBar
import com.bunbeauty.papakarlo.compose.item.OrderItem
import com.bunbeauty.papakarlo.compose.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.databinding.FragmentOrderListBinding
import com.bunbeauty.papakarlo.extensions.compose
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderListFragment : BaseFragment(R.layout.fragment_order_list) {

    override val viewModel: OrderListViewModel by viewModel()
    override val viewBinding by viewBinding(FragmentOrderListBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.fragmentOrderListCvMain.compose {
            val orderListState by viewModel.orderListState.collectAsState()
            OrderListScreen(orderListState)
        }
    }

    @Composable
    private fun OrderListScreen(orderListState: State<List<OrderItemModel>>) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(FoodDeliveryTheme.colors.background)
        ) {
            when (orderListState) {
                is State.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(FoodDeliveryTheme.dimensions.mediumSpace)
                    ) {
                        itemsIndexed(orderListState.data) { i, orderItemModel ->
                            OrderItem(
                                modifier = Modifier.padding(
                                    top = FoodDeliveryTheme.dimensions.getItemSpaceByIndex(i)
                                ),
                                orderItem = orderItemModel
                            ) {
                                viewModel.onOrderClicked(orderItemModel)
                            }
                        }
                    }
                }
                is State.Empty -> {
                    OrderListScreenEmpty()
                }
                is State.Loading -> {
                    CircularProgressBar(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }

    @Composable
    private fun OrderListScreenEmpty() {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.empty_page),
                    contentDescription = stringResource(R.string.description_empty_profile)
                )
                Text(
                    modifier = Modifier.padding(FoodDeliveryTheme.dimensions.mediumSpace),
                    text = stringResource(R.string.msg_order_list_empty),
                    textAlign = TextAlign.Center,
                    style = FoodDeliveryTheme.typography.body1
                )
            }
        }
    }

    @Preview
    @Composable
    private fun OrderListScreenSuccessPreview() {
        OrderListScreen(
            State.Success(
                listOf(
                    OrderItemModel(
                        uuid = "",
                        status = OrderStatus.NOT_ACCEPTED,
                        statusName = "Обрабатывется",
                        statusColorId = 0,
                        code = "О-01",
                        dateTime = "30 января 12:59"
                    ),
                    OrderItemModel(
                        uuid = "",
                        status = OrderStatus.ACCEPTED,
                        statusName = "Принят",
                        statusColorId = 0,
                        code = "П-02",
                        dateTime = "29 января 12:59"
                    ),
                    OrderItemModel(
                        uuid = "",
                        status = OrderStatus.PREPARING,
                        statusName = "Готовится",
                        statusColorId = 0,
                        code = "Г-03",
                        dateTime = "28 января 12:59"
                    ),
                    OrderItemModel(
                        uuid = "",
                        status = OrderStatus.DONE,
                        statusName = "Готов",
                        statusColorId = 0,
                        code = "Г-04",
                        dateTime = "27 января 12:59"
                    ),
                    OrderItemModel(
                        uuid = "",
                        status = OrderStatus.SENT_OUT,
                        statusName = "В пути",
                        statusColorId = 0,
                        code = "В-05",
                        dateTime = "26 января 12:59"
                    ),
                    OrderItemModel(
                        uuid = "",
                        status = OrderStatus.DELIVERED,
                        statusName = "Выдан",
                        statusColorId = 0,
                        code = "В-06",
                        dateTime = "25 января 12:59"
                    ),
                    OrderItemModel(
                        uuid = "",
                        status = OrderStatus.CANCELED,
                        statusName = "Отменен",
                        statusColorId = 0,
                        code = "О-07",
                        dateTime = "24 января 12:59"
                    ),
                )
            )
        )
    }

    @Preview
    @Composable
    private fun OrderListScreenEmptyPreview() {
        OrderListScreen(State.Empty())
    }

    @Preview
    @Composable
    private fun OrderListScreenLoadingPreview() {
        OrderListScreen(State.Loading())
    }
}