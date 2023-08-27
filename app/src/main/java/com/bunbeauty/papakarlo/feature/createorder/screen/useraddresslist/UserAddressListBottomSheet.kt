package com.bunbeauty.papakarlo.feature.createorder.screen.useraddresslist

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
import com.bunbeauty.papakarlo.common.ui.element.button.MainButton
import com.bunbeauty.papakarlo.common.ui.screen.bottomsheet.FoodDeliveryLazyBottomSheet
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import com.bunbeauty.papakarlo.feature.address.model.UserAddressItem
import com.bunbeauty.papakarlo.feature.address.ui.SelectableItemView
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UserAddressListBottomSheet : ComposeBottomSheet<UserAddressListResult>() {

    private var addressList by argument<List<UserAddressItem>>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.setContentWithTheme {
            UserAddressListScreen(
                addressList = addressList,
                scrolledToTop = { isScrolledToTop ->
                    behavior.isDraggable = isScrolledToTop
                },
                onAddressClicked = { addressItem ->
                    callback?.onResult(UserAddressListResult.AddressSelected(addressItem))
                    dismiss()
                },
                onAddAddressClicked = {
                    callback?.onResult(UserAddressListResult.AddNewAddress)
                    dismiss()
                }
            )
        }
    }

    companion object {
        private const val TAG = "UserAddressListBottomSheet"

        suspend fun show(
            fragmentManager: FragmentManager,
            addressList: List<UserAddressItem>
        ) = suspendCoroutine { continuation ->
            UserAddressListBottomSheet().apply {
                this.addressList = addressList
                callback = object : Callback<UserAddressListResult> {
                    override fun onResult(result: UserAddressListResult?) {
                        continuation.resume(result)
                    }
                }
                show(fragmentManager, TAG)
            }
        }
    }
}

@Composable
private fun UserAddressListScreen(
    addressList: List<UserAddressItem>,
    scrolledToTop: (Boolean) -> Unit,
    onAddressClicked: (UserAddressItem) -> Unit,
    onAddAddressClicked: () -> Unit
) {
    FoodDeliveryLazyBottomSheet(
        titleStringId = R.string.delivery_address,
        scrolledToTop = scrolledToTop,
        bottomContent = {
            MainButton(
                textStringId = R.string.action_add_addresses,
                onClick = onAddAddressClicked
            )
        }
    ) {
        itemsIndexed(addressList) { i, addressItem ->
            SelectableItemView(
                modifier = Modifier.padding(
                    top = FoodDeliveryTheme.dimensions.getItemSpaceByIndex(i)
                ),
                title = addressItem.address,
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
private fun UserAddressListScreenPreview() {
    FoodDeliveryTheme {
        UserAddressListScreen(
            addressList = listOf(
                UserAddressItem(
                    uuid = "1",
                    address = "Адрес 1",
                    isSelected = false
                ),
                UserAddressItem(
                    uuid = "2",
                    address = "Адрес 2",
                    isSelected = false
                ),
                UserAddressItem(
                    uuid = "3",
                    address = "Оооооооооооооооооочень длинный адрес 3",
                    isSelected = true
                )
            ),
            scrolledToTop = {},
            onAddressClicked = {},
            onAddAddressClicked = {}
        )
    }
}
