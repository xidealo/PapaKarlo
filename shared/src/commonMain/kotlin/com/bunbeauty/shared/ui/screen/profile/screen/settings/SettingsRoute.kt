package com.bunbeauty.shared.ui.screen.profile.screen.settings

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
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import papakarlo.shared.generated.resources.Res
import com.bunbeauty.designsystem.ui.element.FoodDeliveryScaffold
import com.bunbeauty.designsystem.ui.element.button.MainButton
import com.bunbeauty.designsystem.ui.screen.ErrorScreen
import com.bunbeauty.designsystem.ui.screen.LoadingScreen
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.shared.ui.screen.city.screen.CityUI
import com.bunbeauty.designsystem.ui.element.card.NavigationCardWithDivider
import com.bunbeauty.designsystem.ui.element.card.TextCardWithDivider
import com.bunbeauty.shared.ui.screen.city.screen.changecity.CityListBottomSheetScreen
import com.bunbeauty.shared.ui.screen.profile.screen.logout.LogoutBottomSheetScreen
import papakarlo.shared.generated.resources.action_logout
import papakarlo.shared.generated.resources.common_city
import papakarlo.shared.generated.resources.error_common_data_loading
import papakarlo.shared.generated.resources.hint_settings_phone
import papakarlo.shared.generated.resources.title_settings
import com.bunbeauty.shared.presentation.settings.SettingsState
import com.bunbeauty.shared.presentation.settings.SettingsViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import org.jetbrains.compose.resources.getString
import org.koin.compose.viewmodel.koinViewModel
import papakarlo.shared.generated.resources.error_something_went_wrong
import papakarlo.shared.generated.resources.msg_settings_email_updated

@Composable
fun SettingsRoute(
    viewModel: SettingsViewModel = koinViewModel(),
    back: () -> Unit,
    showInfoMessage: (String, Int) -> Unit,
    showErrorMessage: (String) -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    val viewState by viewModel.dataState.collectAsStateWithLifecycle()

    val onAction =
        remember {
            { action: SettingsState.Action ->
                viewModel.onAction(action)
            }
        }

    val effects by viewModel.events.collectAsStateWithLifecycle()
    val consumeEffects =
        remember {
            {
                viewModel.consumeEvents(effects)
            }
        }

    SettingsEffect(
        effects = effects,
        back = back,
        consumeEffects = consumeEffects,
        showInfoMessage = showInfoMessage,
        showErrorMessage = showErrorMessage,
    )

    SettingsScreen(viewState = viewState.mapState(), onAction = onAction)
}

@Composable
fun SettingsState.DataState.mapState(): SettingsViewState =
    SettingsViewState(
        phoneNumber = settings?.phoneNumber.orEmpty(),
        selectedCityName = selectedCity?.name.orEmpty(),
        state =
            when (state) {
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
        logoutUI =
            SettingsViewState.LogoutBottomSheetUI(
                isShown = isShowLogoutBottomSheet,
            ),
        cityListBottomSheetUI =
            SettingsViewState.CityListBottomSheetUI(
                isShown = isShowCityListBottomSheet,
                cityListUI =
                    cityList
                        .map { city ->
                            CityUI(
                                uuid = city.uuid,
                                name = city.name,
                                isSelected = city.uuid == selectedCity?.uuid,
                            )
                        }.toPersistentList(),
            ),
    )

@Composable
fun SettingsEffect(
    effects: List<SettingsState.Event>,
    back: () -> Unit,
    consumeEffects: () -> Unit,
    showInfoMessage: (String, Int) -> Unit,
    showErrorMessage: (String) -> Unit,
) {
    LaunchedEffect(effects) {
        effects.forEach { effect ->
            when (effect) {
                SettingsState.Event.ShowEmailChangedSuccessfullyEvent -> {
                    showInfoMessage(
                        getString(Res.string.msg_settings_email_updated),
                        0
                    )
                }

                SettingsState.Event.ShowEmailChangingFailedEvent -> {
                    showErrorMessage(getString(Res.string.error_something_went_wrong))
                }

                SettingsState.Event.Back -> back()
            }
        }
        consumeEffects()
    }
}

@Composable
fun SettingsScreen(
    viewState: SettingsViewState,
    onAction: (SettingsState.Action) -> Unit,
) {
    FoodDeliveryScaffold(
        title = stringResource(Res.string.title_settings),
        backActionClick = {
            onAction(SettingsState.Action.BackClick)
        },
        actionButton = {
            MainButton(
                modifier =
                    Modifier
                        .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace),
                text = stringResource(resource = Res.string.action_logout),
                onClick = {
                    onAction(SettingsState.Action.OnLogoutClicked)
                },
            )
        },
        backgroundColor = FoodDeliveryTheme.colors.mainColors.surface,
    ) {
        when (viewState.state) {
            SettingsViewState.State.Success -> {
                SettingsScreenSuccess(
                    settingsState = viewState,
                    onAction = onAction,
                )
                LogoutBottomSheetScreen(
                    logoutUI = viewState.logoutUI,
                    onAction = onAction,
                )
                CityListBottomSheetScreen(
                    cityListBottomSheetUI = viewState.cityListBottomSheetUI,
                    onAction = onAction,
                )
            }

            SettingsViewState.State.Error ->
                ErrorScreen(
                    mainTextId = Res.string.error_common_data_loading,
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
    onAction: (SettingsState.Action) -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
    ) {
        TextCardWithDivider(
            label = stringResource(Res.string.hint_settings_phone),
            value = settingsState.phoneNumber,
        )

        NavigationCardWithDivider(
            onClick = {
                onAction(SettingsState.Action.OnCityClicked)
            },
            label = stringResource(Res.string.common_city),
            value = settingsState.selectedCityName,
        )
    }
}

val previewSettingsViewState =
    SettingsViewState(
        phoneNumber = "+7 999 000-00-00",
        selectedCityName = "Москва",
        state = SettingsViewState.State.Success,
        logoutUI = SettingsViewState.LogoutBottomSheetUI(false),
        cityListBottomSheetUI =
            SettingsViewState.CityListBottomSheetUI(
                isShown = false,
                cityListUI = persistentListOf(),
            ),
    )

@Preview(showBackground = true)
@Composable
private fun SettingsScreenWithEmailPreview() {
    FoodDeliveryTheme {
        SettingsScreen(
            previewSettingsViewState.copy(
                phoneNumber = "+7 999 000-00-00",
                selectedCityName = "Москва",
                state = SettingsViewState.State.Success,
                logoutUI = SettingsViewState.LogoutBottomSheetUI(false),
                cityListBottomSheetUI =
                    SettingsViewState.CityListBottomSheetUI(
                        isShown = false,
                        cityListUI = persistentListOf(),
                    ),
            ),
            onAction = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsScreenWithoutEmailPreview() {
    FoodDeliveryTheme {
        SettingsScreen(
            previewSettingsViewState.copy(
                phoneNumber = "+7 999 000-00-00",
                selectedCityName = "Москва",
                state = SettingsViewState.State.Success,
                logoutUI = SettingsViewState.LogoutBottomSheetUI(isShown = false),
                cityListBottomSheetUI =
                    SettingsViewState.CityListBottomSheetUI(
                        isShown = false,
                        cityListUI = persistentListOf(),
                    ),
            ),
            onAction = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsScreenLoadingPreview() {
    FoodDeliveryTheme {
        SettingsScreen(
            previewSettingsViewState.copy(
                phoneNumber = "+7 999 000-00-00",
                selectedCityName = "Москва",
                state = SettingsViewState.State.Loading,
                logoutUI = SettingsViewState.LogoutBottomSheetUI(isShown = false),
                cityListBottomSheetUI =
                    SettingsViewState.CityListBottomSheetUI(
                        isShown = false,
                        cityListUI = persistentListOf(),
                    ),
            ),
            onAction = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsScreenErrorPreview() {
    FoodDeliveryTheme {
        SettingsScreen(
            previewSettingsViewState.copy(
                phoneNumber = "+7 999 000-00-00",
                selectedCityName = "Москва",
                state = SettingsViewState.State.Error,
                logoutUI = SettingsViewState.LogoutBottomSheetUI(isShown = false),
                cityListBottomSheetUI =
                    SettingsViewState.CityListBottomSheetUI(
                        isShown = false,
                        cityListUI = persistentListOf(),
                    ),
            ),
            onAction = {},
        )
    }
}
