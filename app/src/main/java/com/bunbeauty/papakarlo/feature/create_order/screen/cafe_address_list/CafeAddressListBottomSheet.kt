package com.bunbeauty.papakarlo.feature.create_order.screen.cafe_address_list

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.FragmentManager
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.delegates.argument
import com.bunbeauty.papakarlo.common.ui.ComposeBottomSheet
import com.bunbeauty.papakarlo.common.ui.screen.bottom_sheet.FoodDeliveryLazyBottomSheet
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import com.bunbeauty.papakarlo.feature.address.ui.AddressItem
import com.bunbeauty.shared.presentation.cafe_address_list.SelectableCafeAddressItem
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CafeAddressListBottomSheet : ComposeBottomSheet<SelectableCafeAddressItem>() {

    private var addressList by argument<List<SelectableCafeAddressItem>>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.setContentWithTheme {
            CafeAddressListScreen(
                addressList = addressList,
                scrolledToTop = ::toggleDraggable,
                onAddressClicked = { addressItem ->
                    callback?.onResult(addressItem)
                    dismiss()
                },
            )
        }
    }

    companion object {
        private const val TAG = "CafeAddressListBottomSheet"

        suspend fun show(
            fragmentManager: FragmentManager,
            addressList: List<SelectableCafeAddressItem>,
        ) = suspendCoroutine { continuation ->
            CafeAddressListBottomSheet().apply {
                this.addressList = addressList
                callback = object : Callback<SelectableCafeAddressItem> {
                    override fun onResult(result: SelectableCafeAddressItem?) {
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
    addressList: List<SelectableCafeAddressItem>,
    scrolledToTop: (Boolean) -> Unit,
    onAddressClicked: (SelectableCafeAddressItem) -> Unit,
) {
    FoodDeliveryLazyBottomSheet(
        titleStringId = R.string.cafe_address,
        scrolledToTop = scrolledToTop
    ) {
        itemsIndexed(addressList) { i, addressItem ->
            AddressItem(
                modifier = Modifier.padding(
                    top = FoodDeliveryTheme.dimensions.getItemSpaceByIndex(i)
                ),
                address = addressItem.address,
                isClickable = true,
                elevated = false,
                isSelected = addressItem.isSelected
            ) {
                onAddressClicked(addressItem)
            }
        }
    }
}

@Preview
@Composable
private fun CafeAddressListScreenPreview() {
    FoodDeliveryTheme {
        CafeAddressListScreen(
            addressList = listOf(
                SelectableCafeAddressItem(
                    uuid = "1",
                    address = "Адрес 1",
                    isSelected = true
                ),
                SelectableCafeAddressItem(
                    uuid = "2",
                    address = "Оооооооооооооооооооооооочень длинный адрес 2",
                    isSelected = false
                ),
            ),
            scrolledToTop = {},
            onAddressClicked = {},
        )
    }
}
