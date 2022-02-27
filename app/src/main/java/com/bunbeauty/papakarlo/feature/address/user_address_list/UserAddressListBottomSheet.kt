package com.bunbeauty.papakarlo.feature.address.user_address_list

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.common.Constants.RESULT_USER_ADDRESS_KEY
import com.bunbeauty.common.Constants.USER_ADDRESS_REQUEST_KEY
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseBottomSheet
import com.bunbeauty.papakarlo.common.delegates.argument
import com.bunbeauty.papakarlo.compose.element.MainIconButton
import com.bunbeauty.papakarlo.compose.element.Title
import com.bunbeauty.papakarlo.compose.item.AddressItem
import com.bunbeauty.papakarlo.compose.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.databinding.BottomSheetUserAddressListBinding
import com.bunbeauty.papakarlo.extensions.compose
import com.bunbeauty.papakarlo.feature.address.AddressItemModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserAddressListBottomSheet : BaseBottomSheet(R.layout.bottom_sheet_user_address_list) {

    override val viewModel: UserAddressListViewModel by viewModel()
    override val viewBinding by viewBinding(BottomSheetUserAddressListBinding::bind)

    private val isClickable: Boolean by argument()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.bottomSheetUserAddressesCvMain.compose {
            val addressItemModelList by viewModel.addressItemModelList.collectAsState()
            UserAddressListScreen(
                addressItemModelList,
                this@UserAddressListBottomSheet.isClickable
            )
        }
    }

    @Composable
    private fun UserAddressListScreen(
        addressItemModelList: List<AddressItemModel>,
        isClickable: Boolean
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column {
                Title(textStringId = R.string.title_user_addresses)
                LazyColumn(
                    contentPadding = PaddingValues(
                        start = FoodDeliveryTheme.dimensions.mediumSpace,
                        end = FoodDeliveryTheme.dimensions.mediumSpace,
                        bottom = FoodDeliveryTheme.dimensions.mediumSpace,
                    )
                ) {
                    itemsIndexed(addressItemModelList) { i, addressItemModel ->
                        AddressItem(
                            modifier = Modifier.padding(
                                top = FoodDeliveryTheme.dimensions.getTopItemSpaceByIndex(i),
                                end = FoodDeliveryTheme.dimensions.addressEndSpace
                            ),
                            address = addressItemModel.address,
                            isClickable = isClickable
                        ) {
                            setFragmentResult(
                                USER_ADDRESS_REQUEST_KEY,
                                bundleOf(RESULT_USER_ADDRESS_KEY to addressItemModel.uuid)
                            )
                            viewModel.goBack()
                        }
                    }
                }
            }
            MainIconButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(
                        end = FoodDeliveryTheme.dimensions.mediumSpace,
                        bottom = FoodDeliveryTheme.dimensions.mediumSpace,
                    ),
                iconId = R.drawable.ic_add,
                iconDescriptionStringId = R.string.description_ic_add
            ) {
                viewModel.onCreateAddressClicked()
            }
        }
    }

    @Preview
    @Composable
    private fun UserAddressListScreenPreview() {
        UserAddressListScreen(
            addressItemModelList = listOf(
                AddressItemModel(
                    uuid = "",
                    address = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж"
                ),
                AddressItemModel(
                    uuid = "",
                    address = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж"
                ),
                AddressItemModel(
                    uuid = "",
                    address = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж"
                ),
                AddressItemModel(
                    uuid = "",
                    address = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж"
                ),
            ),
            isClickable = false
        )
    }
}