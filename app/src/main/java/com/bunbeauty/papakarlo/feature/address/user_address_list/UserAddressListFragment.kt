package com.bunbeauty.papakarlo.feature.address.user_address_list

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import by.kirich1409.viewbindingdelegate.viewBinding
import core_common.Constants.RESULT_USER_ADDRESS_KEY
import core_common.Constants.USER_ADDRESS_REQUEST_KEY
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.common.state.State
import com.bunbeauty.papakarlo.compose.element.MainButton
import com.bunbeauty.papakarlo.compose.item.AddressItem
import com.bunbeauty.papakarlo.compose.screen.EmptyScreen
import com.bunbeauty.papakarlo.compose.screen.LoadingScreen
import com.bunbeauty.papakarlo.compose.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.databinding.BottomSheetUserAddressListBinding
import com.bunbeauty.papakarlo.extensions.compose
import com.bunbeauty.papakarlo.feature.address.AddressItemModel
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class UserAddressListFragment : BaseFragment(R.layout.bottom_sheet_user_address_list) {

    override val viewModel: UserAddressListViewModel by stateViewModel(state = {
        arguments ?: bundleOf()
    })
    override val viewBinding by viewBinding(BottomSheetUserAddressListBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.fragmentUserAddressListCvMain.compose {
            val addressItemModelListState by viewModel.addressItemModelList.collectAsState()
            UserAddressListScreen(addressItemModelListState)
        }
    }

    @Composable
    private fun UserAddressListScreen(addressItemModelListState: State<List<AddressItemModel>>) {
        when (addressItemModelListState) {
            is State.Success -> {
                UserAddressListSuccessScreen(addressItemModelListState.data)
            }
            is State.Loading -> {
                LoadingScreen()
            }
            is State.Empty -> {
                EmptyScreen(
                    imageId = R.drawable.empty_page,
                    imageDescriptionId = R.string.description_cafe_addresses_empty,
                    textId = R.string.msg_my_addresses_empty,
                    buttonTextId = R.string.action_add_addresses,
                    onClick = viewModel::onCreateAddressClicked
                )
            }
        }
    }

    @Composable
    private fun UserAddressListSuccessScreen(addressItemModelList: List<AddressItemModel>) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(FoodDeliveryTheme.colors.background)
                .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace)
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(vertical = FoodDeliveryTheme.dimensions.mediumSpace)
            ) {
                itemsIndexed(addressItemModelList) { i, addressItemModel ->
                    AddressItem(
                        modifier = Modifier.padding(
                            top = FoodDeliveryTheme.dimensions.getItemSpaceByIndex(i)
                        ),
                        address = addressItemModel.address,
                        isClickable = addressItemModel.isClickable
                    ) {
                        setFragmentResult(
                            USER_ADDRESS_REQUEST_KEY,
                            bundleOf(RESULT_USER_ADDRESS_KEY to addressItemModel.uuid)
                        )
                        viewModel.goBack()
                    }
                }
            }
            MainButton(
                modifier = Modifier.padding(bottom = FoodDeliveryTheme.dimensions.mediumSpace),
                textStringId = R.string.action_add_addresses
            ) {
                viewModel.onCreateAddressClicked()
            }
        }
    }

    @Preview
    @Composable
    private fun UserAddressListSuccessScreenPreview() {
        val addressItemModel = AddressItemModel(
            uuid = "",
            address = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж",
            isClickable = false,
        )
        UserAddressListScreen(
            addressItemModelListState = State.Success(
                listOf(
                    addressItemModel,
                    addressItemModel,
                    addressItemModel,
                    addressItemModel,
                )
            )
        )
    }

    @Preview
    @Composable
    private fun UserAddressListEmptyScreenPreview() {
        UserAddressListScreen(addressItemModelListState = State.Empty())
    }

    @Preview
    @Composable
    private fun UserAddressListLoadingScreenPreview() {
        UserAddressListScreen(addressItemModelListState = State.Loading())
    }
}