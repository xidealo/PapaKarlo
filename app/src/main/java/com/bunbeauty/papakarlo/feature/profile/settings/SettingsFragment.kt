package com.bunbeauty.papakarlo.feature.profile.settings

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.setFragmentResultListener
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.common.Constants.EMAIL_REQUEST_KEY
import com.bunbeauty.common.Constants.RESULT_EMAIL_KEY
import com.bunbeauty.domain.model.profile.Settings
import com.bunbeauty.domain.model.profile.User
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.common.state.State
import com.bunbeauty.papakarlo.compose.card.NavigationCard
import com.bunbeauty.papakarlo.compose.card.NavigationTextCard
import com.bunbeauty.papakarlo.compose.card.TextCard
import com.bunbeauty.papakarlo.compose.element.CircularProgressBar
import com.bunbeauty.papakarlo.compose.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.databinding.FragmentSettingsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    override val viewModel: SettingsViewModel by viewModel()
    override val viewBinding by viewBinding(FragmentSettingsBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.fragmentSettingsCvMain.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val userState: State<Settings> by viewModel.settingsState.collectAsState()
                SettingsScreen(userState)
            }
        }
        setFragmentResultListener(EMAIL_REQUEST_KEY) { _, bundle ->
            bundle.getString(RESULT_EMAIL_KEY)?.let { email ->
                viewModel.onEmailChanged(email)
            }
        }
    }

    @Composable
    fun SettingsScreen(userState: State<Settings>) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (userState is State.Success) {
                Column {
                    TextCard(
                        modifier = Modifier.padding(
                            start = FoodDeliveryTheme.dimensions.mediumSpace,
                            end = FoodDeliveryTheme.dimensions.mediumSpace,
                            top = FoodDeliveryTheme.dimensions.mediumSpace,
                        ),
                        hint = R.string.hint_settings_phone,
                        label = userState.data.user.phone
                    )
                    val email = userState.data.user.email
                    if (email.isNullOrEmpty()) {
                        NavigationCard(
                            modifier = Modifier.padding(
                                start = FoodDeliveryTheme.dimensions.mediumSpace,
                                end = FoodDeliveryTheme.dimensions.mediumSpace,
                                top = FoodDeliveryTheme.dimensions.smallSpace,
                            ),
                            label = R.string.action_settings_add_email
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
                        label = userState.data.cityName
                    ) {
                        viewModel.onCityClicked()
                    }
                }
            } else {
                CircularProgressBar(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }

    @Preview
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

    @Preview
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

    @Preview
    @Composable
    fun LoadingSettingsScreenPreview() {
        SettingsScreen(
            State.Loading()
        )
    }
}