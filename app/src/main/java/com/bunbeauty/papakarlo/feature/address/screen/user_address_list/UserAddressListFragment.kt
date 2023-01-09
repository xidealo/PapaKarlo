package com.bunbeauty.papakarlo.feature.address.screen.user_address_list

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragmentWithSharedViewModel
import com.bunbeauty.papakarlo.common.ui.element.MainButton
import com.bunbeauty.papakarlo.common.ui.screen.EmptyScreen
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.databinding.BottomSheetUserAddressListBinding
import com.bunbeauty.papakarlo.extensions.compose
import com.bunbeauty.papakarlo.feature.address.screen.user_address_list.UserAddressListFragmentDirections.toCreateAddressFragment
import com.bunbeauty.papakarlo.feature.address.ui.AddressItem
import com.bunbeauty.papakarlo.feature.create_order.mapper.UserAddressItemMapper
import com.bunbeauty.shared.domain.model.Street
import com.bunbeauty.shared.domain.model.address.UserAddress
import com.bunbeauty.shared.presentation.user_address_list.UserAddressListState
import com.bunbeauty.shared.presentation.user_address_list.UserAddressListViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserAddressListFragment :
    BaseFragmentWithSharedViewModel(R.layout.bottom_sheet_user_address_list) {

    override val viewBinding by viewBinding(BottomSheetUserAddressListBinding::bind)
    private val viewModel: UserAddressListViewModel by viewModel()

    val userAddressItemMapper: UserAddressItemMapper by inject()

    @OptIn(ExperimentalLifecycleComposeApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.update()
        viewBinding.fragmentUserAddressListCvMain.compose {
            val addressListState by viewModel.addressListState.collectAsStateWithLifecycle()
            UserAddressListScreen(addressListState)
            LaunchedEffect(addressListState.eventList) {
                handleEventList(addressListState.eventList)
            }
        }
    }

    @Composable
    private fun UserAddressListScreen(userAddressListState: UserAddressListState) {
        when (userAddressListState.state) {
            UserAddressListState.State.SUCCESS -> {
                UserAddressListSuccessScreen(userAddressListState)
            }
            UserAddressListState.State.EMPTY -> {
                EmptyScreen(
                    imageId = R.drawable.empty_page,
                    imageDescriptionId = R.string.description_cafe_addresses_empty,
                    textId = R.string.msg_my_addresses_empty,
                    buttonTextId = R.string.action_add_addresses,
                    onClick = viewModel::onCreateAddressClicked
                )
            }
            UserAddressListState.State.LOADING -> {
                LoadingScreen()
            }
        }
    }

    @Composable
    private fun UserAddressListSuccessScreen(userAddressListState: UserAddressListState) {
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
                itemsIndexed(userAddressListState.userAddressList) { i, userAddress ->
                    val userAddressItem = userAddressItemMapper.toItem(userAddress)
                    AddressItem(
                        modifier = Modifier.padding(
                            top = FoodDeliveryTheme.dimensions.getItemSpaceByIndex(i)
                        ),
                        address = userAddressItem.address,
                        isClickable = false,
                        hasShadow = true
                    ) {}
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

    private fun handleEventList(eventList: List<UserAddressListState.Event>) {
        eventList.forEach { event ->
            when (event) {
                UserAddressListState.Event.OpenCreateAddressEvent -> {
                    findNavController().navigate(toCreateAddressFragment())
                }
                UserAddressListState.Event.GoBack -> {
                    // this event are used for ios
                }
            }
        }
        viewModel.consumeEventList(eventList)
    }

    @Preview
    @Composable
    private fun UserAddressListSuccessScreenPreview() {
        val addressItemModel = UserAddress(
            uuid = "",
            street = Street(
                uuid = "",
                name = "улица Чапаева",
                cityUuid = ""
            ),
            house = "22аб",
            flat = "55",
            entrance = "1",
            floor = "1",
            comment = "",
            userUuid = "",
        )
        UserAddressListScreen(
            UserAddressListState(
                userAddressList = listOf(
                    addressItemModel,
                    addressItemModel,
                    addressItemModel,
                    addressItemModel,
                ),
                state = UserAddressListState.State.SUCCESS
            )
        )
    }

    @Preview
    @Composable
    private fun UserAddressListEmptyScreenPreview() {
        UserAddressListScreen(
            userAddressListState = UserAddressListState(
                state = UserAddressListState.State.EMPTY
            )
        )
    }

    @Preview
    @Composable
    private fun UserAddressListLoadingScreenPreview() {
        UserAddressListScreen(
            userAddressListState = UserAddressListState(
                state = UserAddressListState.State.LOADING
            )
        )
    }
}
