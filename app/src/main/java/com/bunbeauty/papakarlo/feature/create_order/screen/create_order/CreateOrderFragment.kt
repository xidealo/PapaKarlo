package com.bunbeauty.papakarlo.feature.create_order.screen.create_order

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.common.ui.element.BlurLine
import com.bunbeauty.papakarlo.common.ui.element.LoadingButton
import com.bunbeauty.papakarlo.common.ui.element.card.NavigationCard
import com.bunbeauty.papakarlo.common.ui.element.card.NavigationTextCard
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.databinding.FragmentCreateOrderBinding
import com.bunbeauty.papakarlo.feature.create_order.screen.cafe_address_list.CafeAddressListBottomSheet
import com.bunbeauty.papakarlo.feature.create_order.screen.comment.CommentBottomSheet
import com.bunbeauty.papakarlo.feature.create_order.screen.create_order.CreateOrderFragmentDirections.toCreateAddressFragment
import com.bunbeauty.papakarlo.feature.create_order.screen.deferred_time_new.DeferredTimeBottomSheet
import com.bunbeauty.papakarlo.feature.create_order.screen.user_address_list.UserAddressListBottomSheet
import com.bunbeauty.papakarlo.feature.create_order.screen.user_address_list.UserAddressListResult
import com.bunbeauty.papakarlo.feature.create_order.ui.Switcher
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateOrderFragment : BaseFragment(R.layout.fragment_create_order) {

    override val viewModel: CreateOrderViewModel by viewModel()
    override val viewBinding by viewBinding(FragmentCreateOrderBinding::bind)

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.update()
        viewBinding.fragmentCreateOrderCvMain.setContent {
            val orderCreationState by viewModel.orderCreationState.collectAsState()

            LaunchedEffect(key1 = orderCreationState.eventList) {
                handleEventList(orderCreationState.eventList)
            }

            CreateOrderScreen(orderCreationState)
        }
    }

    @Composable
    private fun CreateOrderScreen(orderCreationState: OrderCreationUiState) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(FoodDeliveryTheme.colors.background)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                Column(
                    modifier = Modifier
                        .padding(FoodDeliveryTheme.dimensions.mediumSpace)
                        .verticalScroll(rememberScrollState())
                ) {
                    Switcher(
                        modifier = Modifier.fillMaxWidth(),
                        variantStringIdList = listOf(
                            R.string.action_create_order_delivery,
                            R.string.action_create_order_pickup
                        ),
                        position = orderCreationState.switcherPosition
                    ) { changedPosition ->
                        viewModel.onSwitcherPositionChanged(changedPosition)
                    }
                    AddressCard(orderCreationState)
                    DeliveryAddressError(orderCreationState)
                    CommentCard(orderCreationState)
                    DeferredTimeCard(orderCreationState)
                }
                BlurLine(modifier = Modifier.align(Alignment.BottomCenter))
            }
            BottomAmountBar(orderCreationState)
        }
    }

    @Composable
    private fun AddressCard(orderCreationState: OrderCreationUiState) {
        if (orderCreationState.isDelivery) {
            if (orderCreationState.deliveryAddress == null) {
                NavigationCard(
                    modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                    labelStringId = orderCreationState.addressLabelId,
                    isClickable = !orderCreationState.isLoading
                ) {
                    viewModel.onUserAddressClicked()
                }
            } else {
                NavigationTextCard(
                    modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                    hintStringId = orderCreationState.addressLabelId,
                    label = orderCreationState.deliveryAddress,
                    isClickable = !orderCreationState.isLoading
                ) {
                    viewModel.onUserAddressClicked()
                }
            }
        } else {
            NavigationTextCard(
                modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                hintStringId = orderCreationState.addressLabelId,
                label = orderCreationState.pickupAddress ?: "",
                isClickable = !orderCreationState.isLoading
            ) {
                viewModel.onCafeAddressClicked()
            }
        }
    }

    @Composable
    private fun DeliveryAddressError(orderCreationState: OrderCreationUiState) {
        if (orderCreationState.isDelivery && orderCreationState.isAddressErrorShown) {
            Text(
                modifier = Modifier
                    .padding(top = FoodDeliveryTheme.dimensions.verySmallSpace)
                    .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace),
                text = stringResource(R.string.error_select_delivery_address),
                style = FoodDeliveryTheme.typography.body2,
                color = FoodDeliveryTheme.colors.negative
            )
        }
    }

    @Composable
    private fun CommentCard(orderCreationState: OrderCreationUiState) {
        if (orderCreationState.comment == null) {
            NavigationCard(
                modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                labelStringId = R.string.comment,
                isClickable = !orderCreationState.isLoading
            ) {
                viewModel.onCommentClicked()
            }
        } else {
            NavigationTextCard(
                modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                hintStringId = R.string.hint_create_order_comment,
                label = orderCreationState.comment,
                isClickable = !orderCreationState.isLoading
            ) {
                viewModel.onCommentClicked()
            }
        }
    }

    @Composable
    private fun DeferredTimeCard(orderCreationState: OrderCreationUiState) {
        NavigationTextCard(
            modifier = Modifier.padding(vertical = FoodDeliveryTheme.dimensions.smallSpace),
            hintStringId = orderCreationState.deferredTimeLabelId,
            label = orderCreationState.deferredTime,
            isClickable = !orderCreationState.isLoading
        ) {
            viewModel.onDeferredTimeClicked()
        }
    }

    @Composable
    private fun BottomAmountBar(orderCreationState: OrderCreationUiState) {
        Column(
            modifier = Modifier
                .background(FoodDeliveryTheme.colors.surface)
                .padding(FoodDeliveryTheme.dimensions.mediumSpace)
        ) {
            Row {
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.msg_create_order_total_cost),
                    style = FoodDeliveryTheme.typography.body1,
                    color = FoodDeliveryTheme.colors.onSurface
                )
                if (orderCreationState.totalCost != null) {
                    Text(
                        text = orderCreationState.totalCost,
                        style = FoodDeliveryTheme.typography.body1,
                        color = FoodDeliveryTheme.colors.onSurface
                    )
                }
            }
            if (orderCreationState.isDelivery) {
                Row(modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.smallSpace)) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = stringResource(R.string.msg_create_order_delivery_cost),
                        style = FoodDeliveryTheme.typography.body1,
                        color = FoodDeliveryTheme.colors.onSurface
                    )
                    if (orderCreationState.deliveryCost != null) {
                        Text(
                            text = orderCreationState.deliveryCost,
                            style = FoodDeliveryTheme.typography.body1,
                            color = FoodDeliveryTheme.colors.onSurface
                        )
                    }
                }
            }
            Row(modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.smallSpace)) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.msg_create_order_amount_to_pay),
                    style = FoodDeliveryTheme.typography.h2,
                    color = FoodDeliveryTheme.colors.onSurface
                )
                if (orderCreationState.finalCost != null) {
                    Text(
                        text = orderCreationState.finalCost,
                        style = FoodDeliveryTheme.typography.h2,
                        color = FoodDeliveryTheme.colors.onSurface
                    )
                }
            }
            LoadingButton(
                modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.mediumSpace),
                textStringId = R.string.action_create_order_create_order,
                isLoading = orderCreationState.isLoading
            ) {
                viewModel.onCreateOrderClicked()
            }
        }
    }

    private suspend fun handleEventList(eventList: List<OrderCreationUiState.Event>) {
        eventList.forEach { event ->
            when (event) {
                is OrderCreationUiState.Event.OpenCreateAddressEvent -> {
                    findNavController().navigate(toCreateAddressFragment())
                }
                is OrderCreationUiState.Event.ShowUserAddressListEvent -> {
                    val result =
                        UserAddressListBottomSheet.show(childFragmentManager, event.addressList)
                    handleUserAddressListResult(result)
                }
                is OrderCreationUiState.Event.ShowCafeAddressListEvent -> {
                    CafeAddressListBottomSheet.show(childFragmentManager, event.addressList)
                        ?.let { addressItem ->
                            viewModel.onCafeAddressChanged(addressItem.uuid)
                        }
                }
                is OrderCreationUiState.Event.ShowCommentInputEvent -> {
                    CommentBottomSheet.show(childFragmentManager, event.comment)?.let { comment ->
                        viewModel.onCommentChanged(comment)
                    }
                }
                is OrderCreationUiState.Event.ShowDeferredTimeEvent -> {
                    DeferredTimeBottomSheet.show(
                        fragmentManager = childFragmentManager,
                        deferredTime = event.deferredTime,
                        minTime = event.minTime,
                        title = event.title,
                    )?.let { deferredTime ->
                        viewModel.onDeferredTimeSelected(deferredTime)
                    }
                }
                else -> {}
                //TODO handle all events
            }
        }
        viewModel.consumeEventList(eventList)
    }

    private fun handleUserAddressListResult(result: UserAddressListResult) {
        when (result) {
            is UserAddressListResult.AddressSelected -> {
                viewModel.onUserAddressChanged(result.addressItem.uuid)
            }
            is UserAddressListResult.AddNewAddress -> {
                findNavController().navigate(CreateOrderFragmentDirections.toCreateAddressFragment())
            }
            is UserAddressListResult.Cancel -> {}
        }
    }

    @Preview
    @Composable
    private fun CreateOrderEmptyDeliveryScreenPreview() {
        CreateOrderScreen(
            orderCreationState = OrderCreationUiState(
                isDelivery = true,
                deliveryAddress = null,
                comment = null,
                deferredTime = "Как можно скорее",
                totalCost = null,
                deliveryCost = null,
                finalCost = null,
                isLoading = false,
            )
        )
    }

    @Preview
    @Composable
    private fun CreateOrderDeliveryScreenPreview() {
        CreateOrderScreen(
            orderCreationState = OrderCreationUiState(
                isDelivery = true,
                deliveryAddress = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж, код домофона 555",
                comment = "Побыстрее пожалуйста, кушать очень хочу",
                deferredTime = "Как можно скорее",
                totalCost = "250 ₽",
                deliveryCost = "100 ₽",
                finalCost = "350 ₽",
                isLoading = false,
            )
        )
    }

    @Preview
    @Composable
    private fun CreateOrderEmptyPickupScreenPreview() {
        CreateOrderScreen(
            orderCreationState = OrderCreationUiState(
                isDelivery = false,
                pickupAddress = null,
                comment = null,
                deferredTime = "10:30",
                totalCost = null,
                deliveryCost = null,
                finalCost = null,
                isLoading = false,
            )
        )
    }

    @Preview
    @Composable
    private fun CreateOrderPickupScreenPreview() {
        CreateOrderScreen(
            orderCreationState = OrderCreationUiState(
                isDelivery = false,
                pickupAddress = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж, код домофона 555",
                comment = "Побыстрее пожалуйста, кушать очень хочу",
                deferredTime = "Как можно скорее",
                totalCost = "250 ₽",
                deliveryCost = "100 ₽",
                finalCost = "350 ₽",
                isLoading = true,
            )
        )
    }
}