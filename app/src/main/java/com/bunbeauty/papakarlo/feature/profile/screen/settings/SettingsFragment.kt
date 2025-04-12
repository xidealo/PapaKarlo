package com.bunbeauty.papakarlo.feature.profile.screen.settings

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseComposeFragment
import com.bunbeauty.papakarlo.common.ui.element.FoodDeliveryScaffold
import com.bunbeauty.papakarlo.common.ui.element.button.MainButton
import com.bunbeauty.papakarlo.common.ui.element.card.NavigationCardWithDivider
import com.bunbeauty.papakarlo.common.ui.element.card.TextCardWithDivider
import com.bunbeauty.papakarlo.common.ui.screen.ErrorScreen
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.databinding.LayoutComposeBinding
import com.bunbeauty.papakarlo.feature.city.screen.CityUI
import com.bunbeauty.papakarlo.feature.city.screen.changecity.CityListBottomSheet
import com.bunbeauty.papakarlo.feature.main.IMessageHost
import com.bunbeauty.papakarlo.feature.profile.screen.logout.LogoutBottomSheet
import com.bunbeauty.shared.presentation.settings.SettingsState
import com.bunbeauty.shared.presentation.settings.SettingsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment :
    BaseComposeFragment<SettingsState.DataState, SettingsViewState, SettingsState.Action, SettingsState.Event>() {

    override val viewBinding by viewBinding(LayoutComposeBinding::bind)
    override val viewModel: SettingsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadData()
    }

    @Composable
    override fun Screen(viewState: SettingsViewState, onAction: (SettingsState.Action) -> Unit) {
        SettingsScreen(settingsState = viewState, onAction = onAction)
    }

    @Composable
    override fun SettingsState.DataState.mapState(): SettingsViewState {
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
            }
        )
    }

    override fun handleEvent(event: SettingsState.Event) {
        when (event) {
            is SettingsState.Event.ShowEditEmailEvent -> {
                lifecycleScope.launch {
                    EmailBottomSheet.show(childFragmentManager, event.email)?.let { email ->
                        viewModel.onEmailChanged(email)
                    }
                }
            }

            is SettingsState.Event.ShowCityListEvent -> {
                lifecycleScope.launch {
                    CityListBottomSheet.show(
                        fragmentManager = childFragmentManager,
                        cityList = event.cityList.map { city ->
                            city.run {
                                CityUI(
                                    uuid = uuid,
                                    name = name
                                )
                            }
                        },
                        selectedCityUuid = event.selectedCityUuid
                    )?.let { cityUI ->
                        viewModel.onCitySelected(cityUI.uuid)
                    }
                }
            }

            SettingsState.Event.ShowEmailChangedSuccessfullyEvent -> {
                (activity as? IMessageHost)?.showInfoMessage(
                    resources.getString(R.string.msg_settings_email_updated)
                )
            }

            SettingsState.Event.ShowEmailChangingFailedEvent -> {
                (activity as? IMessageHost)?.showErrorMessage(
                    resources.getString(R.string.error_something_went_wrong)
                )
            }

            SettingsState.Event.Back -> {
                findNavController().navigateUp()
            }

            SettingsState.Event.ShowLogoutEvent -> {
                lifecycleScope.launch {
                    LogoutBottomSheet.show(childFragmentManager)?.let { isLogout ->
                        if (isLogout) {
                            viewModel.logout()
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun SettingsScreen(settingsState: SettingsViewState, onAction: (SettingsState.Action) -> Unit) {
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
            when (settingsState.state) {
                SettingsViewState.State.Success -> {
                    SettingsScreenSuccess(
                        settingsState = settingsState,
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

    @Preview(showSystemUi = true)
    @Composable
    private fun SettingsScreenWithEmailPreview() {
        FoodDeliveryTheme {
            SettingsScreen(
                SettingsViewState(
                    phoneNumber = "+7 999 000-00-00",
                    selectedCityName = "Москва",
                    state = SettingsViewState.State.Success
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
                SettingsViewState(
                    phoneNumber = "+7 999 000-00-00",
                    selectedCityName = "Москва",
                    state = SettingsViewState.State.Success
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
                SettingsViewState(
                    phoneNumber = "+7 999 000-00-00",
                    selectedCityName = "Москва",
                    state = SettingsViewState.State.Loading
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
                SettingsViewState(
                    phoneNumber = "+7 999 000-00-00",
                    selectedCityName = "Москва",
                    state = SettingsViewState.State.Error
                ),
                onAction = {}
            )
        }
    }
}
