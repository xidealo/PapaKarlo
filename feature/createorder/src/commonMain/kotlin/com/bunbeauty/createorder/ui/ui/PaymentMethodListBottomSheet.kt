package com.bunbeauty.createorder.ui.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.bunbeauty.createorder.presentation.createorder.CreateOrder
import com.bunbeauty.createorder.ui.PaymentMethodListUI
import com.bunbeauty.createorder.ui.SelectablePaymentMethodUI
import com.bunbeauty.designsystem.ui.element.selectable.SelectableItem
import com.bunbeauty.designsystem.ui.screen.bottomsheet.FoodDeliveryModalBottomSheet
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import papakarlo.designsystem.generated.resources.Res
import papakarlo.designsystem.generated.resources.payment_method

@Composable
fun PaymentMethodListBottomSheet(
    paymentMethodList: PaymentMethodListUI,
    onAction: (CreateOrder.Action) -> Unit,
) {
    FoodDeliveryModalBottomSheet(
        onDismissRequest = {
            onAction(CreateOrder.Action.HidePaymentMethodList)
        },
        isShown = paymentMethodList.isShown,
        title = stringResource(Res.string.payment_method),
    ) {
        PaymentMethodListBottomSheetContent(
            paymentMethodList = paymentMethodList.paymentMethodList,
            onAction = onAction,
        )
    }
}

@Composable
private fun PaymentMethodListBottomSheetContent(
    paymentMethodList: ImmutableList<SelectablePaymentMethodUI>,
    onAction: (CreateOrder.Action) -> Unit,
) {
    LazyColumn(verticalArrangement = Arrangement.Absolute.spacedBy(8.dp)) {
        items(
            items = paymentMethodList,
            key = { selectableAddress ->
                "PaymentMethod ${selectableAddress.uuid}"
            },
        ) { selectablePaymentMethod ->
            SelectableItem(
                title = selectablePaymentMethod.name,
                clickable = true,
                elevated = false,
                isSelected = selectablePaymentMethod.isSelected,
                onClick = {
                    onAction(
                        CreateOrder.Action.ChangePaymentMethod(
                            paymentMethodUuid = selectablePaymentMethod.uuid,
                        ),
                    )
                },
                enabled = true,
            )
        }
    }
}

@Preview
@Composable
private fun PaymentMethodListBottomSheetPreview() {
    PaymentMethodListBottomSheetContent(
        paymentMethodList =
            persistentListOf(
                SelectablePaymentMethodUI(
                    uuid = "1",
                    name = "Налика",
                    isSelected = false,
                ),
                SelectablePaymentMethodUI(
                    uuid = "2",
                    name = "Картой курьеру",
                    isSelected = true,
                ),
            ),
        onAction = {},
    )
}
