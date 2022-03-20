package com.bunbeauty.papakarlo.feature.create_order

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.setFragmentResultListener
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.common.Constants.CAFE_ADDRESS_REQUEST_KEY
import com.bunbeauty.common.Constants.COMMENT_REQUEST_KEY
import com.bunbeauty.common.Constants.DEFERRED_TIME_REQUEST_KEY
import com.bunbeauty.common.Constants.RESULT_CAFE_ADDRESS_KEY
import com.bunbeauty.common.Constants.RESULT_COMMENT_KEY
import com.bunbeauty.common.Constants.RESULT_USER_ADDRESS_KEY
import com.bunbeauty.common.Constants.SELECTED_DEFERRED_TIME_KEY
import com.bunbeauty.common.Constants.USER_ADDRESS_REQUEST_KEY
import com.bunbeauty.domain.model.date_time.Time
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.compose.card.NavigationCard
import com.bunbeauty.papakarlo.compose.card.TextNavigationCard
import com.bunbeauty.papakarlo.compose.custom.Switcher
import com.bunbeauty.papakarlo.compose.element.BlurLine
import com.bunbeauty.papakarlo.compose.element.LoadingButton
import com.bunbeauty.papakarlo.compose.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.databinding.FragmentCreateOrderBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateOrderFragment : BaseFragment(R.layout.fragment_create_order) {

    override val viewModel: CreateOrderViewModel by viewModel()
    override val viewBinding by viewBinding(FragmentCreateOrderBinding::bind)

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.fragmentCreateOrderCvMain.setContent {
            val orderCreationUI by viewModel.orderCreationUI.collectAsState()
            CreateOrderScreen(orderCreationUI)
        }
        setFragmentResultListener(USER_ADDRESS_REQUEST_KEY) { _, bundle ->
            bundle.getString(RESULT_USER_ADDRESS_KEY)?.let { userAddressUuid ->
                viewModel.onUserAddressChanged(userAddressUuid)
            }
        }
        setFragmentResultListener(CAFE_ADDRESS_REQUEST_KEY) { _, bundle ->
            bundle.getString(RESULT_CAFE_ADDRESS_KEY)?.let { cafUuid ->
                viewModel.onCafeAddressChanged(cafUuid)
            }
        }
        setFragmentResultListener(COMMENT_REQUEST_KEY) { _, bundle ->
            bundle.getString(RESULT_COMMENT_KEY)?.let { comment ->
                viewModel.onCommentChanged(comment)
            }
        }
        setFragmentResultListener(DEFERRED_TIME_REQUEST_KEY) { _, bundle ->
            bundle.getParcelable<Time>(SELECTED_DEFERRED_TIME_KEY).let { selectedDeferredTime ->
                viewModel.onDeferredTimeSelected(selectedDeferredTime)
            }
        }
    }

    @Composable
    private fun CreateOrderScreen(orderCreationUI: OrderCreationUI) {
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
                        position = orderCreationUI.switcherPosition
                    ) { changedPosition ->
                        viewModel.onSwitcherPositionChanged(changedPosition)
                    }
                    AddressCard(orderCreationUI)
                    CommentCard(orderCreationUI)
                    DeferredTimeCard(orderCreationUI)
                }
                BlurLine(modifier = Modifier.align(Alignment.BottomCenter))
            }
            BottomAmountBar(orderCreationUI)
        }
    }

    @Composable
    private fun AddressCard(orderCreationUI: OrderCreationUI) {
        if (orderCreationUI.address == null) {
            val addressStringId = if (orderCreationUI.isDelivery) {
                R.string.action_create_order_delivery_address
            } else {
                R.string.action_create_order_cafe_address
            }
            NavigationCard(
                modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                labelStringId = addressStringId,
                isClickable = !orderCreationUI.isLoading
            ) {
                viewModel.onAddAddressClicked()
            }
        } else {
            val addressHintStringId = if (orderCreationUI.isDelivery) {
                R.string.hint_create_order_delivery_address
            } else {
                R.string.hint_create_order_cafe_address
            }
            TextNavigationCard(
                modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                hintStringId = addressHintStringId,
                label = orderCreationUI.address,
                isClickable = !orderCreationUI.isLoading
            ) {
                viewModel.onChangeAddressClicked()
            }
        }
    }

    @Composable
    private fun CommentCard(orderCreationUI: OrderCreationUI) {
        if (orderCreationUI.comment == null) {
            NavigationCard(
                modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                labelStringId = R.string.action_create_order_comment,
                isClickable = !orderCreationUI.isLoading
            ) {
                viewModel.onAddCommentClicked()
            }
        } else {
            TextNavigationCard(
                modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                hintStringId = R.string.hint_create_order_comment,
                label = orderCreationUI.comment,
                isClickable = !orderCreationUI.isLoading
            ) {
                viewModel.onEditCommentClicked()
            }
        }
    }

    @Composable
    private fun DeferredTimeCard(orderCreationUI: OrderCreationUI) {
        val deferredTimeStringId = if (orderCreationUI.isDelivery) {
            R.string.hint_create_order_delivery_time
        } else {
            R.string.hint_create_order_pickup_time
        }
        TextNavigationCard(
            modifier = Modifier.padding(vertical = FoodDeliveryTheme.dimensions.smallSpace),
            hintStringId = deferredTimeStringId,
            label = orderCreationUI.deferredTime,
            isClickable = !orderCreationUI.isLoading
        ) {
            viewModel.onDeferredTimeClicked()
        }
    }

    @Composable
    private fun BottomAmountBar(orderCreationUI: OrderCreationUI) {
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
                if (orderCreationUI.totalCost != null) {
                    Text(
                        text = orderCreationUI.totalCost,
                        style = FoodDeliveryTheme.typography.body1,
                        color = FoodDeliveryTheme.colors.onSurface
                    )
                }
            }
            if (orderCreationUI.isDelivery) {
                Row(modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.smallSpace)) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = stringResource(R.string.msg_create_order_delivery_cost),
                        style = FoodDeliveryTheme.typography.body1,
                        color = FoodDeliveryTheme.colors.onSurface
                    )
                    if (orderCreationUI.deliveryCost != null) {
                        Text(
                            text = orderCreationUI.deliveryCost,
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
                if (orderCreationUI.amountToPay != null) {
                    Text(
                        text = orderCreationUI.amountToPay,
                        style = FoodDeliveryTheme.typography.h2,
                        color = FoodDeliveryTheme.colors.onSurface
                    )
                }
            }
            LoadingButton(
                modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.mediumSpace),
                textStringId = R.string.action_create_order_create_order,
                isLoading = orderCreationUI.isLoading
            ) {
                viewModel.onCreateOrderClicked(orderCreationUI)
            }
        }
    }

    @Preview
    @Composable
    private fun CreateOrderEmptyDeliveryScreenPreview() {
        CreateOrderScreen(
            orderCreationUI = OrderCreationUI(
                isDelivery = true,
                address = null,
                comment = null,
                deferredTime = "Как можно скорее",
                totalCost = null,
                deliveryCost = null,
                amountToPay = null,
                isLoading = false
            )
        )
    }

    @Preview
    @Composable
    private fun CreateOrderDeliveryScreenPreview() {
        CreateOrderScreen(
            orderCreationUI = OrderCreationUI(
                isDelivery = true,
                address = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж, код домофона 555",
                comment = "Побыстрее пожалуйста, кушать очень хочу",
                deferredTime = "Как можно скорее",
                totalCost = "250 ₽",
                deliveryCost = "100 ₽",
                amountToPay = "350 ₽",
                isLoading = false
            )
        )
    }

    @Preview
    @Composable
    private fun CreateOrderEmptyPickupScreenPreview() {
        CreateOrderScreen(
            orderCreationUI = OrderCreationUI(
                isDelivery = false,
                address = null,
                comment = null,
                deferredTime = "10:30",
                totalCost = null,
                deliveryCost = null,
                amountToPay = null,
                isLoading = false
            )
        )
    }

    @Preview
    @Composable
    private fun CreateOrderPickupScreenPreview() {
        CreateOrderScreen(
            orderCreationUI = OrderCreationUI(
                isDelivery = false,
                address = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж, код домофона 555",
                comment = "Побыстрее пожалуйста, кушать очень хочу",
                deferredTime = "Как можно скорее",
                totalCost = "250 ₽",
                deliveryCost = "100 ₽",
                amountToPay = "350 ₽",
                isLoading = true
            )
        )
    }
}