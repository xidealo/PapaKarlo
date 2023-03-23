package com.bunbeauty.papakarlo.feature.address.screen.user_address_list

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragmentWithSharedViewModel
import com.bunbeauty.papakarlo.common.ui.element.button.MainButton
import com.bunbeauty.papakarlo.common.ui.screen.EmptyScreen
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.element.toolbar.FoodDeliveryToolbarScreen
import com.bunbeauty.papakarlo.databinding.BottomSheetUserAddressListBinding
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import com.bunbeauty.papakarlo.feature.address.model.UserAddressItem
import com.bunbeauty.papakarlo.feature.address.screen.user_address_list.UserAddressListFragmentDirections.toCreateAddressFragment
import com.bunbeauty.papakarlo.feature.address.ui.AddressItem
import com.bunbeauty.papakarlo.feature.create_order.mapper.UserAddressItemMapper
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
        viewBinding.fragmentUserAddressListCvMain.setContentWithTheme {
            val addressListState by viewModel.addressListState.collectAsStateWithLifecycle()
            UserAddressListScreen(
                userAddressListState = UserAddressListAndroidState(
                    userAddressItems = addressListState.userAddressList.map { userAddress ->
                        userAddressItemMapper.toItem(userAddress)
                    },
                    state = addressListState.state,
                    eventList = addressListState.eventList,
                )
            )
            LaunchedEffect(addressListState.eventList) {
                handleEventList(addressListState.eventList)
            }
        }
    }

    @Composable
    private fun UserAddressListScreen(userAddressListState: UserAddressListAndroidState) {
        FoodDeliveryToolbarScreen(
            title = stringResource(R.string.title_my_addresses),
            backActionClick = {
                findNavController().popBackStack()
            },
            actionButton = {
                if (userAddressListState.state != UserAddressListState.State.LOADING) {
                    MainButton(
                        modifier = Modifier
                            .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace),
                        textStringId = R.string.action_add_addresses
                    ) {
                        viewModel.onCreateAddressClicked()
                    }
                }
            }
        ) {
            when (userAddressListState.state) {
                UserAddressListState.State.SUCCESS -> {
                    UserAddressListSuccessScreen(userAddressListState.userAddressItems)
                }
                UserAddressListState.State.EMPTY -> {
                    EmptyScreen(
                        imageId = R.drawable.empty_address,
                        imageDescriptionId = R.string.description_cafe_addresses_empty,
                        mainTextId = R.string.title_my_addresses_empty,
                        extraTextId = R.string.msg_my_addresses_empty,
                        buttonTextId = R.string.action_add_addresses,
                        onClick = {
                            viewModel.update()
                        }
                    )
                }
                UserAddressListState.State.LOADING -> {
                    LoadingScreen()
                }
            }
        }
    }

    @Composable
    private fun UserAddressListSuccessScreen(userAddressItems: List<UserAddressItem>) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(FoodDeliveryTheme.colors.mainColors.background)
                .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    top = FoodDeliveryTheme.dimensions.mediumSpace,
                    bottom = FoodDeliveryTheme.dimensions.scrollScreenBottomSpace
                )
            ) {
                itemsIndexed(userAddressItems) { i, userAddressItem ->
                    AddressItem(
                        modifier = Modifier.padding(
                            top = FoodDeliveryTheme.dimensions.getItemSpaceByIndex(i)
                        ),
                        address = userAddressItem.address,
                        isClickable = false,
                        elevated = true
                    ) {}
                }
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

    @Preview(showSystemUi = true)
    @Composable
    private fun UserAddressListSuccessScreenPreview() {
        FoodDeliveryTheme {
            val addressItemModel = UserAddressItem(
                uuid = "1",
                address = "addddd"
            )
            UserAddressListScreen(
                UserAddressListAndroidState(
                    userAddressItems = listOf(
                        addressItemModel,
                        addressItemModel,
                        addressItemModel,
                        addressItemModel,
                    ),
                    state = UserAddressListState.State.SUCCESS,
                    eventList = emptyList()
                )
            )
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun UserAddressListEmptyScreenPreview() {
        FoodDeliveryTheme {
            UserAddressListScreen(
                userAddressListState = UserAddressListAndroidState(
                    state = UserAddressListState.State.EMPTY,
                    eventList = emptyList()
                )
            )
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun UserAddressListLoadingScreenPreview() {
        FoodDeliveryTheme {
            UserAddressListScreen(
                userAddressListState = UserAddressListAndroidState(
                    state = UserAddressListState.State.LOADING
                )
            )
        }
    }
}
