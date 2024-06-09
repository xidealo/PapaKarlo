package com.bunbeauty.papakarlo.feature.createorder.screen.createorder

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseComposeFragment
import com.bunbeauty.papakarlo.common.extension.navigateSafe
import com.bunbeauty.papakarlo.common.ui.element.FoodDeliveryScaffold
import com.bunbeauty.papakarlo.common.ui.element.button.LoadingButton
import com.bunbeauty.papakarlo.common.ui.element.card.DiscountCard
import com.bunbeauty.papakarlo.common.ui.element.card.NavigationCard
import com.bunbeauty.papakarlo.common.ui.element.card.NavigationTextCard
import com.bunbeauty.papakarlo.common.ui.element.simmer.Shimmer
import com.bunbeauty.papakarlo.common.ui.element.surface.FoodDeliverySurface
import com.bunbeauty.papakarlo.common.ui.element.switcher.FoodDeliverySwitcher
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.bold
import com.bunbeauty.papakarlo.feature.createorder.mapper.toViewState
import com.bunbeauty.papakarlo.feature.createorder.screen.comment.CommentBottomSheet
import com.bunbeauty.papakarlo.feature.createorder.screen.createorder.CreateOrderFragmentDirections.toCreateAddressFragment
import com.bunbeauty.papakarlo.feature.createorder.screen.createorder.CreateOrderFragmentDirections.toProfileFragment
import com.bunbeauty.papakarlo.feature.createorder.screen.createorder.ui.DeferredTimeBottomSheet
import com.bunbeauty.papakarlo.feature.createorder.screen.createorder.ui.DeliveryAddressListBottomSheet
import com.bunbeauty.papakarlo.feature.createorder.screen.createorder.ui.PaymentMethodListBottomSheet
import com.bunbeauty.papakarlo.feature.createorder.screen.createorder.ui.PickupAddressListBottomSheet
import com.bunbeauty.papakarlo.feature.createorder.screen.createorder.ui.TimePickerDialog
import com.bunbeauty.papakarlo.feature.main.IMessageHost
import com.bunbeauty.papakarlo.feature.profile.screen.payment.PaymentMethodUI
import com.bunbeauty.papakarlo.feature.profile.screen.payment.PaymentMethodValueUI
import com.bunbeauty.shared.presentation.createorder.CreateOrder
import com.bunbeauty.shared.presentation.createorder.CreateOrderViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateOrderFragment :
    BaseComposeFragment<CreateOrder.DataState, CreateOrderViewState, CreateOrder.Action, CreateOrder.Event>() {

    override val viewModel: CreateOrderViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.onAction(CreateOrder.Action.Update)
    }

    @Composable
    override fun CreateOrder.DataState.mapState(): CreateOrderViewState {
        return toViewState()
    }

    @Composable
    override fun Screen(viewState: CreateOrderViewState, onAction: (CreateOrder.Action) -> Unit) {
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
                        position = viewState.switcherPosition,
                        onPositionChanged = { position ->
                            onAction(CreateOrder.Action.ChangeMethod(position))
                        }
                    )
                    AddressCard(
                        viewState = viewState,
                        onAction = onAction,
                    )
                    ErrorText(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .padding(horizontal = 16.dp),
                        messageStringId = R.string.error_select_delivery_address,
                        isShown = viewState.isAddressErrorShown
                    )
                    CommentCard(
                        viewState = viewState,
                        onAction = onAction,
                    )
                    DeferredTimeCard(
                        viewState = viewState,
                        onAction = onAction,
                    )
                    PaymentMethodCard(
                        viewState = viewState,
                        onAction = onAction,
                    )
                    ErrorText(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .padding(horizontal = 16.dp),
                        messageStringId = R.string.error_select_payment_method,
                        isShown = viewState.isPaymentMethodErrorShown
                    )
                }
                BottomAmountBar(
                    viewState = viewState,
                    onAction = onAction,
                )
            }

            DeliveryAddressListBottomSheet(
                deliveryAddressList = viewState.deliveryAddressList,
                onAction = onAction,
            )
            PickupAddressListBottomSheet(
                pickupAddressList = viewState.pickupAddressList,
                onAction = onAction,
            )
            DeferredTimeBottomSheet(
                isShown = viewState.isDeferredTimeShown,
                title = stringResource(viewState.deferredTimeStringId),
                onAction = onAction,
            )
            TimePickerDialog(
                timePicker = viewState.timePicker,
                onAction = onAction,
            )
            PaymentMethodListBottomSheet(
                paymentMethodList = viewState.paymentMethodList,
                onAction = onAction,
            )
        }
    }

    override fun handleEvent(event: CreateOrder.Event) {
        when (event) {
            is CreateOrder.Event.OpenCreateAddressEvent -> {
                findNavController().navigateSafe(toCreateAddressFragment())
            }

            is CreateOrder.Event.ShowCommentInputEvent -> {
                lifecycleScope.launch {
                    CommentBottomSheet.show(
                        childFragmentManager,
                        event.comment
                    )?.let { comment ->
                        viewModel.onAction(
                            CreateOrder.Action.ChangeComment(
                                comment = comment
                            )
                        )
                    }
                }
            }

            is CreateOrder.Event.ShowSomethingWentWrongErrorEvent -> {
                (activity as? IMessageHost)?.showErrorMessage(
                    resources.getString(R.string.error_something_went_wrong)
                )
            }

            is CreateOrder.Event.ShowUserUnauthorizedErrorEvent -> {
                (activity as? IMessageHost)?.showErrorMessage(
                    resources.getString(R.string.error_user)
                )
            }

            is CreateOrder.Event.OrderCreatedEvent -> {
                (activity as? IMessageHost)?.showInfoMessage(
                    resources.getString(
                        R.string.msg_order_code,
                        event.code
                    )
                )
                findNavController().navigateSafe(toProfileFragment())
            }

            is CreateOrder.Event.ShowUserAddressError -> {
                (activity as? IMessageHost)?.showErrorMessage(
                    resources.getString(R.string.error_user_address)
                )
            }

            is CreateOrder.Event.ShowPaymentMethodError -> {
                (activity as? IMessageHost)?.showErrorMessage(
                    resources.getString(R.string.error_payment_method)
                )
            }
        }
    }

    @Composable
    private fun AddressCard(
        viewState: CreateOrderViewState,
        onAction: (CreateOrder.Action) -> Unit,
    ) {
        if (viewState.isDelivery) {
            if (viewState.deliveryAddress == null) {
                NavigationCard(
                    modifier = Modifier
                        .padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                    clickable = viewState.isFieldsEnabled,
                    label = stringResource(R.string.delivery_address),
                    onClick = {
                        onAction(CreateOrder.Action.DeliveryAddressClick)
                    }
                )
            } else {
                NavigationTextCard(
                    modifier = Modifier
                        .padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                    hintStringId = R.string.delivery_address,
                    label = viewState.deliveryAddress,
                    clickable = viewState.isFieldsEnabled,
                    onClick = {
                        onAction(CreateOrder.Action.DeliveryAddressClick)
                    }
                )
            }
        } else {
            NavigationTextCard(
                modifier = Modifier
                    .padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                hintStringId = R.string.pickup_address,
                label = viewState.pickupAddress ?: "",
                clickable = viewState.isFieldsEnabled,
                onClick = {
                    onAction(CreateOrder.Action.PickupAddressClick)
                }
            )
        }
    }

    @Composable
    private fun CommentCard(
        viewState: CreateOrderViewState,
        onAction: (CreateOrder.Action) -> Unit,
    ) {
        if (viewState.comment == null) {
            NavigationCard(
                modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                label = stringResource(R.string.comment),
                clickable = viewState.isFieldsEnabled,
                onClick = {
                    onAction(CreateOrder.Action.CommentClick)
                }
            )
        } else {
            NavigationTextCard(
                modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                hintStringId = R.string.hint_create_order_comment,
                label = viewState.comment,
                clickable = viewState.isFieldsEnabled,
                onClick = {
                    onAction(CreateOrder.Action.CommentClick)
                }
            )
        }
    }

    @Composable
    private fun DeferredTimeCard(
        viewState: CreateOrderViewState,
        onAction: (CreateOrder.Action) -> Unit,
    ) {
        NavigationTextCard(
            modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.smallSpace),
            hintStringId = viewState.deferredTimeStringId,
            label = viewState.deferredTime,
            clickable = viewState.isFieldsEnabled,
            onClick = {
                onAction(CreateOrder.Action.DeferredTimeClick)
            }
        )
    }

    @Composable
    private fun PaymentMethodCard(
        viewState: CreateOrderViewState,
        onAction: (CreateOrder.Action) -> Unit,
    ) {
        if (viewState.selectedPaymentMethod == null) {
            NavigationCard(
                modifier = Modifier
                    .padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                label = stringResource(R.string.payment_method),
                clickable = viewState.isFieldsEnabled,
                onClick = {
                    onAction(CreateOrder.Action.PaymentMethodClick)
                }
            )
        } else {
            NavigationTextCard(
                modifier = Modifier
                    .padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                hintStringId = R.string.payment_method,
                label = viewState.selectedPaymentMethod.name,
                clickable = viewState.isFieldsEnabled,
                onClick = {
                    onAction(CreateOrder.Action.PaymentMethodClick)
                }
            )
        }
    }

    @Composable
    private fun ErrorText(
        modifier: Modifier = Modifier,
        @StringRes messageStringId: Int,
        isShown: Boolean,
    ) {
        if (isShown) {
            Text(
                modifier = modifier,
                text = stringResource(messageStringId),
                style = FoodDeliveryTheme.typography.bodySmall,
                color = FoodDeliveryTheme.colors.mainColors.error
            )
        }
    }

    @Composable
    private fun BottomAmountBar(
        viewState: CreateOrderViewState,
        onAction: (CreateOrder.Action) -> Unit,
    ) {
        FoodDeliverySurface(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = spacedBy(8.dp)
            ) {
                when (viewState.cartTotal) {
                    CartTotalUI.Loading -> {
                        BottomAmountBarLoadingContent()
                    }

                    is CartTotalUI.Success -> {
                        BottomAmountBarSuccessContent(cartTotal = viewState.cartTotal)
                    }
                }

                LoadingButton(
                    modifier = Modifier.padding(top = 8.dp),
                    textStringId = R.string.action_create_order_create_order,
                    isLoading = viewState.isLoading,
                    isEnabled = viewState.cartTotal is CartTotalUI.Success,
                    onClick = {
                        onAction(CreateOrder.Action.CreateClick)
                    }
                )
            }
        }
    }

    @Composable
    private fun BottomAmountBarLoadingContent() {
        LoadingRow(
            leftWidth = 50.dp,
            rightWidth = 32.dp,
        )
        LoadingRow(
            leftWidth = 64.dp,
            rightWidth = 40.dp,
        )
        LoadingRow(
            leftWidth = 96.dp,
            rightWidth = 80.dp,
        )
    }

    @Composable
    private fun BottomAmountBarSuccessContent(
        cartTotal: CartTotalUI.Success,
        modifier: Modifier = Modifier,
    ) {
        Column(modifier = modifier) {
            cartTotal.discount?.let { discount ->
                Row {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = stringResource(R.string.msg_order_details_discount),
                        style = FoodDeliveryTheme.typography.bodyMedium,
                        color = FoodDeliveryTheme.colors.mainColors.onSurface
                    )
                    DiscountCard(discount = discount)
                }
            }
            AnimatedVisibility(
                visible = cartTotal.deliveryCost != null,
                enter = expandVertically(),
                exit = shrinkVertically(),
            ) {
                Row {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = stringResource(R.string.msg_delivery),
                        style = FoodDeliveryTheme.typography.bodyMedium,
                        color = FoodDeliveryTheme.colors.mainColors.onSurface
                    )
                    Text(
                        text = cartTotal.deliveryCost.orEmpty(),
                        style = FoodDeliveryTheme.typography.bodyMedium,
                        color = FoodDeliveryTheme.colors.mainColors.onSurface
                    )
                }
            }
            Row {
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.msg_create_order_amount_to_pay),
                    style = FoodDeliveryTheme.typography.bodyMedium.bold,
                    color = FoodDeliveryTheme.colors.mainColors.onSurface
                )
                cartTotal.oldFinalCost?.let { oldFinalCost ->
                    Text(
                        modifier = Modifier.padding(end = 4.dp),
                        text = oldFinalCost,
                        style = FoodDeliveryTheme.typography.bodyMedium.bold,
                        color = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                        textDecoration = TextDecoration.LineThrough
                    )
                }
                Text(
                    text = cartTotal.newFinalCost,
                    style = FoodDeliveryTheme.typography.bodyMedium.bold,
                    color = FoodDeliveryTheme.colors.mainColors.onSurface
                )
            }
        }
    }

    @Composable
    private fun LoadingRow(leftWidth: Dp, rightWidth: Dp) {
        Row {
            val shape = RoundedCornerShape(4.dp)
            Shimmer(
                modifier = Modifier
                    .width(leftWidth)
                    .height(20.dp)
                    .clip(shape)
            )
            Spacer(modifier = Modifier.weight(1f))
            Shimmer(
                modifier = Modifier
                    .width(rightWidth)
                    .height(20.dp)
                    .clip(shape)
            )
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun CartTotalLoadingPreview() {
        FoodDeliveryTheme {
            Screen(
                viewState = CreateOrderViewState(
                    isDelivery = true,
                    deliveryAddress = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж, код домофона 555",
                    pickupAddress = null,
                    isAddressErrorShown = false,
                    comment = "Коммент",
                    deferredTime = "Как можно скорее",
                    deferredTimeStringId = R.string.delivery_time,
                    selectedPaymentMethod = PaymentMethodUI(
                        uuid = "",
                        name = "Наличка",
                        value = PaymentMethodValueUI(
                            value = "Наличка",
                            valueToCopy = "Наличка",
                        )
                    ),
                    isPaymentMethodErrorShown = false,
                    cartTotal = CartTotalUI.Loading,
                    isLoading = false,
                    deliveryAddressList = DeliveryAddressListUI(
                        isShown = false,
                        addressList = persistentListOf()
                    ),
                    pickupAddressList = PickupAddressListUI(
                        isShown = false,
                        addressList = persistentListOf()
                    ),
                    isDeferredTimeShown = false,
                    timePicker = TimePickerUI(
                        isShown = false,
                        minTime = TimeUI(0, 0),
                        initialTime = TimeUI(0, 0),
                    ),
                    paymentMethodList = PaymentMethodListUI(
                        isShown = false,
                        paymentMethodList = persistentListOf()
                    ),
                ),
                onAction = {}
            )
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun CartTotalSuccessPreview() {
        FoodDeliveryTheme {
            Screen(
                viewState = CreateOrderViewState(
                    isDelivery = true,
                    deliveryAddress = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж, код домофона 555",
                    pickupAddress = null,
                    isAddressErrorShown = false,
                    comment = "Коммент",
                    deferredTime = "Как можно скорее",
                    deferredTimeStringId = R.string.delivery_time,
                    selectedPaymentMethod = PaymentMethodUI(
                        uuid = "",
                        name = "Наличка",
                        value = PaymentMethodValueUI(
                            value = "Наличка",
                            valueToCopy = "Наличка",
                        )
                    ),
                    isPaymentMethodErrorShown = false,
                    cartTotal = CartTotalUI.Success(
                        discount = "10%",
                        deliveryCost = "100 ₽",
                        oldFinalCost = "700 ₽",
                        newFinalCost = "650 ₽",
                    ),
                    isLoading = false,
                    deliveryAddressList = DeliveryAddressListUI(
                        isShown = false,
                        addressList = persistentListOf()
                    ),
                    pickupAddressList = PickupAddressListUI(
                        isShown = false,
                        addressList = persistentListOf()
                    ),
                    isDeferredTimeShown = false,
                    timePicker = TimePickerUI(
                        isShown = false,
                        minTime = TimeUI(0, 0),
                        initialTime = TimeUI(0, 0),
                    ),
                    paymentMethodList = PaymentMethodListUI(
                        isShown = false,
                        paymentMethodList = persistentListOf()
                    ),
                ),
                onAction = {}
            )
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun PickUpPreview() {
        FoodDeliveryTheme {
            Screen(
                viewState = CreateOrderViewState(
                    isDelivery = false,
                    deliveryAddress = null,
                    pickupAddress = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж, код домофона 555",
                    isAddressErrorShown = false,
                    comment = "",
                    deferredTime = "18:20",
                    deferredTimeStringId = R.string.pickup_time,
                    selectedPaymentMethod = PaymentMethodUI(
                        uuid = "Коммент",
                        name = "Наличка",
                        value = PaymentMethodValueUI(
                            value = "Наличка",
                            valueToCopy = "Наличка",
                        )
                    ),
                    isPaymentMethodErrorShown = false,
                    cartTotal = CartTotalUI.Success(
                        discount = null,
                        deliveryCost = null,
                        oldFinalCost = null,
                        newFinalCost = "650 ₽",
                    ),
                    isLoading = false,
                    deliveryAddressList = DeliveryAddressListUI(
                        isShown = false,
                        addressList = persistentListOf()
                    ),
                    pickupAddressList = PickupAddressListUI(
                        isShown = false,
                        addressList = persistentListOf()
                    ),
                    isDeferredTimeShown = false,
                    timePicker = TimePickerUI(
                        isShown = false,
                        minTime = TimeUI(0, 0),
                        initialTime = TimeUI(0, 0),
                    ),
                    paymentMethodList = PaymentMethodListUI(
                        isShown = false,
                        paymentMethodList = persistentListOf()
                    ),
                ),
                onAction = {}
            )
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun LoadingPreview() {
        FoodDeliveryTheme {
            Screen(
                viewState = CreateOrderViewState(
                    isDelivery = false,
                    deliveryAddress = null,
                    pickupAddress = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж, код домофона 555",
                    isAddressErrorShown = false,
                    comment = "Коммент",
                    deferredTime = "18:20",
                    deferredTimeStringId = R.string.pickup_time,
                    selectedPaymentMethod = PaymentMethodUI(
                        uuid = "",
                        name = "Наличка",
                        value = PaymentMethodValueUI(
                            value = "Наличка",
                            valueToCopy = "Наличка",
                        )
                    ),
                    isPaymentMethodErrorShown = false,
                    cartTotal = CartTotalUI.Success(
                        discount = null,
                        deliveryCost = null,
                        oldFinalCost = null,
                        newFinalCost = "650 ₽",
                    ),
                    isLoading = true,
                    deliveryAddressList = DeliveryAddressListUI(
                        isShown = false,
                        addressList = persistentListOf()
                    ),
                    pickupAddressList = PickupAddressListUI(
                        isShown = false,
                        addressList = persistentListOf()
                    ),
                    isDeferredTimeShown = false,
                    timePicker = TimePickerUI(
                        isShown = false,
                        minTime = TimeUI(0, 0),
                        initialTime = TimeUI(0, 0),
                    ),
                    paymentMethodList = PaymentMethodListUI(
                        isShown = false,
                        paymentMethodList = persistentListOf()
                    ),
                ),
                onAction = {}
            )
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun ErrorsPreview() {
        FoodDeliveryTheme {
            Screen(
                viewState = CreateOrderViewState(
                    isDelivery = false,
                    deliveryAddress = null,
                    pickupAddress = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж, код домофона 555",
                    isAddressErrorShown = true,
                    comment = "",
                    deferredTime = "18:20",
                    deferredTimeStringId = R.string.pickup_time,
                    selectedPaymentMethod = null,
                    isPaymentMethodErrorShown = true,
                    cartTotal = CartTotalUI.Success(
                        discount = null,
                        deliveryCost = null,
                        oldFinalCost = null,
                        newFinalCost = "650 ₽",
                    ),
                    isLoading = false,
                    deliveryAddressList = DeliveryAddressListUI(
                        isShown = false,
                        addressList = persistentListOf()
                    ),
                    pickupAddressList = PickupAddressListUI(
                        isShown = false,
                        addressList = persistentListOf()
                    ),
                    isDeferredTimeShown = false,
                    timePicker = TimePickerUI(
                        isShown = false,
                        minTime = TimeUI(0, 0),
                        initialTime = TimeUI(0, 0),
                    ),
                    paymentMethodList = PaymentMethodListUI(
                        isShown = false,
                        paymentMethodList = persistentListOf()
                    ),
                ),
                onAction = {}
            )
        }
    }
}
