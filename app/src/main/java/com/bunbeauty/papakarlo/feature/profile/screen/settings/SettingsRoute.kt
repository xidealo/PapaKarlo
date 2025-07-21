package com.bunbeauty.papakarlo.feature.profile.screen.settings

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.element.FoodDeliveryScaffold
import com.bunbeauty.papakarlo.common.ui.element.button.MainButton
import com.bunbeauty.papakarlo.common.ui.element.card.NavigationCardWithDivider
import com.bunbeauty.papakarlo.common.ui.element.card.TextCardWithDivider
import com.bunbeauty.papakarlo.common.ui.screen.ErrorScreen
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.feature.city.screen.CityUI
import com.bunbeauty.papakarlo.feature.city.screen.changecity.CityListBottomSheetScreen
import com.bunbeauty.papakarlo.feature.main.IMessageHost
import com.bunbeauty.papakarlo.feature.profile.screen.logout.LogoutBottomSheetScreen
import com.bunbeauty.shared.presentation.settings.SettingsState
import com.bunbeauty.shared.presentation.settings.SettingsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsRoute(
    viewModel: SettingsViewModel = koinViewModel(),
    back: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    val viewState by viewModel.dataState.collectAsStateWithLifecycle()

    val onAction = remember {
        { action: SettingsState.Action ->
            viewModel.onAction(action)
        }
    }

    val effects by viewModel.events.collectAsStateWithLifecycle()
    val consumeEffects = remember {
        {
            viewModel.consumeEvents(effects)
        }
    }

    SettingsEffect(
        effects = effects,
        back = back,
        consumeEffects = consumeEffects
    )

    SettingsScreen(viewState = viewState.mapState(), onAction = onAction)
}

@Composable
fun SettingsState.DataState.mapState(): SettingsViewState {
    return SettingsViewState(
        phoneNumber = settings?.phoneNumber.orEmpty(),
        selectedCityName = selectedCity?.name.orEmpty(),
        state = when (state) {
            SettingsState.DataState.State.SUCCESS -> {
                SettingsViewState.State.Success
            }

            SettingsState.DataState.State.ERROR -> {
                SettingsViewState.State.Error
            }

            SettingsState.DataState.State.LOADING -> {
                SettingsViewState.State.Loading
            }
        },
        logoutUI = SettingsViewState.LogoutBottomSheetUI(
            isShown = isShowLogoutBottomSheet
        ),
        cityListBottomSheetUI = SettingsViewState.CityListBottomSheetUI(
            isShown = isShowCityListBottomSheet,
            cityListUI = cityList.map { city ->
                CityUI(
                    uuid = city.uuid,
                    name = city.name,
                    isSelected = city.uuid == selectedCity?.uuid
                )
            }
        )
    )
}

@Composable
fun SettingsEffect(
    effects: List<SettingsState.Event>,
    back: () -> Unit,
    consumeEffects: () -> Unit
) {
    val activity = LocalActivity.current
    effects.forEach { effect ->
        when (effect) {
            SettingsState.Event.ShowEmailChangedSuccessfullyEvent -> {
                (activity as? IMessageHost)?.showInfoMessage(
                    activity.resources.getString(R.string.msg_settings_email_updated)
                )
            }

            SettingsState.Event.ShowEmailChangingFailedEvent -> {
                (activity as? IMessageHost)?.showErrorMessage(
                    activity.resources.getString(R.string.error_something_went_wrong)
                )
            }

            SettingsState.Event.Back -> back()
        }
    }
    consumeEffects()
}

@Composable
fun SettingsScreen(viewState: SettingsViewState, onAction: (SettingsState.Action) -> Unit) {
    FoodDeliveryScaffold(
        title = stringResource(R.string.title_settings),
        backActionClick = {
            onAction(SettingsState.Action.BackClick)
        },
        actionButton = {
            MainButton(
                modifier = Modifier
                    .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace),
                text = stringResource(id = R.string.action_logout),
                onClick = {
                    onAction(SettingsState.Action.OnLogoutClicked)
                }
            )
        },
        backgroundColor = FoodDeliveryTheme.colors.mainColors.surface
    ) {
        when (viewState.state) {
            SettingsViewState.State.Success -> {
                SettingsScreenSuccess(
                    settingsState = viewState,
                    onAction = onAction
                )
                LogoutBottomSheetScreen(
                    logoutUI = viewState.logoutUI,
                    onAction = onAction
                )
                CityListBottomSheetScreen(
                    cityListBottomSheetUI = viewState.cityListBottomSheetUI,
                    onAction = onAction
                )
            }

            SettingsViewState.State.Error -> ErrorScreen(
                mainTextId = R.string.error_common_data_loading
            ) {
                onAction(SettingsState.Action.LoadData)
            }

            SettingsViewState.State.Loading -> LoadingScreen()
        }
    }
}

@Composable
fun SettingsScreenSuccess(
    settingsState: SettingsViewState,
    onAction: (SettingsState.Action) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        TextCardWithDivider(
            label = stringResource(R.string.hint_settings_phone),
            value = settingsState.phoneNumber
        )

        NavigationCardWithDivider(
            onClick = {
                onAction(SettingsState.Action.OnCityClicked)
            },
            label = stringResource(R.string.common_city),
            value = settingsState.selectedCityName
        )
    }
}

val previewSettingsViewState = SettingsViewState(
    phoneNumber = "+7 999 000-00-00",
    selectedCityName = "Москва",
    state = SettingsViewState.State.Success,
    logoutUI = SettingsViewState.LogoutBottomSheetUI(false),
    cityListBottomSheetUI = SettingsViewState.CityListBottomSheetUI(
        isShown = false,
        cityListUI = emptyList()
    )
)

@Preview(showSystemUi = true)
@Composable
private fun SettingsScreenWithEmailPreview() {
    FoodDeliveryTheme {
        SettingsScreen(
            previewSettingsViewState.copy(
                phoneNumber = "+7 999 000-00-00",
                selectedCityName = "Москва",
                state = SettingsViewState.State.Success,
                logoutUI = SettingsViewState.LogoutBottomSheetUI(false),
                cityListBottomSheetUI = SettingsViewState.CityListBottomSheetUI(
                    isShown = false,
                    cityListUI = emptyList()
                )
            ),
            onAction = {}
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SettingsScreenWithoutEmailPreview() {
    FoodDeliveryTheme {
        SettingsScreen(
            previewSettingsViewState.copy(
                phoneNumber = "+7 999 000-00-00",
                selectedCityName = "Москва",
                state = SettingsViewState.State.Success,
                logoutUI = SettingsViewState.LogoutBottomSheetUI(isShown = false),
                cityListBottomSheetUI = SettingsViewState.CityListBottomSheetUI(
                    isShown = false,
                    cityListUI = emptyList()
                )
            ),
            onAction = {}
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SettingsScreenLoadingPreview() {
    FoodDeliveryTheme {
        SettingsScreen(
            previewSettingsViewState.copy(
                phoneNumber = "+7 999 000-00-00",
                selectedCityName = "Москва",
                state = SettingsViewState.State.Loading,
                logoutUI = SettingsViewState.LogoutBottomSheetUI(isShown = false),
                cityListBottomSheetUI = SettingsViewState.CityListBottomSheetUI(
                    isShown = false,
                    cityListUI = emptyList()
                )
            ),
            onAction = {}
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SettingsScreenErrorPreview() {
    FoodDeliveryTheme {
        SettingsScreen(
            previewSettingsViewState.copy(
                phoneNumber = "+7 999 000-00-00",
                selectedCityName = "Москва",
                state = SettingsViewState.State.Error,
                logoutUI = SettingsViewState.LogoutBottomSheetUI(isShown = false),
                cityListBottomSheetUI = SettingsViewState.CityListBottomSheetUI(
                    isShown = false,
                    cityListUI = emptyList()
                )
            ),
            onAction = {}
        )
    }
}
