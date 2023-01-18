package com.bunbeauty.papakarlo.feature.order.screen.order_details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.bunbeauty.papakarlo.feature.order.model.OrderUI
import com.bunbeauty.papakarlo.feature.order.ui.OrderProductItem
import com.bunbeauty.papakarlo.feature.order.ui.OrderStatusBar
import com.bunbeauty.papakarlo.util.string.IStringUtil
import com.bunbeauty.shared.domain.model.order.OrderStatus
import com.bunbeauty.shared.presentation.order_details.OrderDetailsState
import com.bunbeauty.shared.presentation.order_details.OrderDetailsViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderDetailsFragment : BaseFragmentWithSharedViewModel(R.layout.fragment_order_details) {

    private val viewModel: OrderDetailsViewModel by viewModel()
    override val viewBinding by viewBinding(FragmentOrderDetailsBinding::bind)

    val stringUtil: IStringUtil by inject()

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
                    item {
//                        Column {
//                            OrderStatusBar(
//                                orderStatus = state.orderInfo.status,
//                                orderStatusName = stringUtil.getOrderStatusName(state.orderInfo.status)
//                            )
//                            OrderInfoCard(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .padding(top = FoodDeliveryTheme.dimensions.mediumSpace),
//                                orderInfo = state.orderInfo
//                            )
//                        }
                    }
                    items(state.orderDetailsList) { orderDetailsItem ->
//                        OrderProductItem(
//                            modifier = Modifier.padding(
//                                top = FoodDeliveryTheme.dimensions.getItemSpaceByIndex(i)
//                            ),
//                            orderProductItem = orderDetailsItem
//                        )
                    }
                }
                BlurLine(modifier = Modifier.align(BottomCenter))
            }
            //BottomAmountBar(orderUI)
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
    private fun BottomAmountBar(orderUI: OrderUI) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(FoodDeliveryTheme.colors.surface)
                .padding(FoodDeliveryTheme.dimensions.mediumSpace)
        ) {
            if (orderUI.isDelivery && orderUI.deliveryCost != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = FoodDeliveryTheme.dimensions.smallSpace)
                ) {
                    Text(
                        text = stringResource(R.string.msg_order_details_delivery_cost),
                        style = FoodDeliveryTheme.typography.body1,
                        color = FoodDeliveryTheme.colors.onSurface
                    )
                    Text(
                        modifier = Modifier.weight(1f),
                        text = orderUI.deliveryCost,
                        style = FoodDeliveryTheme.typography.body1,
                        color = FoodDeliveryTheme.colors.onSurface,
                        textAlign = TextAlign.End
                    )
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.msg_order_details_order_cost),
                    style = FoodDeliveryTheme.typography.h2,
                    color = FoodDeliveryTheme.colors.onSurface
                )
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.End
                ) {
                    orderUI.oldAmountToPay?.let {
                        Text(
                            modifier = Modifier
                                .padding(end = FoodDeliveryTheme.dimensions.smallSpace),
                            text = orderUI.oldAmountToPay,
                            style = FoodDeliveryTheme.typography.h2,
                            color = FoodDeliveryTheme.colors.onSurfaceVariant,
                            textDecoration = TextDecoration.LineThrough
                        )
                    }
                    Text(
                        text = orderUI.newAmountToPay,
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
        //OrderInfoCard(orderInfo = getOrderUI())
    }

    @Preview
    @Composable
    private fun OrderInfoCardWithoutDeferredTimeAndCommentPreview() {
        //OrderInfoCard(orderInfo = orderUIMinimal)
    }

    @Preview
    @Composable
    private fun BottomAmountBarPreview() {
        BottomAmountBar(orderUI = getOrderUI())
    }

    @Preview
    @Composable
    private fun BottomAmountBarWithoutDeliveryPreview() {
        BottomAmountBar(orderUI = orderUIMinimal)
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun OrderDetailsSuccessScreenPreview() {
        //OrderDetailsScreen(orderState = State.Success(getOrderUI()))
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun OrderDetailsLoadingScreenPreview() {
        //OrderDetailsScreen(orderState = State.Loading())
    }

    private fun getOrderUI(): OrderUI {
//        val orderProductItemModel = OrderProductItem(
//            uuid = "",
//            name = "Бэргер с вкусной свинкой ням ням ням ням",
//            newPrice = 50,
//            oldPrice = 100,
//            newCost = 100,
//            oldCost = 200,
//            photoLink = "",
//            count = 2
//        )
        return OrderUI(
            code = "",
            status = OrderStatus.PREPARING,
            statusName = "Готовится",
            dateTime = "20 февраля 18:15",
            pickupMethod = "Доставка",
            deferredTimeHintStringId = R.string.msg_order_details_deferred_time_delivery,
            deferredTime = "20:30",
            address = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж",
            comment = "Позвонить за 5 минут",
            deliveryCost = "100 ₽",
            orderProductList = listOf(),
            isDelivery = true,
            oldAmountToPay = "450 ₽",
            newAmountToPay = "390 ₽",
        )
    }

    private val orderUIMinimal = OrderUI(
        code = "",
        status = OrderStatus.PREPARING,
        statusName = "Готовится",
        dateTime = "20 февраля 18:15",
        pickupMethod = "Доставка",
        deferredTimeHintStringId = R.string.msg_order_details_deferred_time_delivery,
        deferredTime = null,
        address = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж",
        comment = null,
        deliveryCost = "100 ₽",
        orderProductList = listOf(),
        isDelivery = false,
        oldAmountToPay = "450 ₽",
        newAmountToPay = "390 ₽",
    )
}
