package com.bunbeauty.papakarlo.feature.address.user_address_list

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.common.Constants.RESULT_USER_ADDRESS_KEY
import com.bunbeauty.common.Constants.USER_ADDRESS_REQUEST_KEY
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.compose.element.MainButton
import com.bunbeauty.papakarlo.compose.item.AddressItem
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
            val addressItemModelList by viewModel.addressItemModelList.collectAsState()
            UserAddressListScreen(addressItemModelList)
        }
    }

    @Composable
    private fun UserAddressListScreen(addressItemModelList: List<AddressItemModel>) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(FoodDeliveryTheme.colors.background)
                .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace)
        ) {
            if (addressItemModelList.isEmpty()) {
                UserAddressListEmptyScreen(modifier = Modifier.weight(1f))
            } else {
                UserAddressListNotEmptyScreen(
                    modifier = Modifier.weight(1f),
                    addressItemModelList = addressItemModelList
                )
            }
            MainButton(
                modifier = Modifier.padding(bottom = FoodDeliveryTheme.dimensions.mediumSpace),
                textStringId = R.string.action_add_addresses
            ) {
                viewModel.onCreateAddressClicked()
            }
        }
    }

    @Composable
    fun UserAddressListEmptyScreen(modifier: Modifier = Modifier) {
        Box(modifier = modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.empty_page),
                    contentDescription = stringResource(R.string.description_empty_profile)
                )
                Text(
                    modifier = Modifier.padding(FoodDeliveryTheme.dimensions.mediumSpace),
                    text = stringResource(R.string.msg_my_addresses_empty),
                    textAlign = TextAlign.Center,
                    style = FoodDeliveryTheme.typography.body1
                )
            }
        }
    }

    @Composable
    fun UserAddressListNotEmptyScreen(
        modifier: Modifier = Modifier,
        addressItemModelList: List<AddressItemModel>
    ) {
        LazyColumn(
            modifier = modifier,
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
    }

    private val addressItemModel = AddressItemModel(
        uuid = "",
        address = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж",
        isClickable = false,
    )

    @Preview
    @Composable
    private fun UserAddressListScreenPreview() {
        UserAddressListScreen(
            addressItemModelList = listOf(
                addressItemModel,
                addressItemModel,
                addressItemModel,
                addressItemModel,
            )
        )
    }

    @Preview
    @Composable
    private fun UserAddressListEmptyScreenPreview() {
        UserAddressListScreen(
            addressItemModelList = emptyList()
        )
    }
}