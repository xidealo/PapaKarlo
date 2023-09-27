package com.bunbeauty.papakarlo.feature.profile.screen.settings

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.bunbeauty.papakarlo.common.ui.element.FoodDeliveryScaffold
import com.bunbeauty.papakarlo.common.ui.element.button.MainButton
import com.bunbeauty.papakarlo.common.ui.element.card.NavigationCard
import com.bunbeauty.papakarlo.common.ui.element.card.NavigationTextCard
import com.bunbeauty.papakarlo.common.ui.element.card.TextCard
import com.bunbeauty.papakarlo.common.ui.screen.ErrorScreen
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.databinding.LayoutComposeBinding
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import com.bunbeauty.papakarlo.feature.city.screen.changecity.CityListBottomSheet
import com.bunbeauty.papakarlo.feature.main.IMessageHost
import com.bunbeauty.papakarlo.feature.profile.screen.logout.LogoutBottomSheet
import com.bunbeauty.shared.domain.model.Settings
import com.bunbeauty.shared.domain.model.city.City
import com.bunbeauty.shared.presentation.settings.SettingsState
import com.bunbeauty.shared.presentation.settings.SettingsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : BaseFragmentWithSharedViewModel(R.layout.layout_compose) {

    override val viewBinding by viewBinding(LayoutComposeBinding::bind)
    private val viewModel: SettingsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadData()
        viewBinding.root.setContentWithTheme {
            val settingsState by viewModel.settingsState.collectAsStateWithLifecycle()
            SettingsScreen(settingsState)
            LaunchedEffect(settingsState.eventList) {
                handleEventList(settingsState.eventList)
            }
        }
    }

    @Composable
    fun SettingsScreen(settingsState: SettingsState) {
        FoodDeliveryScaffold(
            title = stringResource(R.string.title_settings),
            backActionClick = {
                findNavController().popBackStack()
            },
            actionButton = {
                MainButton(
                    modifier = Modifier
                        .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace),
                    text = stringResource(id = R.string.action_logout),
                    onClick = viewModel::onLogoutClicked
                )
            }
        ) {
            when (settingsState.state) {
                SettingsState.State.SUCCESS -> {
                    SettingsScreenSuccess(settingsState)
                }

                SettingsState.State.ERROR -> {
                    ErrorScreen(mainTextId = R.string.error_common_data_loading) {
                        viewModel.loadData()
                    }
                }

                SettingsState.State.LOADING -> {
                    LoadingScreen()
                }
            }
        }
    }

    @Composable
    fun SettingsScreenSuccess(settingsState: SettingsState) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(FoodDeliveryTheme.dimensions.mediumSpace)
        ) {
            settingsState.settings?.phoneNumber?.let { phoneNumber ->
                TextCard(
                    hint = stringResource(R.string.hint_settings_phone),
                    label = phoneNumber
                )
            }

            val email = settingsState.settings?.email
            if (email.isNullOrEmpty()) {
                NavigationCard(
                    modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                    label = stringResource(R.string.common_email),
                    onClick = viewModel::onEmailClicked
                )
            } else {
                NavigationTextCard(
                    modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                    hintStringId = R.string.common_email,
                    label = email,
                    onClick = viewModel::onEmailClicked
                )
            }
            NavigationTextCard(
                modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                hintStringId = R.string.common_city,
                label = settingsState.selectedCity?.name,
                onClick = viewModel::onCityClicked
            )
        }
    }

    private suspend fun handleEventList(eventList: List<SettingsState.Event>) {
        eventList.onEach { event ->
            when (event) {
                is SettingsState.Event.ShowEditEmailEvent -> {
                    EmailBottomSheet.show(childFragmentManager, event.email)?.let { email ->
                        viewModel.onEmailChanged(email)
                    }
                }

                is SettingsState.Event.ShowCityListEvent -> {
                    CityListBottomSheet.show(
                        fragmentManager = childFragmentManager,
                        cityList = event.cityList,
                        selectedCityUuid = event.selectedCityUuid
                    )?.let { city ->
                        viewModel.onCitySelected(city.uuid)
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
                    LogoutBottomSheet.show(childFragmentManager)?.let { isLogout ->
                        if (isLogout) {
                            viewModel.logout()
                        }
                    }
                }
            }
        }
        viewModel.consumeEventList(eventList)
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun SettingsScreenWithEmailPreview() {
        FoodDeliveryTheme {
            SettingsScreen(
                SettingsState(
                    settings = Settings(
                        userUuid = "",
                        phoneNumber = "+7 999 000-00-00",
                        email = "example@email.com"
                    ),
                    selectedCity = City(
                        uuid = "",
                        name = "Москва",
                        timeZone = ""
                    ),
                    state = SettingsState.State.SUCCESS
                )
            )
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun SettingsScreenWithoutEmailPreview() {
        FoodDeliveryTheme {
            SettingsScreen(
                SettingsState(
                    settings = Settings(
                        userUuid = "",
                        phoneNumber = "+7 999 000-00-00",
                        email = ""
                    ),
                    selectedCity = City(
                        uuid = "",
                        name = "Москва",
                        timeZone = ""
                    ),
                    state = SettingsState.State.SUCCESS
                )
            )
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun SettingsScreenLoadingPreview() {
        FoodDeliveryTheme {
            SettingsScreen(
                SettingsState(
                    settings = null,
                    selectedCity = null,
                    state = SettingsState.State.LOADING
                )
            )
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun SettingsScreenErrorPreview() {
        FoodDeliveryTheme {
            SettingsScreen(
                SettingsState(
                    settings = null,
                    selectedCity = null,
                    state = SettingsState.State.ERROR
                )
            )
        }
    }
}
