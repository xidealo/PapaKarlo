package com.bunbeauty.papakarlo.feature.create_order.screen.create_order

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragmentWithSharedViewModel
import com.bunbeauty.papakarlo.common.ui.element.BlurLine
import com.bunbeauty.papakarlo.common.ui.element.LoadingButton
import com.bunbeauty.papakarlo.common.ui.element.card.NavigationCard
import com.bunbeauty.papakarlo.common.ui.element.card.NavigationTextCard
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.databinding.FragmentCreateOrderBinding
import com.bunbeauty.papakarlo.extensions.showSnackbar
import com.bunbeauty.papakarlo.feature.create_order.mapper.UserAddressItemMapper
import com.bunbeauty.papakarlo.feature.create_order.screen.cafe_address_list.CafeAddressListBottomSheet
import com.bunbeauty.papakarlo.feature.create_order.screen.comment.CommentBottomSheet
import com.bunbeauty.papakarlo.feature.create_order.screen.create_order.CreateOrderFragmentDirections.toCreateAddressFragment
import com.bunbeauty.papakarlo.feature.create_order.screen.create_order.CreateOrderFragmentDirections.toProfileFragment
import com.bunbeauty.papakarlo.feature.create_order.screen.deferred_time.DeferredTimeBottomSheet
import com.bunbeauty.papakarlo.feature.create_order.screen.user_address_list.UserAddressListBottomSheet
import com.bunbeauty.papakarlo.feature.create_order.screen.user_address_list.UserAddressListResult
import com.bunbeauty.papakarlo.feature.create_order.ui.Switcher
import com.bunbeauty.papakarlo.util.string.IStringUtil
import com.bunbeauty.shared.presentation.create_order.CreateOrderViewModel
import com.bunbeauty.shared.presentation.create_order.OrderCreationState
import com.bunbeauty.shared.presentation.create_order.model.TimeUI
import com.bunbeauty.shared.presentation.create_order.model.UserAddressUi
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateOrderFragment : BaseFragmentWithSharedViewModel(R.layout.fragment_create_order) {

    val viewModel: CreateOrderViewModel by viewModel()
    override val viewBinding by viewBinding(FragmentCreateOrderBinding::bind)

    val stringUtil: IStringUtil by inject()

    private val userAddressItemMapper: UserAddressItemMapper by inject()

    @OptIn(ExperimentalLifecycleComposeApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.update()
        viewBinding.fragmentCreateOrderCvMain.setContent {
            val orderCreationState by viewModel.orderCreationState.collectAsStateWithLifecycle()
            CreateOrderScreen(orderCreationState)
            LaunchedEffect(orderCreationState.eventList) {
                handleEventList(orderCreationState.eventList)
            }
        }
    }

    @Composable
    private fun CreateOrderScreen(orderCreationState: OrderCreationState) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(FoodDeliveryTheme.colors.mainColors.background)
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
    private fun AddressCard(orderCreationState: OrderCreationState) {
        val labelStringId = if (orderCreationState.isDelivery) {
            R.string.delivery_address
        } else {
            R.string.cafe_address
        }
        if (orderCreationState.isDelivery) {
            if (orderCreationState.deliveryAddress == null) {
                NavigationCard(
                    modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                    enabled = !orderCreationState.isLoading,
                    labelStringId = labelStringId
                ) {
                    viewModel.onUserAddressClicked()
                }
            } else {
                NavigationTextCard(
                    modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                    hintStringId = labelStringId,
                    label = stringUtil.getUserAddressString(orderCreationState.deliveryAddress),
                    isClickable = !orderCreationState.isLoading
                ) {
                    viewModel.onUserAddressClicked()
                }
            }
        } else {
            NavigationTextCard(
                modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                hintStringId = labelStringId,
                label = orderCreationState.pickupAddress ?: "",
                isClickable = !orderCreationState.isLoading
            ) {
                viewModel.onCafeAddressClicked()
            }
        }
    }

    @Composable
    private fun DeliveryAddressError(orderCreationState: OrderCreationState) {
        if (orderCreationState.isDelivery && orderCreationState.isAddressErrorShown) {
            Text(
                modifier = Modifier
                    .padding(top = FoodDeliveryTheme.dimensions.verySmallSpace)
                    .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace),
                text = stringResource(R.string.error_select_delivery_address),
                style = FoodDeliveryTheme.typography.bodySmall,
                color = FoodDeliveryTheme.colors.negative
            )
        }
    }

    @Composable
    private fun CommentCard(orderCreationState: OrderCreationState) {
        if (orderCreationState.comment == null) {
            NavigationCard(
                modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                enabled = !orderCreationState.isLoading,
                labelStringId = R.string.comment
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
    private fun DeferredTimeCard(orderCreationState: OrderCreationState) {
        val hintStringId = if (orderCreationState.isDelivery) {
            R.string.delivery_time
        } else {
            R.string.pickup_time
        }
        NavigationTextCard(
            modifier = Modifier.padding(vertical = FoodDeliveryTheme.dimensions.smallSpace),
            hintStringId = hintStringId,
            label = stringUtil.getTimeString(orderCreationState.deferredTime),
            isClickable = !orderCreationState.isLoading
        ) {
            viewModel.onDeferredTimeClicked()
        }
    }

    @Composable
    private fun BottomAmountBar(orderCreationState: OrderCreationState) {
        Column(
            modifier = Modifier
                .background(FoodDeliveryTheme.colors.mainColors.surface)
                .padding(FoodDeliveryTheme.dimensions.mediumSpace)
        ) {
            Row {
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.msg_create_order_total_cost),
                    style = FoodDeliveryTheme.typography.body1,
                    color = FoodDeliveryTheme.colors.mainColors.onSurface
                )
                if (orderCreationState.totalCost != null) {
                    stringUtil.getCostString(orderCreationState.totalCost)?.let {
                        Text(
                            text = it,
                            style = FoodDeliveryTheme.typography.body1,
                            color = FoodDeliveryTheme.colors.mainColors.onSurface
                        )
                    }
                }
            }
            if (orderCreationState.isDelivery) {
                Row(modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.smallSpace)) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = stringResource(R.string.msg_create_order_delivery_cost),
                        style = FoodDeliveryTheme.typography.body1,
                        color = FoodDeliveryTheme.colors.mainColors.onSurface
                    )
                    if (orderCreationState.deliveryCost != null) {
                        stringUtil.getCostString(orderCreationState.deliveryCost)?.let {
                            Text(
                                text = it,
                                style = FoodDeliveryTheme.typography.body1,
                                color = FoodDeliveryTheme.colors.mainColors.onSurface
                            )
                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .padding(top = FoodDeliveryTheme.dimensions.smallSpace)
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.msg_create_order_amount_to_pay),
                    style = FoodDeliveryTheme.typography.h2,
                    color = FoodDeliveryTheme.colors.mainColors.onSurface
                )
                if (orderCreationState.finalCost != null) {
                    stringUtil.getCostString(orderCreationState.finalCost)?.let {
                        Text(
                            text = it,
                            style = FoodDeliveryTheme.typography.h2,
                            color = FoodDeliveryTheme.colors.mainColors.onSurface
                        )
                    }
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

    private suspend fun handleEventList(eventList: List<OrderCreationState.Event>) {
        eventList.forEach { event ->
            when (event) {
                is OrderCreationState.Event.OpenCreateAddressEvent -> {
                    findNavController().navigate(toCreateAddressFragment())
                }
                is OrderCreationState.Event.ShowUserAddressListEvent -> {
                    UserAddressListBottomSheet.show(
                        fragmentManager = childFragmentManager,
                        addressList = event.addressList.map(userAddressItemMapper::toItem),
                        selectedUserAddressUuid = event.selectedUserAddressUuid,
                    )?.let { result ->
                        handleUserAddressListResult(result)
                    }
                }
                is OrderCreationState.Event.ShowCafeAddressListEvent -> {
                    CafeAddressListBottomSheet.show(
                        fragmentManager = childFragmentManager,
                        addressList = event.addressList,
                        selectedCafeAddress = event.selectedCafeAddress
                    )?.let { addressItem ->
                        viewModel.onCafeAddressChanged(addressItem.uuid)
                    }
                }
                is OrderCreationState.Event.ShowCommentInputEvent -> {
                    CommentBottomSheet.show(
                        childFragmentManager,
                        event.comment
                    )?.let { comment ->
                        viewModel.onCommentChanged(comment)
                    }
                }
                is OrderCreationState.Event.ShowDeferredTimeEvent -> {
                    val titleId = if (event.isDelivery) {
                        R.string.delivery_time
                    } else {
                        R.string.pickup_time
                    }
                    DeferredTimeBottomSheet.show(
                        fragmentManager = childFragmentManager,
                        deferredTime = event.deferredTime,
                        minTime = event.minTime,
                        title = resources.getString(titleId)
                    )?.let { deferredTime ->
                        viewModel.onDeferredTimeSelected(deferredTime)
                    }
                }
                is OrderCreationState.Event.ShowSomethingWentWrongErrorEvent -> {
                    viewBinding.root.showSnackbar(
                        message = resources.getString(R.string.error_something_went_wrong),
                        textColor = resourcesProvider.getColorByAttr(R.attr.colorOnError),
                        backgroundColor = resourcesProvider.getColorByAttr(R.attr.colorError),
                        isTop = true
                    )
                }
                is OrderCreationState.Event.ShowUserUnauthorizedErrorEvent -> {
                    viewBinding.root.showSnackbar(
                        message = resources.getString(R.string.error_user),
                        textColor = resourcesProvider.getColorByAttr(R.attr.colorOnError),
                        backgroundColor = resourcesProvider.getColorByAttr(R.attr.colorError),
                        isTop = true
                    )
                }
                is OrderCreationState.Event.OrderCreatedEvent -> {
                    viewBinding.root.showSnackbar(
                        message = resources.getString(R.string.msg_order_code, event.code),
                        textColor = resourcesProvider.getColorByAttr(R.attr.colorOnPrimary),
                        backgroundColor = resourcesProvider.getColorByAttr(R.attr.colorPrimary),
                        isTop = false
                    )
                    findNavController().navigate(toProfileFragment())
                }
                is OrderCreationState.Event.ShowUserAddressError -> {
                    // TODO (show address error)
                }
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
                findNavController().navigate(toCreateAddressFragment())
            }
        }
    }

    @Preview
    @Composable
    private fun CreateOrderEmptyDeliveryScreenPreview() {
        CreateOrderScreen(
            orderCreationState = OrderCreationState(
                isDelivery = true,
                deliveryAddress = null,
                comment = null,
                deferredTime = TimeUI.ASAP,
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
            orderCreationState = OrderCreationState(
                isDelivery = true,
                deliveryAddress = UserAddressUi(
                    uuid = "1",
                    street = "улица Чапаева",
                    house = "22аб",
                    flat = "55",
                    entrance = "1",
                    floor = "1",
                    comment = "код домофона 555",
                ),
                comment = "Побыстрее пожалуйста, кушать очень хочу",
                deferredTime = TimeUI.ASAP,
                totalCost = 250,
                deliveryCost = 100,
                finalCost = 350,
                isLoading = false,
            )
        )
    }

    @Preview
    @Composable
    private fun CreateOrderEmptyPickupScreenPreview() {
        CreateOrderScreen(
            orderCreationState = OrderCreationState(
                isDelivery = false,
                pickupAddress = null,
                comment = null,
                deferredTime = TimeUI.Time(10, 30),
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
            orderCreationState = OrderCreationState(
                isDelivery = false,
                pickupAddress = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж, код домофона 555",
                comment = "Побыстрее пожалуйста, кушать очень хочу",
                deferredTime = TimeUI.ASAP,
                totalCost = 250,
                deliveryCost = 100,
                finalCost = 350,
                isLoading = true,
            )
        )
    }
}
