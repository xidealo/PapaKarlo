package com.bunbeauty.papakarlo.feature.profile.settings

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.setFragmentResultListener
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.shared.domain.model.profile.Settings
import com.bunbeauty.shared.domain.model.profile.User
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.common.state.State
import com.bunbeauty.papakarlo.compose.card.NavigationCard
import com.bunbeauty.papakarlo.compose.card.NavigationTextCard
import com.bunbeauty.papakarlo.compose.card.TextCard
import com.bunbeauty.papakarlo.compose.screen.ErrorScreen
import com.bunbeauty.papakarlo.compose.screen.LoadingScreen
import com.bunbeauty.papakarlo.compose.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.databinding.FragmentSettingsBinding
import com.bunbeauty.papakarlo.extensions.compose
import core_common.Constants.EMAIL_REQUEST_KEY
import core_common.Constants.RESULT_EMAIL_KEY
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    override val viewModel: SettingsViewModel by viewModel()
    override val viewBinding by viewBinding(FragmentSettingsBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.fragmentSettingsCvMain.compose {
            val settingsState: State<Settings> by viewModel.settingsState.collectAsState()
            SettingsScreen(settingsState)
        }
        setFragmentResultListener(EMAIL_REQUEST_KEY) { _, bundle ->
            bundle.getString(RESULT_EMAIL_KEY)?.let { email ->
                viewModel.onEmailChanged(email)
            }
        }
    }

    @Composable
    fun SettingsScreen(settingsState: State<Settings>) {
        when (settingsState) {
            is State.Success -> {
                SettingsScreenSuccessPreview(settingsState.data)
            }
            is State.Error -> {
                ErrorScreen(message = settingsState.message)
            }
            else -> {
                LoadingScreen()
            }
        }
    }

    @Composable
    fun SettingsScreenSuccessPreview(settings: Settings) {
        Column(modifier = Modifier.fillMaxSize()) {
            TextCard(
                modifier = Modifier.padding(
                    start = FoodDeliveryTheme.dimensions.mediumSpace,
                    end = FoodDeliveryTheme.dimensions.mediumSpace,
                    top = FoodDeliveryTheme.dimensions.mediumSpace,
                ),
                hintStringId = R.string.hint_settings_phone,
                label = settings.user.phone
            )
            val email = settings.user.email
            if (email.isNullOrEmpty()) {
                NavigationCard(
                    modifier = Modifier.padding(
                        start = FoodDeliveryTheme.dimensions.mediumSpace,
                        end = FoodDeliveryTheme.dimensions.mediumSpace,
                        top = FoodDeliveryTheme.dimensions.smallSpace,
                    ),
                    labelStringId = R.string.action_settings_add_email
                ) {
                    viewModel.onAddEmailClicked()
                }
            } else {
                NavigationTextCard(
                    modifier = Modifier.padding(
                        start = FoodDeliveryTheme.dimensions.mediumSpace,
                        end = FoodDeliveryTheme.dimensions.mediumSpace,
                        top = FoodDeliveryTheme.dimensions.smallSpace,
                    ),
                    hint = R.string.hint_settings_email,
                    label = email
                ) {
                    viewModel.onEditEmailClicked(email)
                }
            }
            NavigationTextCard(
                modifier = Modifier.padding(
                    start = FoodDeliveryTheme.dimensions.mediumSpace,
                    end = FoodDeliveryTheme.dimensions.mediumSpace,
                    top = FoodDeliveryTheme.dimensions.smallSpace,
                    bottom = FoodDeliveryTheme.dimensions.mediumSpace,
                ),
                hint = R.string.hint_settings_city,
                label = settings.cityName
            ) {
                viewModel.onCityClicked()
            }
        }
    }


    @Preview(showSystemUi = true)
    @Composable
    fun SettingsScreenWithEmailPreview() {
        SettingsScreen(
            State.Success(
                Settings(
                    user = User(
                        uuid = "",
                        phone = "+7 999 000-00-00",
                        email = "email@bb.com"
                    ),
                    cityName = "Москва"
                )
            )
        )
    }

    @Preview(showSystemUi = true)
    @Composable
    fun SettingsScreenWithoutEmailPreview() {
        SettingsScreen(
            State.Success(
                Settings(
                    user = User(
                        uuid = "",
                        phone = "+7 999 000-00-00",
                        email = null
                    ),
                    cityName = "Москва"
                )
            )
        )
    }

    @Preview(showSystemUi = true)
    @Composable
    fun LoadingSettingsScreenPreview() {
        SettingsScreen(State.Loading())
    }
}