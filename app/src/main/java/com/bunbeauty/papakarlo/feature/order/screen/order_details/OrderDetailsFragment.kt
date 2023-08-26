package com.bunbeauty.papakarlo.feature.order.screen.order_details

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragmentWithSharedViewModel
import com.bunbeauty.papakarlo.common.delegates.argument
import com.bunbeauty.papakarlo.common.ui.element.FoodDeliveryScaffold
import com.bunbeauty.papakarlo.common.ui.element.card.FoodDeliveryCard
import com.bunbeauty.papakarlo.common.ui.element.surface.FoodDeliverySurface
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.bold
import com.bunbeauty.papakarlo.common.ui.theme.medium
import com.bunbeauty.papakarlo.databinding.LayoutComposeBinding
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import com.bunbeauty.papakarlo.feature.order.ui.OrderProductItem
import com.bunbeauty.papakarlo.feature.order.ui.OrderStatusBar
import com.bunbeauty.shared.domain.model.order.OrderStatus
import com.bunbeauty.shared.presentation.order_details.OrderDetailsViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderDetailsFragment : BaseFragmentWithSharedViewModel(R.layout.layout_compose) {

    private val viewModel: OrderDetailsViewModel by viewModel()
    override val viewBinding by viewBinding(LayoutComposeBinding::bind)

    private val orderDetailsUiStateMapper: OrderDetailsUiStateMapper by inject()

    private val orderUuid: String by argument()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.root.setContentWithTheme {
            val orderState by viewModel.orderState.collectAsStateWithLifecycle()
            OrderDetailsScreen(
                orderDetailsUi = orderDetailsUiStateMapper.map(orderState)
            )
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadOrder(orderUuid)
    }

    override fun onStop() {
        viewModel.stopObserveOrders()
        super.onStop()
    }

    @Composable
    private fun OrderDetailsScreen(orderDetailsUi: OrderDetailsUi) {
        FoodDeliveryScaffold(
            title = orderDetailsUi.code,
            backActionClick = {
                findNavController().popBackStack()
            }
        ) {
            if (orderDetailsUi.isLoading) {
                LoadingScreen()
            } else {
                OrderDetailsSuccessScreen(orderDetailsUi)
            }
        }
    }

    @Composable
    private fun OrderDetailsSuccessScreen(state: OrderDetailsUi) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(FoodDeliveryTheme.dimensions.screenContentSpace)
                ) {
                    state.orderInfo?.let { orderInfo ->
                        item {
                            Column {
                                OrderStatusBar(
                                    orderStatus = orderInfo.status,
                                    orderStatusName = orderInfo.statusName
                                )
                                OrderInfoCard(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp),
                                    orderInfo = orderInfo
                                )
                            }
                        }
                    }

                    items(state.orderProductItemList) { orderProductItem ->
                        OrderProductItem(
                            modifier = Modifier.padding(top = 8.dp),
                            orderProductItem = orderProductItem
                        )
                    }
                }
            }
            BottomAmountBar(state)
        }
    }

    @Composable
    private fun OrderInfoTextColumn(
        modifier: Modifier = Modifier,
        hint: String,
        info: String,
    ) {
        Column(modifier = modifier) {
            Text(
                text = hint,
                style = FoodDeliveryTheme.typography.labelSmall.medium,
                color = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant
            )
            Text(
                text = info,
                style = FoodDeliveryTheme.typography.bodyMedium,
                color = FoodDeliveryTheme.colors.mainColors.onSurface
            )
        }
    }

    @Composable
    private fun OrderInfoCard(
        modifier: Modifier = Modifier,
        orderInfo: OrderDetailsUi.OrderInfo,
    ) {
        FoodDeliveryCard(
            modifier = modifier,
            clickable = false
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OrderInfoTextColumn(
                        modifier = Modifier
                            .weight(1f),
                        hint = stringResource(R.string.msg_order_details_date_time),
                        info = orderInfo.dateTime,
                    )
                    orderInfo.deferredTime?.let { deferredTime ->
                        OrderInfoTextColumn(
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .weight(1f),
                            hint = stringResource(id = orderInfo.deferredTimeHintId),
                            info = deferredTime,
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    OrderInfoTextColumn(
                        modifier = Modifier
                            .weight(1f),
                        hint = stringResource(R.string.msg_order_details_pickup_method),
                        info = orderInfo.pickupMethod,
                    )
                    orderInfo.paymentMethod?.let { paymentMethod ->
                        OrderInfoTextColumn(
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .weight(1f),
                            hint = stringResource(R.string.msg_order_details_payment_method),
                            info = paymentMethod,
                        )
                    }
                }
                OrderInfoTextColumn(
                    modifier = Modifier
                        .padding(top = 8.dp),
                    hint = stringResource(R.string.msg_order_details_address),
                    info = orderInfo.address,
                )
                orderInfo.comment?.let { comment ->
                    OrderInfoTextColumn(
                        modifier = Modifier
                            .padding(top = 8.dp),
                        hint = stringResource(R.string.msg_order_details_comment),
                        info = comment,
                    )
                }
            }
        }
    }

    @Composable
    private fun BottomAmountBar(orderDetailsUi: OrderDetailsUi) {
        FoodDeliverySurface(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(FoodDeliveryTheme.colors.mainColors.surface)
                    .padding(FoodDeliveryTheme.dimensions.mediumSpace)
            ) {
                orderDetailsUi.deliveryCost?.let { deliveryCost ->
                    Row(modifier = Modifier.padding(bottom = 8.dp)) {
                        Text(
                            text = stringResource(R.string.msg_order_details_delivery_cost),
                            style = FoodDeliveryTheme.typography.bodyMedium,
                            color = FoodDeliveryTheme.colors.mainColors.onSurface
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            modifier = Modifier.weight(1f),
                            text = deliveryCost,
                            style = FoodDeliveryTheme.typography.bodyMedium,
                            color = FoodDeliveryTheme.colors.mainColors.onSurface,
                            textAlign = TextAlign.End
                        )
                    }
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(R.string.msg_order_details_order_cost),
                        style = FoodDeliveryTheme.typography.bodyMedium.bold,
                        color = FoodDeliveryTheme.colors.mainColors.onSurface
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    orderDetailsUi.totalCost?.let { totalCost ->
                        Text(
                            modifier = Modifier
                                .padding(end = FoodDeliveryTheme.dimensions.smallSpace),
                            text = totalCost,
                            style = FoodDeliveryTheme.typography.bodyMedium.bold,
                            color = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                            textDecoration = TextDecoration.LineThrough
                        )
                    }
                    orderDetailsUi.finalCost?.let { finalCost ->
                        Text(
                            text = finalCost,
                            style = FoodDeliveryTheme.typography.bodyMedium.bold,
                            color = FoodDeliveryTheme.colors.mainColors.onSurface,
                        )
                    }
                }
            }
        }
    }

    @Preview
    @Composable
    private fun OrderInfoCardPreview() {
        FoodDeliveryTheme {
            OrderInfoCard(orderInfo = getOrderInfo())
        }
    }

    @Preview
    @Composable
    private fun OrderInfoCardWithoutDeferredTimeAndCommentPreview() {
        FoodDeliveryTheme {
            OrderInfoCard(
                orderInfo = getOrderInfo().copy(
                    deferredTime = null,
                    comment = null
                )
            )
        }
    }

    @Preview
    @Composable
    private fun BottomAmountBarPreview() {
        FoodDeliveryTheme {
            BottomAmountBar(orderDetailsUi = getOrderDetails())
        }
    }

    @Preview
    @Composable
    private fun BottomAmountBarWithoutDeliveryPreview() {
        FoodDeliveryTheme {
            BottomAmountBar(
                orderDetailsUi = getOrderDetails().copy(
                    deliveryCost = null,
                    totalCost = null
                )
            )
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun OrderDetailsSuccessScreenPreview() {
        FoodDeliveryTheme {
            OrderDetailsScreen(orderDetailsUi = getOrderDetails())
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun OrderDetailsLoadingScreenPreview() {
        FoodDeliveryTheme {
            OrderDetailsScreen(
                orderDetailsUi = getOrderDetails().copy(
                    isLoading = true
                )
            )
        }
    }

    private fun getOrderDetails(): OrderDetailsUi {
        return OrderDetailsUi(
            orderProductItemList = listOf(
                OrderProductUiItem(
                    uuid = "",
                    name = "Product 1",
                    newPrice = "100",
                    oldPrice = "150",
                    newCost = "200",
                    oldCost = "300",
                    photoLink = "",
                    count = "2"
                ),
                OrderProductUiItem(
                    uuid = "",
                    name = "Product 2",
                    newPrice = "150",
                    oldPrice = null,
                    newCost = "150",
                    oldCost = null,
                    photoLink = "",
                    count = "1"
                ),
            ),
            orderInfo = getOrderInfo(),
            totalCost = "450",
            deliveryCost = "100",
            finalCost = "550",
            isLoading = false,
            code = "A-40",
        )
    }

    private fun getOrderInfo(): OrderDetailsUi.OrderInfo {
        return OrderDetailsUi.OrderInfo(
            status = OrderStatus.PREPARING,
            dateTime = "19.03.2023",
            deferredTime = "10:30",
            address =
            "" +
                "ул. Лука" +
                "2" +
                "10" +
                "1" +
                "3" +
                "тест",
            comment = "давай кушать",
            pickupMethod = "доставка",
            statusName = "Готовится",
            deferredTimeHintId = R.string.pickup_time,
            paymentMethod = "Наличными"
        )
    }
}
