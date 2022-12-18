package com.bunbeauty.papakarlo.feature.order.screen.order_details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.os.bundleOf
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.common.state.State
import com.bunbeauty.papakarlo.common.ui.element.BlurLine
import com.bunbeauty.papakarlo.common.ui.screen.ErrorScreen
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.mediumRoundedCornerShape
import com.bunbeauty.papakarlo.databinding.FragmentOrderDetailsBinding
import com.bunbeauty.papakarlo.extensions.compose
import com.bunbeauty.papakarlo.feature.order.model.OrderProductItem
import com.bunbeauty.papakarlo.feature.order.model.OrderUI
import com.bunbeauty.papakarlo.feature.order.ui.OrderProductItem
import com.bunbeauty.papakarlo.feature.order.ui.OrderStatusBar
import com.bunbeauty.shared.domain.model.order.OrderStatus
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class OrderDetailsFragment : BaseFragment(R.layout.fragment_order_details) {

    override val viewModel: OrderDetailsViewModel by stateViewModel(state = {
        arguments ?: bundleOf()
    })
    override val viewBinding by viewBinding(FragmentOrderDetailsBinding::bind)

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.fragmentOrderDetailsCvMain.compose {
            val orderState by viewModel.orderState.collectAsState()
            OrderDetailsScreen(orderState)
        }
    }

    @Composable
    private fun OrderDetailsScreen(orderState: State<OrderUI>) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(FoodDeliveryTheme.colors.background),
        ) {
            when (orderState) {
                is State.Success -> OrderDetailsSuccessScreen(orderState.data)
                is State.Error -> ErrorScreen(orderState.message)
                else -> LoadingScreen()
            }
        }
    }

    @Composable
    private fun OrderDetailsSuccessScreen(orderUI: OrderUI) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
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
                        OrderStatusBar(
                            orderStatus = orderUI.status,
                            orderStatusName = orderUI.statusName
                        )
                    }
                    item {
                        OrderInfoCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = FoodDeliveryTheme.dimensions.mediumSpace),
                            orderUI = orderUI
                        )
                    }
                    itemsIndexed(orderUI.orderProductList) { i, orderProductItemModel ->
                        OrderProductItem(
                            modifier = Modifier.padding(
                                top = FoodDeliveryTheme.dimensions.getItemSpaceByIndex(i)
                            ),
                            orderProductItem = orderProductItemModel
                        )
                    }
                }
                BlurLine(modifier = Modifier.align(BottomCenter))
            }
            BottomAmountBar(orderUI)
        }
    }

    @Composable
    private fun OrderInfoTextColumn(
        modifier: Modifier = Modifier,
        @StringRes hintStringId: Int,
        info: String,
    ) {
        Column(modifier = modifier) {
            Text(
                text = stringResource(hintStringId),
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
        orderUI: OrderUI
    ) {
        Card(
            modifier = modifier
                .shadow(
                    elevation = FoodDeliveryTheme.dimensions.elevation,
                    shape = mediumRoundedCornerShape
                )
                .clip(mediumRoundedCornerShape),
            colors = FoodDeliveryTheme.colors.cardColors(),
            elevation = FoodDeliveryTheme.dimensions.cardEvaluation(),
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
                        hintStringId = R.string.msg_order_details_date_time,
                        info = orderUI.dateTime,
                    )
                    orderUI.deferredTime?.let { deferredTime ->
                        OrderInfoTextColumn(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = FoodDeliveryTheme.dimensions.smallSpace),
                            hintStringId = orderUI.deferredTimeHintStringId,
                            info = deferredTime,
                        )
                    }
                }
                OrderInfoTextColumn(
                    modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.mediumSpace),
                    hintStringId = R.string.msg_order_details_pickup_method,
                    info = orderUI.pickupMethod,
                )
                OrderInfoTextColumn(
                    modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.mediumSpace),
                    hintStringId = R.string.msg_order_details_address,
                    info = orderUI.address,
                )
                orderUI.comment?.let { comment ->
                    OrderInfoTextColumn(
                        modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.mediumSpace),
                        hintStringId = R.string.msg_order_details_comment,
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
            hintStringId = R.string.msg_order_details_address,
            info = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж",
        )
    }

    @Preview
    @Composable
    private fun OrderInfoCardPreview() {
        OrderInfoCard(orderUI = getOrderUI())
    }

    @Preview
    @Composable
    private fun OrderInfoCardWithoutDeferredTimeAndCommentPreview() {
        OrderInfoCard(orderUI = orderUIMinimal)
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
        OrderDetailsScreen(orderState = State.Success(getOrderUI()))
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun OrderDetailsLoadingScreenPreview() {
        OrderDetailsScreen(orderState = State.Loading())
    }

    private fun getOrderUI(): OrderUI {
        val orderProductItemModel = OrderProductItem(
            uuid = "",
            name = "Бэргер с вкусной свинкой ням ням ням ням",
            newPrice = "50 ₽",
            oldPrice = "100 ₽",
            newCost = "100 ₽",
            oldCost = "200 ₽",
            photoLink = "",
            count = "x 2"
        )
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
            orderProductList = listOf(
                orderProductItemModel,
                orderProductItemModel,
                orderProductItemModel,
                orderProductItemModel,
                orderProductItemModel,
                orderProductItemModel,
                orderProductItemModel,
            ),
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
