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
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragmentWithSharedViewModel
import com.bunbeauty.papakarlo.common.ui.element.card.NavigationCard
import com.bunbeauty.papakarlo.common.ui.element.card.NavigationTextCard
import com.bunbeauty.papakarlo.common.ui.element.card.TextCard
import com.bunbeauty.papakarlo.common.ui.screen.ErrorScreen
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.databinding.FragmentSettingsBinding
import com.bunbeauty.papakarlo.extensions.compose
import com.bunbeauty.papakarlo.extensions.showSnackbar
import com.bunbeauty.papakarlo.feature.city.screen.change_city.CityListBottomSheet
import com.bunbeauty.shared.domain.model.City
import com.bunbeauty.shared.domain.model.Settings
import com.bunbeauty.shared.presentation.settings.SettingsState
import com.bunbeauty.shared.presentation.settings.SettingsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : BaseFragmentWithSharedViewModel(R.layout.fragment_settings) {

    override val viewBinding by viewBinding(FragmentSettingsBinding::bind)
    private val viewModel: SettingsViewModel by viewModel()

    @OptIn(ExperimentalLifecycleComposeApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadData()
        viewBinding.root.compose {
            val settingsState by viewModel.settingsState.collectAsStateWithLifecycle()
            SettingsScreen(settingsState)
            LaunchedEffect(settingsState.eventList) {
                handleEventList(settingsState.eventList)
            }
        }
    }

    @Composable
    fun SettingsScreen(settingsState: SettingsState) {
        when (settingsState.state) {
            SettingsState.State.SUCCESS -> {
                SettingsScreenSuccessPreview(settingsState)
            }
            SettingsState.State.ERROR -> {
                ErrorScreen(message = stringResource(R.string.error_settings_loading))
            }
            SettingsState.State.LOADING -> {
                LoadingScreen()
            }
        }
    }

    @Composable
    fun SettingsScreenSuccessPreview(settingsState: SettingsState) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(FoodDeliveryTheme.dimensions.mediumSpace)
        ) {
            settingsState.settings?.phoneNumber?.let { phoneNumber ->
                TextCard(
                    hintStringId = R.string.hint_settings_phone,
                    label = phoneNumber
                )
            }

            val email = settingsState.settings?.email
            if (email.isNullOrEmpty()) {
                NavigationCard(
                    modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                    labelStringId = R.string.common_email
                ) {
                    viewModel.onEmailClicked()
                }
            } else {
                NavigationTextCard(
                    modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                    hintStringId = R.string.common_email,
                    label = email
                ) {
                    viewModel.onEmailClicked()
                }
            }
            NavigationTextCard(
                modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                hintStringId = R.string.common_city,
                label = settingsState.selectedCity?.name
            ) {
                viewModel.onCityClicked()
            }
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
                    CityListBottomSheet.show(childFragmentManager, event.cityList)?.let { city ->
                        viewModel.onCitySelected(city.uuid)
                    }
                }
                SettingsState.Event.ShowEmailChangedSuccessfullyEvent -> {
                    viewBinding.root.showSnackbar(
                        message = resources.getString(R.string.msg_settings_email_updated),
                        textColor = resourcesProvider.getColorByAttr(R.attr.colorOnPrimary),
                        backgroundColor = resourcesProvider.getColorByAttr(R.attr.colorPrimary),
                        isTop = false
                    )
                }
                SettingsState.Event.ShowEmailChangingFailedEvent -> {
                    viewBinding.root.showSnackbar(
                        message = resources.getString(R.string.error_something_went_wrong),
                        textColor = resourcesProvider.getColorByAttr(R.attr.colorOnError),
                        backgroundColor = resourcesProvider.getColorByAttr(R.attr.colorError),
                        isTop = false
                    )
                }
                SettingsState.Event.Back -> {
                    findNavController().navigateUp()
                }
            }
        }
        viewModel.consumeEventList(eventList)
    }

    @Preview(showSystemUi = true)
    @Composable
    fun SettingsScreenWithEmailPreview() {
        SettingsScreen(
            SettingsState(
                settings = Settings(
                    userUuid = "",
                    phoneNumber = "+7 999 000-00-00",
                    email = "example@email.com",
                ),
                selectedCity = City(
                    uuid = "",
                    name = "Москва",
                    timeZone = "",
                ),
                state = SettingsState.State.SUCCESS
            )
        )
    }

    @Preview(showSystemUi = true)
    @Composable
    fun SettingsScreenWithoutEmailPreview() {
        SettingsScreen(
            SettingsState(
                settings = Settings(
                    userUuid = "",
                    phoneNumber = "+7 999 000-00-00",
                    email = "",
                ),
                selectedCity = City(
                    uuid = "",
                    name = "Москва",
                    timeZone = "",
                ),
                state = SettingsState.State.SUCCESS
            )
        )
    }

    @Preview(showSystemUi = true)
    @Composable
    fun SettingsScreenLoadingPreview() {
        SettingsScreen(
            SettingsState(
                settings = null,
                selectedCity = null,
                state = SettingsState.State.LOADING
            )
        )
    }

    @Preview(showSystemUi = true)
    @Composable
    fun SettingsScreenErrorPreview() {
        SettingsScreen(
            SettingsState(
                settings = null,
                selectedCity = null,
                state = SettingsState.State.ERROR
            )
        )
    }
}
