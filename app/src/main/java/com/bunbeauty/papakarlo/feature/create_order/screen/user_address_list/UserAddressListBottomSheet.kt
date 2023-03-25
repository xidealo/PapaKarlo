package com.bunbeauty.papakarlo.feature.create_order.screen.user_address_list

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
import com.bunbeauty.papakarlo.common.ui.element.button.MainButton
import com.bunbeauty.papakarlo.common.ui.screen.FoodDeliveryLazyBottomSheet
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.feature.address.model.UserAddressItem
import com.bunbeauty.papakarlo.feature.address.ui.AddressItem
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UserAddressListBottomSheet : ComposeBottomSheet<UserAddressListResult>() {

    private var addressList by argument<List<UserAddressItem>>()
    private var selectedUserAddressUuid by nullableArgument<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.setContent {
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
                },
                selectedUserAddressUuid = selectedUserAddressUuid
            )
        }
    }

    companion object {
        private const val TAG = "UserAddressListBottomSheet"

        suspend fun show(
            fragmentManager: FragmentManager,
            addressList: List<UserAddressItem>,
            selectedUserAddressUuid: String?,
        ) = suspendCoroutine { continuation ->
            UserAddressListBottomSheet().apply {
                this.addressList = addressList
                this.selectedUserAddressUuid = selectedUserAddressUuid
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
    selectedUserAddressUuid: String?,
    scrolledToTop: (Boolean) -> Unit,
    onAddressClicked: (UserAddressItem) -> Unit,
    onAddAddressClicked: () -> Unit,
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
            AddressItem(
                modifier = Modifier.padding(
                    top = FoodDeliveryTheme.dimensions.getItemSpaceByIndex(i)
                ),
                address = addressItem.address,
                isClickable = true,
                elevated = false,
                isSelected = addressItem.uuid == selectedUserAddressUuid
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
                ),
                UserAddressItem(
                    uuid = "2",
                    address = "Адрес 2",
                ),
                UserAddressItem(
                    uuid = "3",
                    address = "Оооооооооооооооооочень длинный адрес 3",
                ),
            ),
            selectedUserAddressUuid = "Адрес 2",
            scrolledToTop = {},
            onAddressClicked = {},
            onAddAddressClicked = {}
        )
    }
}
