package com.bunbeauty.papakarlo.feature.createorder.screen.cafeaddresslist

import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.FragmentManager
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.delegates.argument
import com.bunbeauty.papakarlo.common.ui.ComposeBottomSheet
import com.bunbeauty.papakarlo.common.ui.screen.bottomsheet.FoodDeliveryLazyBottomSheet
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.element.selectable.SelectableItem
import com.bunbeauty.papakarlo.feature.createorder.screen.createorder.model.SelectableCafeAddressUI
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CafeAddressListBottomSheet : ComposeBottomSheet<SelectableCafeAddressUI>() {

    private var addressList by argument<List<SelectableCafeAddressUI>>()

    @Composable
    override fun Content() {
        CafeAddressListScreen(
            addressList = addressList,
            scrolledToTop = ::toggleDraggable,
            onAddressClicked = { addressItem ->
                callback?.onResult(addressItem)
                dismiss()
            }
        )
    }

    companion object {
        private const val TAG = "CafeAddressListBottomSheet"

        suspend fun show(
            fragmentManager: FragmentManager,
            addressList: List<SelectableCafeAddressUI>
        ) = suspendCoroutine { continuation ->
            CafeAddressListBottomSheet().apply {
                this.addressList = addressList
                callback = object : Callback<SelectableCafeAddressUI> {
                    override fun onResult(result: SelectableCafeAddressUI?) {
                        continuation.resume(result)
                    }
                }
                show(fragmentManager, TAG)
            }
        }
    }
}

@Composable
private fun CafeAddressListScreen(
    addressList: List<SelectableCafeAddressUI>,
    scrolledToTop: (Boolean) -> Unit,
    onAddressClicked: (SelectableCafeAddressUI) -> Unit
) {
    FoodDeliveryLazyBottomSheet(
        titleStringId = R.string.pickup_address,
        scrolledToTop = scrolledToTop
    ) {
        items(addressList) { addressItem ->
            SelectableItem(
                title = addressItem.address,
                clickable = true,
                elevated = false,
                isSelected = addressItem.isSelected,
                onClick = {
                    onAddressClicked(addressItem)
                }
            )
        }
    }
}

@Preview
@Composable
private fun CafeAddressListScreenPreview() {
    FoodDeliveryTheme {
        CafeAddressListScreen(
            addressList = listOf(
                SelectableCafeAddressUI(
                    uuid = "1",
                    address = "Адрес 1",
                    isSelected = true
                ),
                SelectableCafeAddressUI(
                    uuid = "2",
                    address = "Оооооооооооооооооооооооочень длинный адрес 2",
                    isSelected = false
                )
            ),
            scrolledToTop = {},
            onAddressClicked = {}
        )
    }
}
