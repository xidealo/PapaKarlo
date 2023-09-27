package com.bunbeauty.papakarlo.feature.createorder.screen.createorder

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragmentWithSharedViewModel
import com.bunbeauty.papakarlo.common.extension.navigateSafe
import com.bunbeauty.papakarlo.common.ui.element.FoodDeliveryScaffold
import com.bunbeauty.papakarlo.common.ui.element.button.LoadingButton
import com.bunbeauty.papakarlo.common.ui.element.card.DiscountCard
import com.bunbeauty.papakarlo.common.ui.element.card.NavigationCard
import com.bunbeauty.papakarlo.common.ui.element.card.NavigationTextCard
import com.bunbeauty.papakarlo.common.ui.element.surface.FoodDeliverySurface
import com.bunbeauty.papakarlo.common.ui.element.switcher.FoodDeliverySwitcher
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.bold
import com.bunbeauty.papakarlo.databinding.LayoutComposeBinding
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import com.bunbeauty.papakarlo.feature.createorder.mapper.UserAddressItemMapper
import com.bunbeauty.papakarlo.feature.createorder.screen.cafeaddresslist.CafeAddressListBottomSheet
import com.bunbeauty.papakarlo.feature.createorder.screen.comment.CommentBottomSheet
import com.bunbeauty.papakarlo.feature.createorder.screen.createorder.CreateOrderFragmentDirections.toCreateAddressFragment
import com.bunbeauty.papakarlo.feature.createorder.screen.createorder.CreateOrderFragmentDirections.toProfileFragment
import com.bunbeauty.papakarlo.feature.createorder.screen.deferredtime.DeferredTimeBottomSheet
import com.bunbeauty.papakarlo.feature.createorder.screen.paymentmethod.SelectPaymentMethodBottomSheet
import com.bunbeauty.papakarlo.feature.createorder.screen.paymentmethod.SelectablePaymentMethodUI
import com.bunbeauty.papakarlo.feature.createorder.screen.useraddresslist.UserAddressListBottomSheet
import com.bunbeauty.papakarlo.feature.createorder.screen.useraddresslist.UserAddressListResult
import com.bunbeauty.papakarlo.feature.main.IMessageHost
import com.bunbeauty.papakarlo.feature.profile.screen.payment.PaymentMethodUI
import com.bunbeauty.papakarlo.feature.profile.screen.payment.PaymentMethodValueUI
import com.bunbeauty.papakarlo.feature.profile.screen.profile.PaymentMethodUiStateMapper
import com.bunbeauty.shared.presentation.create_order.CreateOrderEvent
import com.bunbeauty.shared.presentation.create_order.CreateOrderViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateOrderFragment : BaseFragmentWithSharedViewModel(R.layout.layout_compose) {

    val viewModel: CreateOrderViewModel by viewModel()
    override val viewBinding by viewBinding(LayoutComposeBinding::bind)

    private val userAddressItemMapper: UserAddressItemMapper by inject()
    private val createOrderStateMapper: CreateOrderStateMapper by inject()
    private val paymentMethodUiStateMapper: PaymentMethodUiStateMapper by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.update()
        viewBinding.root.setContentWithTheme {
            val orderCreationState by viewModel.uiState.collectAsStateWithLifecycle()
            CreateOrderScreen(
                createOrderStateMapper.map(orderCreationState),
                onPositionChanged = viewModel::onSwitcherPositionChanged,
                onUserAddressClicked = viewModel::onUserAddressClicked,
                onCafeAddressClicked = viewModel::onCafeAddressClicked,
                onCommentClicked = viewModel::onCommentClicked,
                onDeferredTimeClicked = viewModel::onDeferredTimeClicked,
                onPaymentMethodClick = viewModel::onPaymentMethodClick,
                onCreateOrderClicked = viewModel::onCreateOrderClicked
            )
            LaunchedEffect(orderCreationState.eventList) {
                handleEventList(orderCreationState.eventList)
            }
        }
    }

    @Composable
    private fun CreateOrderScreen(
        createOrderUi: CreateOrderUi,
        onPositionChanged: (Int) -> Unit,
        onUserAddressClicked: () -> Unit,
        onCafeAddressClicked: () -> Unit,
        onCommentClicked: () -> Unit,
        onDeferredTimeClicked: () -> Unit,
        onPaymentMethodClick: () -> Unit,
        onCreateOrderClicked: () -> Unit
    ) {
        FoodDeliveryScaffold(
            title = stringResource(id = R.string.title_create_order),
            backActionClick = {
                findNavController().popBackStack()
            }
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(FoodDeliveryTheme.dimensions.mediumSpace)
                ) {
                    FoodDeliverySwitcher(
                        modifier = Modifier.fillMaxWidth(),
                        optionResIdList = listOf(
                            R.string.action_create_order_delivery,
                            R.string.action_create_order_pickup
                        ),
                        position = createOrderUi.switcherPosition,
                        onPositionChanged = onPositionChanged
                    )
                    AddressCard(
                        createOrderUi = createOrderUi,
                        onUserAddressClicked = onUserAddressClicked,
                        onCafeAddressClicked = onCafeAddressClicked
                    )
                    DeliveryAddressError(createOrderUi)
                    CommentCard(
                        createOrderUi = createOrderUi,
                        onCommentClicked = onCommentClicked
                    )
                    DeferredTimeCard(
                        createOrderUi = createOrderUi,
                        onDeferredTimeClicked = onDeferredTimeClicked
                    )
                    PaymentMethodCard(
                        createOrderUi = createOrderUi,
                        onPaymentMethodClick = onPaymentMethodClick
                    )
                }
                BottomAmountBar(
                    createOrderUi = createOrderUi,
                    onCreateOrderClicked = onCreateOrderClicked
                )
            }
        }
    }

    @Composable
    private fun AddressCard(
        createOrderUi: CreateOrderUi,
        onUserAddressClicked: () -> Unit,
        onCafeAddressClicked: () -> Unit
    ) {
        val labelStringId = if (createOrderUi.isDelivery) {
            R.string.delivery_address
        } else {
            R.string.pickup_address
        }
        if (createOrderUi.isDelivery) {
            if (createOrderUi.deliveryAddress == null) {
                NavigationCard(
                    modifier = Modifier
                        .padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                    clickable = !createOrderUi.isLoading,
                    label = stringResource(labelStringId),
                    onClick = onUserAddressClicked
                )
            } else {
                NavigationTextCard(
                    modifier = Modifier
                        .padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                    hintStringId = labelStringId,
                    label = createOrderUi.deliveryAddress,
                    clickable = !createOrderUi.isLoading,
                    onClick = onUserAddressClicked
                )
            }
        } else {
            NavigationTextCard(
                modifier = Modifier
                    .padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                hintStringId = labelStringId,
                label = createOrderUi.pickupAddress ?: "",
                clickable = !createOrderUi.isLoading,
                onClick = onCafeAddressClicked
            )
        }
    }

    @Composable
    private fun DeliveryAddressError(createOrderUi: CreateOrderUi) {
        if (createOrderUi.isDelivery && createOrderUi.isAddressErrorShown) {
            Text(
                modifier = Modifier
                    .padding(top = FoodDeliveryTheme.dimensions.verySmallSpace)
                    .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace),
                text = stringResource(R.string.error_select_delivery_address),
                style = FoodDeliveryTheme.typography.bodySmall,
                color = FoodDeliveryTheme.colors.mainColors.error
            )
        }
    }

    @Composable
    private fun CommentCard(
        createOrderUi: CreateOrderUi,
        onCommentClicked: () -> Unit
    ) {
        if (createOrderUi.comment == null) {
            NavigationCard(
                modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                label = stringResource(R.string.comment),
                clickable = !createOrderUi.isLoading,
                onClick = onCommentClicked
            )
        } else {
            NavigationTextCard(
                modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                hintStringId = R.string.hint_create_order_comment,
                label = createOrderUi.comment,
                clickable = !createOrderUi.isLoading,
                onClick = onCommentClicked
            )
        }
    }

    @Composable
    private fun DeferredTimeCard(
        createOrderUi: CreateOrderUi,
        onDeferredTimeClicked: () -> Unit
    ) {
        val hintStringId = if (createOrderUi.isDelivery) {
            R.string.delivery_time
        } else {
            R.string.pickup_time
        }
        NavigationTextCard(
            modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.smallSpace),
            hintStringId = hintStringId,
            label = createOrderUi.deferredTime,
            clickable = !createOrderUi.isLoading,
            onClick = onDeferredTimeClicked
        )
    }

    @Composable
    private fun PaymentMethodCard(
        createOrderUi: CreateOrderUi,
        onPaymentMethodClick: () -> Unit
    ) {
        if (createOrderUi.selectedPaymentMethod == null) {
            NavigationCard(
                modifier = Modifier
                    .padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                label = stringResource(R.string.payment_method),
                clickable = !createOrderUi.isLoading,
                onClick = onPaymentMethodClick
            )
        } else {
            NavigationTextCard(
                modifier = Modifier
                    .padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                hintStringId = R.string.payment_method,
                label = createOrderUi.selectedPaymentMethod.name,
                clickable = !createOrderUi.isLoading,
                onClick = onPaymentMethodClick
            )
        }
    }

    @Composable
    private fun BottomAmountBar(
        createOrderUi: CreateOrderUi,
        onCreateOrderClicked: () -> Unit
    ) {
        FoodDeliverySurface(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(FoodDeliveryTheme.dimensions.mediumSpace)) {
                createOrderUi.discount?.let { discount ->
                    Row(modifier = Modifier.padding(bottom = 8.dp)) {
                        Text(
                            text = stringResource(R.string.msg_order_details_discount),
                            style = FoodDeliveryTheme.typography.bodyMedium,
                            color = FoodDeliveryTheme.colors.mainColors.onSurface
                        )
                        Spacer(modifier = Modifier.weight(1f))

                        DiscountCard(discount = discount)
                    }
                }
                Row {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = stringResource(R.string.msg_create_order_total_cost),
                        style = FoodDeliveryTheme.typography.bodyMedium,
                        color = FoodDeliveryTheme.colors.mainColors.onSurface
                    )
                    createOrderUi.totalCost?.let {
                        Text(
                            text = it,
                            style = FoodDeliveryTheme.typography.bodyMedium,
                            color = FoodDeliveryTheme.colors.mainColors.onSurface
                        )
                    }
                }
                if (createOrderUi.isDelivery) {
                    Row(modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.smallSpace)) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = stringResource(R.string.msg_create_order_delivery_cost),
                            style = FoodDeliveryTheme.typography.bodyMedium,
                            color = FoodDeliveryTheme.colors.mainColors.onSurface
                        )
                        createOrderUi.deliveryCost?.let {
                            Text(
                                text = it,
                                style = FoodDeliveryTheme.typography.bodyMedium,
                                color = FoodDeliveryTheme.colors.mainColors.onSurface
                            )
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
                        style = FoodDeliveryTheme.typography.bodyMedium.bold,
                        color = FoodDeliveryTheme.colors.mainColors.onSurface
                    )
                    createOrderUi.oldFinalCost?.let { oldFinalCost ->
                        Text(
                            modifier = Modifier
                                .padding(end = FoodDeliveryTheme.dimensions.smallSpace),
                            text = oldFinalCost,
                            style = FoodDeliveryTheme.typography.bodyMedium.bold,
                            color = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                            textDecoration = TextDecoration.LineThrough
                        )
                    }
                    createOrderUi.newFinalCost?.let { newFinalCost ->
                        Text(
                            text = newFinalCost,
                            style = FoodDeliveryTheme.typography.bodyMedium.bold,
                            color = FoodDeliveryTheme.colors.mainColors.onSurface
                        )
                    }
                }
                LoadingButton(
                    modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.mediumSpace),
                    textStringId = R.string.action_create_order_create_order,
                    isLoading = createOrderUi.isLoading,
                    onClick = onCreateOrderClicked
                )
            }
        }
    }

    private fun handleEventList(eventList: List<CreateOrderEvent>) {
        eventList.forEach { event ->
            when (event) {
                is CreateOrderEvent.OpenCreateAddressEvent -> {
                    findNavController().navigateSafe(toCreateAddressFragment())
                }

                is CreateOrderEvent.ShowUserAddressListEvent -> {
                    lifecycleScope.launch {
                        UserAddressListBottomSheet.show(
                            fragmentManager = childFragmentManager,
                            addressList = event.addressList.map(userAddressItemMapper::toItem)
                        )?.let { result ->
                            handleUserAddressListResult(result)
                        }
                    }
                }

                is CreateOrderEvent.ShowCafeAddressListEvent -> {
                    lifecycleScope.launch {
                        CafeAddressListBottomSheet.show(
                            fragmentManager = childFragmentManager,
                            addressList = event.addressList
                        )?.let { addressItem ->
                            viewModel.onCafeAddressChanged(addressItem.uuid)
                        }
                    }
                }

                is CreateOrderEvent.ShowCommentInputEvent -> {
                    lifecycleScope.launch {
                        CommentBottomSheet.show(
                            childFragmentManager,
                            event.comment
                        )?.let { comment ->
                            viewModel.onCommentChanged(comment)
                        }
                    }
                }

                is CreateOrderEvent.ShowDeferredTimeEvent -> {
                    lifecycleScope.launch {
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
                }

                is CreateOrderEvent.ShowSomethingWentWrongErrorEvent -> {
                    (activity as? IMessageHost)?.showErrorMessage(
                        resources.getString(R.string.error_something_went_wrong)
                    )
                }

                is CreateOrderEvent.ShowUserUnauthorizedErrorEvent -> {
                    (activity as? IMessageHost)?.showErrorMessage(
                        resources.getString(R.string.error_user)
                    )
                }

                is CreateOrderEvent.OrderCreatedEvent -> {
                    (activity as? IMessageHost)?.showInfoMessage(
                        resources.getString(
                            R.string.msg_order_code,
                            event.code
                        )
                    )
                    findNavController().navigateSafe(toProfileFragment())
                }

                is CreateOrderEvent.ShowUserAddressError -> {
                    (activity as? IMessageHost)?.showErrorMessage(
                        resources.getString(R.string.error_user_address)
                    )
                }

                is CreateOrderEvent.ShowPaymentMethodList -> {
                    lifecycleScope.launch {
                        SelectPaymentMethodBottomSheet.show(
                            fragmentManager = childFragmentManager,
                            selectablePaymentMethodList = event.selectablePaymentMethodList
                                .map { selectablePaymentMethod ->
                                    SelectablePaymentMethodUI(
                                        paymentMethodUI = paymentMethodUiStateMapper.map(
                                            selectablePaymentMethod.paymentMethod
                                        ),
                                        isSelected = selectablePaymentMethod.isSelected
                                    )
                                }
                        )?.let { paymentMethod ->
                            viewModel.onPaymentMethodChanged(paymentMethod.paymentMethodUI.uuid)
                        }
                    }
                }

                is CreateOrderEvent.ShowPaymentMethodError -> {
                    (activity as? IMessageHost)?.showErrorMessage(
                        resources.getString(R.string.error_payment_method)
                    )
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
                findNavController().navigateSafe(toCreateAddressFragment())
            }
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun CreateOrderEmptyDeliveryScreenPreview() {
        FoodDeliveryTheme {
            CreateOrderScreen(
                createOrderUi = CreateOrderUi(
                    isDelivery = true,
                    deliveryAddress = null,
                    comment = null,
                    deferredTime = "",
                    totalCost = null,
                    deliveryCost = null,
                    newFinalCost = null,
                    oldFinalCost = null,
                    isLoading = false,
                    pickupAddress = null,
                    isAddressErrorShown = false,
                    discount = null,
                    selectedPaymentMethod = PaymentMethodUI(
                        uuid = "uuid",
                        name = "Наличка",
                        value = PaymentMethodValueUI(
                            value = "наличка",
                            valueToCopy = "наличка"
                        )
                    )
                ),
                onPositionChanged = {},
                onUserAddressClicked = {},
                onCafeAddressClicked = {},
                onCommentClicked = {},
                onDeferredTimeClicked = {},
                onPaymentMethodClick = {},
                onCreateOrderClicked = {}
            )
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun CreateOrderDeliveryScreenPreview() {
        FoodDeliveryTheme {
            CreateOrderScreen(
                createOrderUi = CreateOrderUi(
                    isDelivery = true,
                    deliveryAddress =
                    "1" +
                        "улица Чапаева" +
                        "22аб" +
                        "55" +
                        "1" +
                        "1" +
                        "код домофона 555",
                    comment = "Побыстрее пожалуйста, кушать очень хочу",
                    deferredTime = "",
                    totalCost = "250 $",
                    deliveryCost = "100 $",
                    newFinalCost = "350 $",
                    oldFinalCost = "450 $",
                    isLoading = false,
                    pickupAddress = null,
                    isAddressErrorShown = false,
                    discount = "10%",
                    selectedPaymentMethod = PaymentMethodUI(
                        uuid = "uuid",
                        name = "Наличка",
                        value = PaymentMethodValueUI(
                            value = "наличка",
                            valueToCopy = "наличка"
                        )
                    )
                ),
                onPositionChanged = {},
                onUserAddressClicked = {},
                onCafeAddressClicked = {},
                onCommentClicked = {},
                onDeferredTimeClicked = {},
                onPaymentMethodClick = {},
                onCreateOrderClicked = {}
            )
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun CreateOrderEmptyPickupScreenPreview() {
        FoodDeliveryTheme {
            CreateOrderScreen(
                createOrderUi = CreateOrderUi(
                    isDelivery = false,
                    pickupAddress = null,
                    comment = null,
                    deferredTime = "10:30",
                    totalCost = null,
                    deliveryCost = null,
                    newFinalCost = null,
                    oldFinalCost = null,
                    isLoading = false,
                    isAddressErrorShown = false,
                    deliveryAddress = null,
                    discount = null,
                    selectedPaymentMethod = PaymentMethodUI(
                        uuid = "uuid",
                        name = "Наличка",
                        value = PaymentMethodValueUI(
                            value = "наличка",
                            valueToCopy = "наличка"
                        )
                    )
                ),
                onPositionChanged = {},
                onUserAddressClicked = {},
                onCafeAddressClicked = {},
                onCommentClicked = {},
                onDeferredTimeClicked = {},
                onPaymentMethodClick = {},
                onCreateOrderClicked = {}
            )
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun CreateOrderPickupScreenPreview() {
        FoodDeliveryTheme {
            CreateOrderScreen(
                createOrderUi = CreateOrderUi(
                    isDelivery = false,
                    pickupAddress = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж, код домофона 555",
                    comment = "Побыстрее пожалуйста, кушать очень хочу",
                    deferredTime = "",
                    totalCost = "250 $",
                    deliveryCost = "100 $",
                    newFinalCost = "350 $",
                    oldFinalCost = "450 $",
                    isLoading = true,
                    isAddressErrorShown = false,
                    deliveryAddress = null,
                    discount = null,
                    selectedPaymentMethod = PaymentMethodUI(
                        uuid = "uuid",
                        name = "Наличка",
                        value = PaymentMethodValueUI(
                            value = "наличка",
                            valueToCopy = "наличка"
                        )
                    )
                ),
                onPositionChanged = {},
                onUserAddressClicked = {},
                onCafeAddressClicked = {},
                onCommentClicked = {},
                onDeferredTimeClicked = {},
                onPaymentMethodClick = {},
                onCreateOrderClicked = {}
            )
        }
    }
}
