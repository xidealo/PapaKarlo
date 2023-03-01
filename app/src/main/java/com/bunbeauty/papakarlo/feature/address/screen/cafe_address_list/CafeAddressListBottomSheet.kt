package com.bunbeauty.papakarlo.feature.address.screen.cafe_address_list

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseBottomSheet
import com.bunbeauty.papakarlo.common.ui.element.CircularProgressBar
import com.bunbeauty.papakarlo.common.ui.element.Title
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.bottomSheetShape
import com.bunbeauty.papakarlo.databinding.BottomSheetCafeAddressListBinding
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import com.bunbeauty.papakarlo.feature.address.model.AddressItem
import com.bunbeauty.papakarlo.feature.address.ui.AddressItem
import com.bunbeauty.shared.Constants.CAFE_ADDRESS_REQUEST_KEY
import com.bunbeauty.shared.Constants.RESULT_CAFE_ADDRESS_KEY
import org.koin.androidx.viewmodel.ext.android.viewModel

class CafeAddressListBottomSheet : BaseBottomSheet(R.layout.bottom_sheet_cafe_address_list) {

    override val viewModel: CafeAddressListViewModel by viewModel()
    override val viewBinding by viewBinding(BottomSheetCafeAddressListBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.bottomSheetCafeAddressListCvMain.setContentWithTheme {
            val cafeAddressList by viewModel.cafeAddressList.collectAsState()
            CafeAddressListScreen(cafeAddressList)
        }
    }

    @Composable
    private fun CafeAddressListScreen(cafeAddressList: List<AddressItem>?) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(bottomSheetShape)
                .background(FoodDeliveryTheme.colors.surface)
        ) {
            if (cafeAddressList == null) {
                CafeAddressListLoadingScreen()
            } else {
                CafeAddressListSuccessScreen(cafeAddressList)
            }
        }
    }

    @Composable
    private fun CafeAddressListSuccessScreen(cafeAddressList: List<AddressItem>) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Title(textStringId = R.string.title_cafe_addresses)
            LazyColumn(
                contentPadding = PaddingValues(
                    start = FoodDeliveryTheme.dimensions.mediumSpace,
                    end = FoodDeliveryTheme.dimensions.mediumSpace,
                    bottom = FoodDeliveryTheme.dimensions.mediumSpace,
                )
            ) {
                itemsIndexed(cafeAddressList) { i, addressItem ->
                    AddressItem(
                        modifier = Modifier.padding(
                            top = FoodDeliveryTheme.dimensions.getItemSpaceByIndex(i)
                        ),
                        address = addressItem.address,
                        isClickable = true,
                        hasShadow = false
                    ) {
                        setFragmentResult(
                            CAFE_ADDRESS_REQUEST_KEY,
                            bundleOf(RESULT_CAFE_ADDRESS_KEY to addressItem.uuid)
                        )
                        viewModel.goBack()
                    }
                }
            }
        }
    }

    @Composable
    private fun CafeAddressListLoadingScreen() {
        Box(modifier = Modifier.fillMaxWidth()) {
            CircularProgressBar(
                modifier = Modifier
                    .padding(FoodDeliveryTheme.dimensions.mediumSpace)
                    .align(Alignment.Center)
            )
        }
    }

    private val addressItemModel = AddressItem(
        uuid = "",
        address = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж",
        isClickable = true
    )

    @Preview
    @Composable
    private fun CafeAddressListSuccessScreenPreview() {
        CafeAddressListScreen(
            listOf(
                addressItemModel,
                addressItemModel,
                addressItemModel
            )
        )
    }

    @Preview
    @Composable
    private fun CafeAddressListLoadingScreenPreview() {
        CafeAddressListScreen(null)
    }
}
