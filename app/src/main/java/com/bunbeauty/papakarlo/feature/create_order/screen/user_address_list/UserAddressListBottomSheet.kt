package com.bunbeauty.papakarlo.feature.create_order.screen.user_address_list

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.FragmentManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.delegates.argument
import com.bunbeauty.papakarlo.common.ui.element.MainButton
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.databinding.BottomSheetBinding
import com.bunbeauty.papakarlo.feature.address.ui.AddressItem
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UserAddressListBottomSheet : BottomSheetDialogFragment() {

    private var addressList by argument<List<UserAddressItem>>()
    private var callback: Callback? = null

    private val binding by viewBinding(BottomSheetBinding::bind)
    private val behavior by lazy { (dialog as BottomSheetDialog).behavior }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet, container, true)
    }

    override fun getTheme(): Int {
        return R.style.BottomSheetDialogStyle
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        behavior.state = BottomSheetBehavior.STATE_EXPANDED
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
                }
            )
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        callback?.onResult(UserAddressListResult.Cancel)
    }

    private interface Callback {
        fun onResult(result: UserAddressListResult)
    }

    companion object {
        private const val TAG = "UserAddressListBottomSheet"

        suspend fun show(
            fragmentManager: FragmentManager,
            addressList: List<UserAddressItem>
        ) = suspendCoroutine { continuation ->
            UserAddressListBottomSheet().apply {
                this.addressList = addressList
                callback = object : Callback {
                    override fun onResult(result: UserAddressListResult) {
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
    onAddAddressClicked: () -> Unit,
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
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = FoodDeliveryTheme.dimensions.mediumSpace)
                .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace),
            text = stringResource(R.string.delivery_address),
            style = FoodDeliveryTheme.typography.h2,
            color = FoodDeliveryTheme.colors.onSurface
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
                    isClickable = true
                ) {
                    onAddressClicked(addressItem)
                }
            }
        }
        MainButton(
            modifier = Modifier
                .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace)
                .padding(bottom = FoodDeliveryTheme.dimensions.mediumSpace),
            textStringId = R.string.action_add_addresses,
            onClick = onAddAddressClicked
        )
    }
}