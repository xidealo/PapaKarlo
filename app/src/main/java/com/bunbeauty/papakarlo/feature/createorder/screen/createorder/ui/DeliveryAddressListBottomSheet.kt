package com.bunbeauty.papakarlo.feature.createorder.screen.createorder.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.element.button.MainButton
import com.bunbeauty.papakarlo.common.ui.element.selectable.SelectableItem
import com.bunbeauty.papakarlo.common.ui.screen.bottomsheet.FoodDeliveryModalBottomSheet
import com.bunbeauty.papakarlo.feature.createorder.screen.createorder.DeliveryAddressListUI
import com.bunbeauty.papakarlo.feature.createorder.screen.createorder.SelectableAddressUI
import com.bunbeauty.shared.presentation.createorder.CreateOrder
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun DeliveryAddressListBottomSheet(
    deliveryAddressList: DeliveryAddressListUI,
    onAction: (CreateOrder.Action) -> Unit
) {
    FoodDeliveryModalBottomSheet(
        onDismissRequest = {
            onAction(CreateOrder.Action.HideDeliveryAddressList)
        },
        isShown = deliveryAddressList.isShown,
        title = stringResource(R.string.delivery_address)
    ) {
        DeliveryAddressListBottomSheetContent(
            addressList = deliveryAddressList.addressList,
            onAction = onAction
        )
    }
}

@Composable
private fun DeliveryAddressListBottomSheetContent(
    addressList: ImmutableList<SelectableAddressUI>,
    onAction: (CreateOrder.Action) -> Unit
) {
    LazyColumn(verticalArrangement = Arrangement.Absolute.spacedBy(8.dp)) {
        items(
            items = addressList,
            key = { selectableAddress ->
                "DeliveryAddress ${selectableAddress.uuid}"
            }
        ) { selectableAddress ->
            SelectableItem(
                title = selectableAddress.address,
                clickable = true,
                elevated = false,
                isSelected = selectableAddress.isSelected,
                onClick = {
                    onAction(CreateOrder.Action.ChangeDeliveryAddress(addressUuid = selectableAddress.uuid))
                }
            )
        }
        item(key = "AddAddress") {
            MainButton(
                textStringId = R.string.action_add_addresses,
                onClick = {
                    onAction(CreateOrder.Action.AddAddressClick)
                }
            )
        }
    }
}

@Preview
@Composable
private fun DeliveryAddressListBottomPreview() {
    DeliveryAddressListBottomSheetContent(
        addressList = persistentListOf(
            SelectableAddressUI(
                uuid = "1",
                address = "улица Чапаева, д. 22А",
                isSelected = false
            ),
            SelectableAddressUI(
                uuid = "2",
                address = "улица Чапаева, д. 22А кв. 55, 1 подъезд, 1 этаж",
                isSelected = true
            )
        ),
        onAction = {}
    )
}
