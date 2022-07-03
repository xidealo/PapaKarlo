package com.bunbeauty.papakarlo.feature.profile.screen.profile

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.shared.domain.model.order.OrderStatus
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.common.state.State
import com.bunbeauty.papakarlo.common.ui.element.card.NavigationIconCard
import com.bunbeauty.papakarlo.common.ui.element.MainButton
import com.bunbeauty.papakarlo.feature.order.ui.OrderItem
import com.bunbeauty.papakarlo.common.ui.screen.ErrorScreen
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.databinding.FragmentProfileBinding
import com.bunbeauty.papakarlo.extensions.compose
import com.bunbeauty.papakarlo.feature.order.model.OrderItem
import com.bunbeauty.papakarlo.feature.profile.model.ProfileUI
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    override val viewModel: ProfileViewModel by viewModel()
    override val viewBinding by viewBinding(FragmentProfileBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        overrideBackPressedCallback()
        super.onViewCreated(view, savedInstanceState)

        viewModel.getProfile()
        viewBinding.fragmentProfileCvMain.compose {
            val state: State<ProfileUI> by viewModel.profileUIState.collectAsState()
            ProfileScreen(state)
        }
    }

    @Composable
    private fun ProfileScreen(profileState: State<ProfileUI>) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(FoodDeliveryTheme.colors.background)
        ) {
            when (profileState) {
                is State.Success -> SuccessProfileScreen(profileState.data)
                is State.Empty -> EmptyProfileScreen()
                is State.Loading -> LoadingScreen()
                is State.Error -> ErrorScreen(profileState.message) {
                    viewModel.getProfile()
                }
            }
        }
    }

    @Composable
    private fun SuccessProfileScreen(profile: ProfileUI) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace)
            ) {
                profile.lastOrderItem?.let { lastOrderItem ->
                    OrderItem(
                        modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.mediumSpace),
                        orderItem = lastOrderItem
                    ) {
                        viewModel.onLastOrderClicked(lastOrderItem)
                    }
                }
                NavigationIconCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = FoodDeliveryTheme.dimensions.mediumSpace),
                    iconId = R.drawable.ic_settings,
                    iconDescription = R.string.description_ic_settings,
                    labelStringId = R.string.action_profile_settings
                ) {
                    viewModel.onSettingsClicked()
                }
                NavigationIconCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                    iconId = R.drawable.ic_address,
                    iconDescription = R.string.description_ic_my_addresses,
                    labelStringId = R.string.action_profile_my_addresses
                ) {
                    viewModel.onYourAddressesClicked()
                }
                NavigationIconCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                    iconId = R.drawable.ic_history,
                    iconDescription = R.string.description_ic_my_orders,
                    labelStringId = R.string.action_profile_my_orders
                ) {
                    viewModel.onOrderHistoryClicked(profile.userUuid)
                }
                NavigationIconCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                    iconId = R.drawable.ic_payment,
                    iconDescription = R.string.description_ic_payment,
                    labelStringId = R.string.action_profile_payment
                ) {
                    viewModel.onPaymentClicked()
                }
                ProfileInfoCards(
                    modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.smallSpace)
                )
            }
        }
    }

    @Composable
    private fun EmptyProfileScreen() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(FoodDeliveryTheme.dimensions.mediumSpace),
        ) {
            ProfileInfoCards()
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.empty_profile),
                    contentDescription = stringResource(R.string.description_empty_profile)
                )
                Text(
                    modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.mediumSpace),
                    text = stringResource(R.string.msg_profile_no_profile),
                    style = FoodDeliveryTheme.typography.body1,
                    textAlign = TextAlign.Center
                )
            }
            MainButton(
                modifier = Modifier.align(Alignment.BottomCenter),
                textStringId = R.string.action_profile_login
            ) {
                viewModel.onLoginClicked()
            }
        }
    }

    @Composable
    private fun ProfileInfoCards(modifier: Modifier = Modifier) {
        Column(modifier = modifier) {
            NavigationIconCard(
                modifier = Modifier.fillMaxWidth(),
                iconId = R.drawable.ic_feedback,
                iconDescription = R.string.description_ic_feedback,
                labelStringId = R.string.title_feedback
            ) {
                viewModel.onFeedbackClicked()
            }
            NavigationIconCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                iconId = R.drawable.ic_info,
                iconDescription = R.string.description_ic_about,
                labelStringId = R.string.title_about_app
            ) {
                viewModel.onAboutAppClicked()
            }
        }
    }

    @Preview
    @Composable
    private fun ProfileScreenWithAddressesAndLastOrderPreview() {
        ProfileScreen(
            State.Success(
                ProfileUI(
                    userUuid = "",
                    hasAddresses = true,
                    lastOrderItem = OrderItem(
                        uuid = "",
                        status = OrderStatus.NOT_ACCEPTED,
                        statusName = "Обрабатывается",
                        code = "А-12",
                        dateTime = "30 января 12:59",
                    )
                )
            )
        )
    }

    @Preview
    @Composable
    private fun ProfileScreenWithoutAddressesAndLastOrderPreview() {
        ProfileScreen(
            State.Success(
                ProfileUI(
                    userUuid = "",
                    hasAddresses = false,
                    lastOrderItem = null
                )
            )
        )
    }

    @Preview
    @Composable
    private fun EmptyProfileScreenPreview() {
        ProfileScreen(State.Empty())
    }

    @Preview
    @Composable
    private fun LoadingProfileScreenPreview() {
        ProfileScreen(State.Loading())
    }

    @Preview
    @Composable
    private fun ErrorProfileScreenPreview() {
        ProfileScreen(State.Error("Не удалось загрузить профиль"))
    }
}