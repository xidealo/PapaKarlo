package com.bunbeauty.papakarlo.feature.address.screen.useraddresslist

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
import com.bunbeauty.papakarlo.common.ui.screen.ErrorScreen
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.feature.address.mapper.toUserAddressItem
import com.bunbeauty.papakarlo.feature.address.model.UserAddressItem
import com.bunbeauty.shared.presentation.user_address_list.UserAddressListDataState
import com.bunbeauty.shared.presentation.user_address_list.UserAddressListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
private fun UserAddressListDataState.DataState.mapState(): UserAddressListViewState {
    return UserAddressListViewState(
        userAddressItems = userAddressList.map { userAddressList ->
            userAddressList.toUserAddressItem()
        },
        state = when (state) {
            UserAddressListDataState.DataState.State.SUCCESS -> UserAddressListViewState.State.Success
            UserAddressListDataState.DataState.State.ERROR -> UserAddressListViewState.State.Error
            UserAddressListDataState.DataState.State.EMPTY -> UserAddressListViewState.State.Empty
            UserAddressListDataState.DataState.State.LOADING -> UserAddressListViewState.State.Loading
        }
    )
}

// TODO need refactoring
@Composable
fun UserAddressListRoute(
    viewModel: UserAddressListViewModel = koinViewModel(),
    back: () -> Unit,
    goToCreateAddress: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.onAction(UserAddressListDataState.Action.Init)
    }

    val viewState by viewModel.dataState.collectAsStateWithLifecycle()

    val effects by viewModel.events.collectAsStateWithLifecycle()
    val consumeEffects = remember {
        {
            viewModel.consumeEvents(effects)
        }
    }

    val onAction = remember {
        { event: UserAddressListDataState.Action ->
            viewModel.onAction(event)
        }
    }

    UserAddressEffect(
        effects = effects,
        back = back,
        goToCreateAddress = goToCreateAddress,
        consumeEffects = consumeEffects
    )

    UserAddressListScreen(
        viewState = viewState.mapState(),
        onAction = onAction
    )
}

@Composable
private fun UserAddressListScreen(
    viewState: UserAddressListViewState,
    onAction: (UserAddressListDataState.Action) -> Unit
) {
    FoodDeliveryScaffold(
        title = stringResource(R.string.title_my_addresses),
        backActionClick = { onAction(UserAddressListDataState.Action.BackClicked) },
        actionButton = {
            if (viewState.state != UserAddressListViewState.State.Loading) {
                MainButton(
                    modifier = Modifier
                        .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace),
                    textStringId = R.string.action_add_addresses,
                    onClick = { onAction(UserAddressListDataState.Action.OnClickedCreateAddress) }
                )
            }
        }
    ) {
        Crossfade(
            targetState = viewState.state,
            label = "UserAddressListScreen"
        ) { state ->
            when (viewState.state) {
                UserAddressListViewState.State.Error ->
                    ErrorScreen(
                        mainTextId = R.string.error_addresses_list_loading,
                        onClick = {
                            onAction(UserAddressListDataState.Action.OnRefreshClicked)
                        }
                    )
                UserAddressListViewState.State.Loading ->
                    LoadingScreen()

                UserAddressListViewState.State.Success ->
                    UserAddressListSuccessScreen(viewState.userAddressItems)

                UserAddressListViewState.State.Empty ->
                    EmptyScreen(
                        imageId = R.drawable.ic_address,
                        imageDescriptionId = R.string.description_cafe_addresses_empty,
                        mainTextId = R.string.title_my_addresses_empty,
                        extraTextId = R.string.msg_my_addresses_empty,
                        buttonTextId = R.string.action_add_addresses,
                        onClick = { onAction(UserAddressListDataState.Action.Init) }
                    )
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
    effects: List<UserAddressListDataState.Event>,
    back: () -> Unit,
    goToCreateAddress: () -> Unit,
    consumeEffects: () -> Unit
) {
    LaunchedEffect(effects) {
        effects.forEach { effect ->
            when (effect) {
                UserAddressListDataState.Event.GoBackEvent -> back()
                UserAddressListDataState.Event.OpenCreateAddressEvent -> goToCreateAddress()
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
            UserAddressListViewState(
                userAddressItems = listOf(
                    addressItemModel,
                    addressItemModel,
                    addressItemModel,
                    addressItemModel
                ),
                state = UserAddressListViewState.State.Success
            ),
            onAction = { }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun UserAddressListEmptyScreenPreview() {
    FoodDeliveryTheme {
        UserAddressListScreen(
            viewState = UserAddressListViewState(
                state = UserAddressListViewState.State.Empty

            ),
            onAction = {}
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun UserAddressListLoadingScreenPreview() {
    FoodDeliveryTheme {
        UserAddressListScreen(
            viewState = UserAddressListViewState(
                state = UserAddressListViewState.State.Loading
            ),
            onAction = {}
        )
    }
}
