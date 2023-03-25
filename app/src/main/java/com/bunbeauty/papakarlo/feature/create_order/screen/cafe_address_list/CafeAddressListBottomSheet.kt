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
import com.bunbeauty.papakarlo.common.delegates.nullableArgument
import com.bunbeauty.papakarlo.common.ui.ComposeBottomSheet
import com.bunbeauty.papakarlo.common.ui.screen.FoodDeliveryLazyBottomSheet
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.feature.address.ui.AddressItem
import com.bunbeauty.shared.presentation.cafe_address_list.CafeAddressItem
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CafeAddressListBottomSheet : ComposeBottomSheet<CafeAddressItem>() {

    private var addressList by argument<List<CafeAddressItem>>()
    private var selectedCafeAddress by nullableArgument<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.setContent {
            CafeAddressListScreen(
                addressList = addressList,
                scrolledToTop = ::toggleDraggable,
                onAddressClicked = { addressItem ->
                    callback?.onResult(addressItem)
                    dismiss()
                },
                selectedCafeAddress = selectedCafeAddress
            )
        }
    }

    companion object {
        private const val TAG = "CafeAddressListBottomSheet"

        suspend fun show(
            fragmentManager: FragmentManager,
            addressList: List<CafeAddressItem>,
            selectedCafeAddress: String?,
        ) = suspendCoroutine { continuation ->
            CafeAddressListBottomSheet().apply {
                this.addressList = addressList
                this.selectedCafeAddress = selectedCafeAddress
                callback = object : Callback<CafeAddressItem> {
                    override fun onResult(result: CafeAddressItem?) {
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
    addressList: List<CafeAddressItem>,
    selectedCafeAddress: String?,
    scrolledToTop: (Boolean) -> Unit,
    onAddressClicked: (CafeAddressItem) -> Unit,
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
                isSelected = addressItem.address == selectedCafeAddress
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
                CafeAddressItem(
                    uuid = "1",
                    address = "Адрес 1",
                ),
                CafeAddressItem(
                    uuid = "2",
                    address = "Оооооооооооооооооооооооочень длинный адрес 2",
                ),
            ),
            selectedCafeAddress = "Адрес 1",
            scrolledToTop = {},
            onAddressClicked = {},
        )
    }
}
