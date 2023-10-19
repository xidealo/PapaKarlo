package com.bunbeauty.papakarlo.feature.address.screen.useraddresslist

import android.os.Bundle
import android.view.View
import androidx.compose.animation.Crossfade
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragmentWithSharedViewModel
import com.bunbeauty.papakarlo.common.extension.navigateSafe
import com.bunbeauty.papakarlo.common.ui.element.FoodDeliveryScaffold
import com.bunbeauty.papakarlo.common.ui.element.button.MainButton
import com.bunbeauty.papakarlo.common.ui.screen.EmptyScreen
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.databinding.LayoutComposeBinding
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import com.bunbeauty.papakarlo.feature.address.model.UserAddressItem
import com.bunbeauty.papakarlo.feature.address.screen.useraddresslist.UserAddressListFragmentDirections.toCreateAddressFragment
import com.bunbeauty.papakarlo.feature.address.ui.SelectableItemView
import com.bunbeauty.papakarlo.feature.createorder.mapper.UserAddressItemMapper
import com.bunbeauty.shared.presentation.user_address_list.UserAddressListState
import com.bunbeauty.shared.presentation.user_address_list.UserAddressListViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserAddressListFragment : BaseFragmentWithSharedViewModel(R.layout.layout_compose) {

    override val viewBinding by viewBinding(LayoutComposeBinding::bind)
    private val viewModel: UserAddressListViewModel by viewModel()

    val userAddressItemMapper: UserAddressItemMapper by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.update()
        viewBinding.root.setContentWithTheme {
            val addressListState by viewModel.addressListState.collectAsStateWithLifecycle()
            UserAddressListScreen(
                userAddressListState = UserAddressListUi(
                    userAddressItems = addressListState.userAddressList.map { userAddress ->
                        userAddressItemMapper.toItem(userAddress)
                    },
                    state = addressListState.state,
                    eventList = addressListState.eventList
                )
            )
            LaunchedEffect(addressListState.eventList) {
                handleEventList(addressListState.eventList)
            }
        }
    }

    @Composable
    private fun UserAddressListScreen(userAddressListState: UserAddressListUi) {
        FoodDeliveryScaffold(
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
            Crossfade(
                targetState = userAddressListState.state,
                label = "UserAddressListScreen"
            ) { state ->
                when (state) {
                    UserAddressListState.State.SUCCESS -> {
                        UserAddressListSuccessScreen(userAddressListState.userAddressItems)
                    }

                    UserAddressListState.State.EMPTY -> {
                        EmptyScreen(
                            imageId = R.drawable.ic_address,
                            imageDescriptionId = R.string.description_cafe_addresses_empty,
                            mainTextId = R.string.title_my_addresses_empty,
                            extraTextId = R.string.msg_my_addresses_empty,
                            buttonTextId = R.string.action_add_addresses,
                            onClick = viewModel::update
                        )
                    }

                    UserAddressListState.State.LOADING -> {
                        LoadingScreen()
                    }
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
                    SelectableItemView(
                        modifier = Modifier.padding(
                            top = FoodDeliveryTheme.dimensions.getItemSpaceByIndex(i)
                        ),
                        title = userAddressItem.address,
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
                    findNavController().navigateSafe(toCreateAddressFragment())
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
                address = "addddd",
                isSelected = false
            )
            UserAddressListScreen(
                UserAddressListUi(
                    userAddressItems = listOf(
                        addressItemModel,
                        addressItemModel,
                        addressItemModel,
                        addressItemModel
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
                userAddressListState = UserAddressListUi(
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
                userAddressListState = UserAddressListUi(
                    state = UserAddressListState.State.LOADING
                )
            )
        }
    }
}
