package com.bunbeauty.papakarlo.feature.createorder.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.element.selectable.SelectableItem
import com.bunbeauty.papakarlo.common.ui.screen.bottomsheet.FoodDeliveryModalBottomSheet
import com.bunbeauty.papakarlo.feature.createorder.PaymentMethodListUI
import com.bunbeauty.papakarlo.feature.createorder.SelectablePaymentMethodUI
import com.bunbeauty.shared.presentation.createorder.CreateOrder
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun PaymentMethodListBottomSheet(
    paymentMethodList: PaymentMethodListUI,
    onAction: (CreateOrder.Action) -> Unit
) {
    FoodDeliveryModalBottomSheet(
        onDismissRequest = {
            onAction(CreateOrder.Action.HidePaymentMethodList)
        },
        isShown = paymentMethodList.isShown,
        title = stringResource(R.string.payment_method)
    ) {
        PaymentMethodListBottomSheetContent(
            paymentMethodList = paymentMethodList.paymentMethodList,
            onAction = onAction
        )
    }
}

@Composable
private fun PaymentMethodListBottomSheetContent(
    paymentMethodList: ImmutableList<SelectablePaymentMethodUI>,
    onAction: (CreateOrder.Action) -> Unit
) {
    LazyColumn(verticalArrangement = Arrangement.Absolute.spacedBy(8.dp)) {
        items(
            items = paymentMethodList,
            key = { selectableAddress ->
                "PaymentMethod ${selectableAddress.uuid}"
            }
        ) { selectablePaymentMethod ->
            SelectableItem(
                title = selectablePaymentMethod.name,
                clickable = true,
                elevated = false,
                isSelected = selectablePaymentMethod.isSelected,
                onClick = {
                    onAction(
                        CreateOrder.Action.ChangePaymentMethod(
                            paymentMethodUuid = selectablePaymentMethod.uuid
                        )
                    )
                }
            )
        }
    }
}

@Preview
@Composable
private fun PaymentMethodListBottomSheetPreview() {
    PaymentMethodListBottomSheetContent(
        paymentMethodList = persistentListOf(
            SelectablePaymentMethodUI(
                uuid = "1",
                name = "Налика",
                isSelected = false
            ),
            SelectablePaymentMethodUI(
                uuid = "2",
                name = "Картой курьеру",
                isSelected = true
            )
        ),
        onAction = {}
    )
}
