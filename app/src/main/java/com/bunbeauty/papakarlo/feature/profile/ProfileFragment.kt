package com.bunbeauty.papakarlo.feature.profile

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.common.state.State
import com.bunbeauty.papakarlo.compose.NavigationCard
import com.bunbeauty.papakarlo.compose.OrderItem
import com.bunbeauty.papakarlo.compose.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.compose.theme.mediumRoundedCornerShape
import com.bunbeauty.papakarlo.databinding.FragmentProfileBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.feature.profile.order.order_list.OrderItem

class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    override val viewModel: ProfileViewModel by viewModels { viewModelFactory }
    override val viewBinding by viewBinding(FragmentProfileBinding::bind)

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    @OptIn(ExperimentalMaterialApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        overrideBackPressedCallback()
        super.onViewCreated(view, savedInstanceState)

        viewBinding.run {
            fragmentProfileCvCards.apply {
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                setContent {
                    val state: State<ProfileUI> by viewModel.profileUIState.collectAsState()
                    ProfileCards(state)
                }
            }
        }
    }

    @Composable
    private fun ProfileCards(profileState: State<ProfileUI>) {
        FoodDeliveryTheme {
            Box(modifier = Modifier.fillMaxSize()) {
                Column {
                    if (profileState is State.Success) {
                        profileState.data.lastOrderItem?.let { lastOrderItem ->
                            OrderItem(
                                modifier = Modifier
                                    .padding(
                                        top = FoodDeliveryTheme.dimensions.mediumSpace,
                                        start = FoodDeliveryTheme.dimensions.mediumSpace,
                                        end = FoodDeliveryTheme.dimensions.mediumSpace,
                                    ),
                                orderItem = lastOrderItem
                            ) {
                                viewModel.onLastOrderClicked(lastOrderItem)
                            }
                        }

                        NavigationCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    top = FoodDeliveryTheme.dimensions.mediumSpace,
                                    start = FoodDeliveryTheme.dimensions.mediumSpace,
                                    end = FoodDeliveryTheme.dimensions.mediumSpace,
                                ),
                            iconId = R.drawable.ic_settings,
                            iconDescription = R.string.description_ic_settings,
                            label = R.string.action_profile_settings
                        ) {
                            viewModel.onSettingsClicked()
                        }

                        if (profileState.data.hasAddresses) {
                            NavigationCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        top = FoodDeliveryTheme.dimensions.smallSpace,
                                        start = FoodDeliveryTheme.dimensions.mediumSpace,
                                        end = FoodDeliveryTheme.dimensions.mediumSpace,
                                    ),
                                iconId = R.drawable.ic_address,
                                iconDescription = R.string.description_ic_settings,
                                label = R.string.action_profile_your_addresses
                            ) {
                                viewModel.onYourAddressesClicked()
                            }
                        } else {
                            NavigationCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        top = FoodDeliveryTheme.dimensions.smallSpace,
                                        start = FoodDeliveryTheme.dimensions.mediumSpace,
                                        end = FoodDeliveryTheme.dimensions.mediumSpace,
                                    ),
                                iconId = R.drawable.ic_add,
                                iconDescription = R.string.description_ic_create_address,
                                label = R.string.action_profile_create_address
                            ) {
                                viewModel.onAddAddressClicked()
                            }
                        }

                        NavigationCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    top = FoodDeliveryTheme.dimensions.smallSpace,
                                    start = FoodDeliveryTheme.dimensions.mediumSpace,
                                    end = FoodDeliveryTheme.dimensions.mediumSpace,
                                ),
                            iconId = R.drawable.ic_history,
                            iconDescription = R.string.description_ic_order_history,
                            label = R.string.action_profile_order_history
                        ) {
                            viewModel.onOrderHistoryClicked(profileState.data.userUuid)
                        }
                        NavigationCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    top = FoodDeliveryTheme.dimensions.smallSpace,
                                    start = FoodDeliveryTheme.dimensions.mediumSpace,
                                    end = FoodDeliveryTheme.dimensions.mediumSpace,
                                ),
                            iconId = R.drawable.ic_payment,
                            iconDescription = R.string.description_ic_payment,
                            label = R.string.action_profile_payment
                        ) {
                            viewModel.onPaymentClicked()
                        }
                    }

                    if (profileState is State.Success || profileState is State.Empty) {
                        val topSpace = if (profileState is State.Success) {
                            FoodDeliveryTheme.dimensions.smallSpace
                        } else {
                            FoodDeliveryTheme.dimensions.mediumSpace
                        }
                        NavigationCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    top = topSpace,
                                    start = FoodDeliveryTheme.dimensions.mediumSpace,
                                    end = FoodDeliveryTheme.dimensions.mediumSpace,
                                ),
                            iconId = R.drawable.ic_feedback,
                            iconDescription = R.string.description_ic_feedback,
                            label = R.string.title_feedback
                        ) {
                            viewModel.onFeedbackClicked()
                        }
                        NavigationCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    top = FoodDeliveryTheme.dimensions.smallSpace,
                                    start = FoodDeliveryTheme.dimensions.mediumSpace,
                                    end = FoodDeliveryTheme.dimensions.mediumSpace,
                                    bottom = FoodDeliveryTheme.dimensions.mediumSpace
                                ),
                            iconId = R.drawable.ic_info,
                            iconDescription = R.string.description_ic_about,
                            label = R.string.title_about_app
                        ) {
                            viewModel.onAboutAppClicked()
                        }
                    }
                }
                if (profileState is State.Empty) {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.empty_profile_papa_karlo),
                            contentDescription = stringResource(id = R.string.description_empty_profile)
                        )
                        Text(
                            modifier = Modifier.padding(FoodDeliveryTheme.dimensions.mediumSpace),
                            text = stringResource(id = R.string.msg_profile_no_profile),
                            style = FoodDeliveryTheme.typography.body1,
                            textAlign = TextAlign.Center
                        )
                    }
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(FoodDeliveryTheme.dimensions.buttonHeight)
                            .align(Alignment.BottomCenter)
                            .padding(
                                start = FoodDeliveryTheme.dimensions.mediumSpace,
                                end = FoodDeliveryTheme.dimensions.mediumSpace,
                                bottom = FoodDeliveryTheme.dimensions.mediumSpace
                            ),
                        colors = FoodDeliveryTheme.colors.buttonColors(),
                        shape = mediumRoundedCornerShape,
                        onClick = {
                            viewModel.onLoginClicked()
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.action_profile_login).uppercase(),
                            style = FoodDeliveryTheme.typography.button,
                            color = FoodDeliveryTheme.colors.onPrimary
                        )
                    }
                }
            }
        }
    }

    @Preview
    @Composable
    private fun ProfileCardsWithAddressesAndLastOrderPreview() {
        ProfileCards(
            State.Success(
                ProfileUI(
                    userUuid = "",
                    hasAddresses = true,
                    lastOrderItem = OrderItem(
                        uuid = "",
                        status = OrderStatus.NOT_ACCEPTED,
                        statusName = "Обрабатывается",
                        statusColorId = 0,
                        code = "А-12",
                        dateTime = "30 января 12:59",
                    )
                )
            )
        )
    }

    @Preview
    @Composable
    private fun ProfileCardsWithoutAddressesAndLastOrderPreview() {
        ProfileCards(
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
    private fun EmptyProfileCardsPreview() {
        ProfileCards(State.Empty())
    }
}