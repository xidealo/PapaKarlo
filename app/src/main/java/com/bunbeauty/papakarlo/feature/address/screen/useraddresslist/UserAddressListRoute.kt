package com.bunbeauty.papakarlo.feature.address.screen.useraddresslist

import androidx.activity.compose.LocalActivity
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.element.FoodDeliveryScaffold
import com.bunbeauty.papakarlo.common.ui.element.button.MainButton
import com.bunbeauty.papakarlo.common.ui.element.selectable.SelectableItem
import com.bunbeauty.papakarlo.common.ui.screen.EmptyScreen
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.feature.address.mapper.toUserAddressItem
import com.bunbeauty.papakarlo.feature.address.model.UserAddressItem
import com.bunbeauty.shared.presentation.user_address_list.UserAddressListState
import com.bunbeauty.shared.presentation.user_address_list.UserAddressListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun UserAddressListRoute(
    viewModel: UserAddressListViewModel = koinViewModel(),
    back: () -> Unit,
    goToCreateAddress: () -> Unit,
) {

    LaunchedEffect(Unit) {
        viewModel.update()
    }

    val viewState by viewModel.addressListState.collectAsStateWithLifecycle()

    val consumeEffects = remember {
        {
            viewModel.consumeEventList(viewState.eventList)
        }
    }

    UserAddressEffect(
        effects = viewState.eventList,
        back = back,
        goToCreateAddress = goToCreateAddress,
        consumeEffects = consumeEffects
    )
    UserAddressListScreen(
        viewState = UserAddressListUi(
            userAddressItems = viewState.userAddressList.map { userAddress ->
                userAddress.toUserAddressItem()
            },
            state = viewState.state,
            eventList = viewState.eventList
        ),
        back = back,
        onCreateAddressClicked = viewModel::onCreateAddressClicked,
        update = viewModel::update,
    )
}

@Composable
private fun UserAddressListScreen(
    viewState: UserAddressListUi,
    back: () -> Unit,
    onCreateAddressClicked: () -> Unit,
    update: () -> Unit,
) {
    FoodDeliveryScaffold(
        title = stringResource(R.string.title_my_addresses),
        backActionClick = back,
        actionButton = {
            if (viewState.state != UserAddressListState.State.LOADING) {
                MainButton(
                    modifier = Modifier
                        .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace),
                    textStringId = R.string.action_add_addresses,
                    onClick = onCreateAddressClicked
                )
            }
        }
    ) {
        Crossfade(
            targetState = viewState.state,
            label = "UserAddressListScreen"
        ) { state ->
            when (state) {
                UserAddressListState.State.SUCCESS -> {
                    UserAddressListSuccessScreen(viewState.userAddressItems)
                }

                UserAddressListState.State.EMPTY -> {
                    EmptyScreen(
                        imageId = R.drawable.ic_address,
                        imageDescriptionId = R.string.description_cafe_addresses_empty,
                        mainTextId = R.string.title_my_addresses_empty,
                        extraTextId = R.string.msg_my_addresses_empty,
                        buttonTextId = R.string.action_add_addresses,
                        onClick = update
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
            ),
            verticalArrangement = spacedBy(8.dp)
        ) {
            itemsIndexed(userAddressItems) { i, userAddressItem ->
                SelectableItem(
                    title = userAddressItem.address,
                    clickable = false,
                    elevated = true,
                    onClick = {},
                    enabled = true
                )
            }
        }
    }
}

@Composable
fun UserAddressEffect(
    effects: List<UserAddressListState.Event>,
    back: () -> Unit,
    goToCreateAddress: () -> Unit,
    consumeEffects: () -> Unit,
) {
    val activity = LocalActivity.current
    LaunchedEffect(effects) {

        effects.forEach { effect ->
            when (effect) {
                UserAddressListState.Event.OpenCreateAddressEvent -> goToCreateAddress()

                UserAddressListState.Event.GoBack -> back()
            }
        }
        consumeEffects()
    }
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
            ),
            back = {},
            onCreateAddressClicked = {},
            update = {}
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun UserAddressListEmptyScreenPreview() {
    FoodDeliveryTheme {
        UserAddressListScreen(
            viewState = UserAddressListUi(
                state = UserAddressListState.State.EMPTY,
                eventList = emptyList()
            ),
            back = {},
            onCreateAddressClicked = {},
            update = {}
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun UserAddressListLoadingScreenPreview() {
    FoodDeliveryTheme {
        UserAddressListScreen(
            viewState = UserAddressListUi(
                state = UserAddressListState.State.LOADING
            ),
            back = {},
            onCreateAddressClicked = {},
            update = {}
        )
    }
}
