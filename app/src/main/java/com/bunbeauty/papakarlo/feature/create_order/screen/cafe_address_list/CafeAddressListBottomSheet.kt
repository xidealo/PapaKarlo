package com.bunbeauty.papakarlo.feature.create_order.screen.cafe_address_list

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.FragmentManager
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.delegates.argument
import com.bunbeauty.papakarlo.common.ui.ComposeBottomSheet
import com.bunbeauty.papakarlo.common.ui.element.Title
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.feature.address.ui.AddressItem
import com.bunbeauty.shared.presentation.cafe_address_list.CafeAddressItem
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CafeAddressListBottomSheet : ComposeBottomSheet<CafeAddressItem>() {

    private var addressList by argument<List<CafeAddressItem>>()
    private var selectedCafeAddress by argument<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        binding.root.setContent {
            CafeAddressListScreen(
                addressList = addressList,
                scrolledToTop = { isScrolledToTop ->
                    behavior.isDraggable = isScrolledToTop
                },
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
            selectedCafeAddress: String,
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
    selectedCafeAddress: String,
    scrolledToTop: (Boolean) -> Unit,
    onAddressClicked: (CafeAddressItem) -> Unit,
) {
    val listState = rememberLazyListState()
    val itemPosition by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex
        }
    }
    LaunchedEffect(Unit) {
        snapshotFlow { itemPosition }.collect { itemPosition ->
            scrolledToTop(itemPosition == 0)
        }
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        Title(
            modifier = Modifier
                .padding(top = FoodDeliveryTheme.dimensions.mediumSpace)
                .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace),
            text = stringResource(R.string.cafe_address),
        )
        LazyColumn(
            modifier = Modifier.weight(1f, false),
            state = listState,
            contentPadding = PaddingValues(FoodDeliveryTheme.dimensions.mediumSpace)
        ) {
            itemsIndexed(addressList) { i, addressItem ->
                AddressItem(
                    modifier = Modifier.padding(
                        top = FoodDeliveryTheme.dimensions.getItemSpaceByIndex(i)
                    ),
                    address = addressItem.address,
                    isClickable = true,
                    hasShadow = false,
                    isSelected = addressItem.address == selectedCafeAddress
                ) {
                    onAddressClicked(addressItem)
                }
            }
        }
    }
}
