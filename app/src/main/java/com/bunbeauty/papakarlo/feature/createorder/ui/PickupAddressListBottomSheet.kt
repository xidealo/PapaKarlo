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
import com.bunbeauty.papakarlo.feature.createorder.PickupAddressListUI
import com.bunbeauty.papakarlo.feature.createorder.SelectableAddressUI
import com.bunbeauty.shared.presentation.createorder.CreateOrder
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun PickupAddressListBottomSheet(
    pickupAddressList: PickupAddressListUI,
    onAction: (CreateOrder.Action) -> Unit,
) {
    FoodDeliveryModalBottomSheet(
        onDismissRequest = {
            onAction(CreateOrder.Action.HidePickupAddressList)
        },
        isShown = pickupAddressList.isShown,
        title = stringResource(R.string.pickup_address),
    ) {
        PickupAddressListBottomSheetContent(
            addressList = pickupAddressList.addressList,
            onAction = onAction,
        )
    }
}

@Composable
private fun PickupAddressListBottomSheetContent(
    addressList: ImmutableList<SelectableAddressUI>,
    onAction: (CreateOrder.Action) -> Unit,
) {
    LazyColumn(verticalArrangement = Arrangement.Absolute.spacedBy(8.dp)) {
        items(
            items = addressList,
            key = { selectableAddress ->
                "PickupAddress ${selectableAddress.uuid}"
            },
        ) { selectableAddress ->
            SelectableItem(
                title = selectableAddress.address,
                clickable = true,
                elevated = false,
                isSelected = selectableAddress.isSelected,
                onClick = {
                    onAction(
                        CreateOrder.Action.ChangePickupAddress(
                            addressUuid = selectableAddress.uuid,
                        ),
                    )
                },
                enabled = selectableAddress.isEnabled,
            )
        }
    }
}

@Preview
@Composable
fun PickupAddressListBottomSheet() {
    PickupAddressListBottomSheetContent(
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
