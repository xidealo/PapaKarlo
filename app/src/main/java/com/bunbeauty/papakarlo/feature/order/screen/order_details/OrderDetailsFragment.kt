package com.bunbeauty.papakarlo.feature.order.screen.order_details

import android.annotation.SuppressLint
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
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragmentWithSharedViewModel
import com.bunbeauty.papakarlo.common.delegates.argument
import com.bunbeauty.papakarlo.common.ui.element.BlurLine
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.mediumRoundedCornerShape
import com.bunbeauty.papakarlo.databinding.FragmentOrderDetailsBinding
import com.bunbeauty.papakarlo.extensions.compose
import com.bunbeauty.papakarlo.feature.order.ui.OrderProductItem
import com.bunbeauty.papakarlo.feature.order.ui.OrderStatusBar
import com.bunbeauty.papakarlo.util.string.IStringUtil
import com.bunbeauty.shared.domain.model.date_time.Date
import com.bunbeauty.shared.domain.model.date_time.DateTime
import com.bunbeauty.shared.domain.model.date_time.Time
import com.bunbeauty.shared.domain.model.order.OrderAddress
import com.bunbeauty.shared.domain.model.order.OrderStatus
import com.bunbeauty.shared.presentation.create_order.model.TimeUI
import com.bunbeauty.shared.presentation.order_details.OrderDetailsState
import com.bunbeauty.shared.presentation.order_details.OrderDetailsViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderDetailsFragment : BaseFragmentWithSharedViewModel(R.layout.fragment_order_details) {

    private val viewModel: OrderDetailsViewModel by viewModel()
    override val viewBinding by viewBinding(FragmentOrderDetailsBinding::bind)

    val stringUtil: IStringUtil by inject()

    val orderProductItemMapper: OrderProductItemMapper by inject()

    val orderUuid: String by argument()

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.fragmentOrderDetailsCvMain.compose {
            val orderState by viewModel.orderState.collectAsState()
            OrderDetailsScreen(orderState)
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
    private fun OrderDetailsScreen(orderDetailsState: OrderDetailsState) {
        if (orderDetailsState.isLoading) {
            LoadingScreen()
        } else {
            OrderDetailsSuccessScreen(orderDetailsState)
        }
    }

    @Composable
    private fun OrderDetailsSuccessScreen(state: OrderDetailsState) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(FoodDeliveryTheme.dimensions.mediumSpace)
                ) {
                    state.orderInfo?.let { orderInfo ->
                        item {
                            Column {
                                OrderStatusBar(
                                    orderStatus = orderInfo.status,
                                    orderStatusName = stringUtil.getOrderStatusName(orderInfo.status)
                                )
                                OrderInfoCard(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = FoodDeliveryTheme.dimensions.mediumSpace),
                                    orderInfo = orderInfo
                                )
                            }
                        }
                    }

                    items(state.orderProductItemList) { orderProductItem ->
                        OrderProductItem(
                            modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                            orderProductItem = orderProductItemMapper.toItem(orderProductItem)
                        )
                    }
                }
                BlurLine(modifier = Modifier.align(BottomCenter))
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
                style = FoodDeliveryTheme.typography.body2,
                color = FoodDeliveryTheme.colors.onSurfaceVariant
            )
            Text(
                modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.verySmallSpace),
                text = info,
                style = FoodDeliveryTheme.typography.body1,
                color = FoodDeliveryTheme.colors.onSurface
            )
        }
    }

    @Composable
    private fun OrderInfoCard(
        modifier: Modifier = Modifier,
        orderInfo: OrderDetailsState.OrderInfo
    ) {
        Card(
            modifier = modifier,
            colors = FoodDeliveryTheme.colors.cardColors(),
            elevation = FoodDeliveryTheme.dimensions.cardEvaluation(true),
            shape = mediumRoundedCornerShape,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(FoodDeliveryTheme.dimensions.mediumSpace)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OrderInfoTextColumn(
                        modifier = Modifier
                            .weight(1f),
                        hint = stringResource(R.string.msg_order_details_date_time),
                        info = stringUtil.getDateTimeString(orderInfo.dateTime),
                    )
                    orderInfo.deferredTime?.let { deferredTime ->
                        val deferredTimeHintId = if (orderInfo.isDelivery) {
                            R.string.delivery_time
                        } else {
                            R.string.pickup_time
                        }
                        OrderInfoTextColumn(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = FoodDeliveryTheme.dimensions.smallSpace),
                            hint = stringResource(deferredTimeHintId),
                            info = stringUtil.getTimeString(deferredTime),
                        )
                    }
                }
                OrderInfoTextColumn(
                    modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.mediumSpace),
                    hint = stringResource(R.string.msg_order_details_pickup_method),
                    info = stringUtil.getPickupMethodString(orderInfo.isDelivery),
                )
                OrderInfoTextColumn(
                    modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.mediumSpace),
                    hint = stringResource(R.string.msg_order_details_address),
                    info = stringUtil.getOrderAddressString(orderInfo.address),
                )
                orderInfo.comment?.let { comment ->
                    OrderInfoTextColumn(
                        modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.mediumSpace),
                        hint = stringResource(R.string.msg_order_details_comment),
                        info = comment,
                    )
                }
            }
        }
    }

    @Composable
    private fun BottomAmountBar(orderDetailsState: OrderDetailsState) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(FoodDeliveryTheme.colors.surface)
                .padding(FoodDeliveryTheme.dimensions.mediumSpace)
        ) {
            orderDetailsState.deliveryCost?.let { deliveryCost ->
                Row {
                    Text(
                        text = stringResource(R.string.msg_order_details_delivery_cost),
                        style = FoodDeliveryTheme.typography.body1,
                        color = FoodDeliveryTheme.colors.onSurface
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        modifier = Modifier.weight(1f),
                        text = stringUtil.getCostString(deliveryCost),
                        style = FoodDeliveryTheme.typography.body1,
                        color = FoodDeliveryTheme.colors.onSurface,
                        textAlign = TextAlign.End
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = FoodDeliveryTheme.dimensions.smallSpace)
            ) {
                Text(
                    text = stringResource(R.string.msg_order_details_order_cost),
                    style = FoodDeliveryTheme.typography.h2,
                    color = FoodDeliveryTheme.colors.onSurface
                )
                Spacer(modifier = Modifier.weight(1f))
                orderDetailsState.totalCost?.let { totalCost ->
                    Text(
                        modifier = Modifier
                            .padding(end = FoodDeliveryTheme.dimensions.smallSpace),
                        text = stringUtil.getCostString(totalCost),
                        style = FoodDeliveryTheme.typography.h2,
                        color = FoodDeliveryTheme.colors.onSurfaceVariant,
                        textDecoration = TextDecoration.LineThrough
                    )
                }
                orderDetailsState.finalCost?.let { finalCost ->
                    Text(
                        text = stringUtil.getCostString(finalCost),
                        style = FoodDeliveryTheme.typography.h2,
                        color = FoodDeliveryTheme.colors.onSurface,
                    )
                }
            }
        }
    }

    @Preview
    @Composable
    private fun OrderInfoTextColumnPreview() {
        OrderInfoTextColumn(
            hint = stringResource(R.string.msg_order_details_address),
            info = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж",
        )
    }

    @Preview
    @Composable
    private fun OrderInfoCardPreview() {
        OrderInfoCard(orderInfo = getOrderInfo())
    }

    @Preview
    @Composable
    private fun OrderInfoCardWithoutDeferredTimeAndCommentPreview() {
        OrderInfoCard(
            orderInfo = getOrderInfo().copy(
                deferredTime = null,
                comment = null
            )
        )
    }

    @Preview
    @Composable
    private fun BottomAmountBarPreview() {
        BottomAmountBar(orderDetailsState = getOrderDetails())
    }

    @Preview
    @Composable
    private fun BottomAmountBarWithoutDeliveryPreview() {
        BottomAmountBar(
            orderDetailsState = getOrderDetails().copy(
                deliveryCost = null,
                totalCost = null
            )
        )
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun OrderDetailsSuccessScreenPreview() {
        OrderDetailsScreen(orderDetailsState = getOrderDetails())
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun OrderDetailsLoadingScreenPreview() {
        OrderDetailsScreen(
            orderDetailsState = getOrderDetails().copy(
                isLoading = true
            )
        )
    }

    private fun getOrderDetails(): OrderDetailsState {
        return OrderDetailsState(
            orderProductItemList = listOf(
                OrderDetailsState.OrderProductItem(
                    uuid = "",
                    name = "Product 1",
                    newPrice = "100",
                    oldPrice = "150",
                    newCost = "200",
                    oldCost = "300",
                    photoLink = "",
                    count = "2"
                ),
                OrderDetailsState.OrderProductItem(
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
            isLoading = false
        )
    }

    private fun getOrderInfo(): OrderDetailsState.OrderInfo {
        return OrderDetailsState.OrderInfo(
            code = "A-40",
            status = OrderStatus.PREPARING,
            dateTime = DateTime(
                Date(1, 1, 2023),
                Time(1, 30)
            ),
            deferredTime = TimeUI.Time(10, 30),
            address = OrderAddress(
                description = "",
                street = "ул. Лука",
                house = "2",
                flat = "10",
                entrance = "1",
                floor = "3",
                comment = "тест",
            ),
            comment = "давай кушать",
            isDelivery = true,
        )
    }
}
