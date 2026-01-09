package com.bunbeauty.shared.ui.screen.createorder.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.bunbeauty.designsystem.ui.element.button.MainButton
import com.bunbeauty.designsystem.ui.element.selectable.SelectableItem
import com.bunbeauty.designsystem.ui.screen.bottomsheet.FoodDeliveryModalBottomSheet
import com.bunbeauty.shared.presentation.createorder.CreateOrder
import com.bunbeauty.shared.ui.screen.createorder.DeliveryAddressListUI
import com.bunbeauty.shared.ui.screen.createorder.SelectableAddressUI
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import papakarlo.designsystem.generated.resources.Res
import papakarlo.designsystem.generated.resources.action_add_addresses
import papakarlo.designsystem.generated.resources.delivery_address

@Composable
fun DeliveryAddressListBottomSheet(
    deliveryAddressList: DeliveryAddressListUI,
    onAction: (CreateOrder.Action) -> Unit,
) {
    FoodDeliveryModalBottomSheet(
        onDismissRequest = {
            onAction(CreateOrder.Action.HideDeliveryAddressList)
        },
        isShown = deliveryAddressList.isShown,
        title = stringResource(Res.string.delivery_address),
    ) {
        DeliveryAddressListBottomSheetContent(
            addressList = deliveryAddressList.addressList,
            onAction = onAction,
        )
    }
}

@Composable
private fun DeliveryAddressListBottomSheetContent(
    addressList: ImmutableList<SelectableAddressUI>,
    onAction: (CreateOrder.Action) -> Unit,
) {
    LazyColumn(verticalArrangement = Arrangement.Absolute.spacedBy(8.dp)) {
        items(
            items = addressList,
            key = { selectableAddress ->
                "DeliveryAddress ${selectableAddress.uuid}"
            },
        ) { selectableAddress ->
            SelectableItem(
                title = selectableAddress.address,
                clickable = true,
                elevated = false,
                isSelected = selectableAddress.isSelected,
                onClick = {
                    onAction(CreateOrder.Action.ChangeDeliveryAddress(addressUuid = selectableAddress.uuid))
                },
                enabled = selectableAddress.isEnabled,
            )
        }
        item(key = "AddAddress") {
            MainButton(
                textStringId = Res.string.action_add_addresses,
                onClick = {
                    onAction(CreateOrder.Action.AddAddressClick)
                },
            )
        }
    }
}

@Preview
@Composable
private fun DeliveryAddressListBottomPreview() {
    DeliveryAddressListBottomSheetContent(
        addressList =
            persistentListOf(
                SelectableAddressUI(
                    uuid = "1",
                    address = "улица Чапаева, д. 22А",
                    isSelected = false,
                    isEnabled = true,
                ),
                SelectableAddressUI(
                    uuid = "2",
                    address = "улица Чапаева, д. 22А кв. 55, 1 подъезд, 1 этаж",
                    isSelected = true,
                    isEnabled = true,
                ),
                SelectableAddressUI(
                    uuid = "3",
                    address = "улица Чапаева, д. 22А кв. 55, 1 подъезд, 2 этаж",
                    isSelected = true,
                    isEnabled = false,
                ),
            ),
        onAction = {},
    )
}
