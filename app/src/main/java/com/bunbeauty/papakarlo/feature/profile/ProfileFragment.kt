package com.bunbeauty.papakarlo.feature.profile

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.Image
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
import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.common.state.State
import com.bunbeauty.papakarlo.compose.card.NavigationIconCard
import com.bunbeauty.papakarlo.compose.element.CircularProgressBar
import com.bunbeauty.papakarlo.compose.element.MainButton
import com.bunbeauty.papakarlo.compose.item.OrderItem
import com.bunbeauty.papakarlo.compose.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.databinding.FragmentProfileBinding
import com.bunbeauty.papakarlo.extensions.compose
import com.bunbeauty.papakarlo.feature.profile.order.order_list.OrderItemModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    override val viewModel: ProfileViewModel by viewModel()
    override val viewBinding by viewBinding(FragmentProfileBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        overrideBackPressedCallback()
        super.onViewCreated(view, savedInstanceState)

        viewBinding.fragmentProfileCvMain.compose {
            val state: State<ProfileUI> by viewModel.profileUIState.collectAsState()
            ProfileScreen(state)
        }
    }

    @Composable
    private fun ProfileScreen(profileState: State<ProfileUI>) {
        FoodDeliveryTheme {
            when (profileState) {
                is State.Success -> SuccessProfileScreen(profileState.data)
                is State.Empty -> EmptyProfileScreen()
                is State.Loading -> LoadingProfileScreen()
            }
        }
    }

    @Composable
    private fun SuccessProfileScreen(profile: ProfileUI) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column {
                profile.lastOrderItem?.let { lastOrderItem ->
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

                NavigationIconCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = FoodDeliveryTheme.dimensions.mediumSpace,
                            start = FoodDeliveryTheme.dimensions.mediumSpace,
                            end = FoodDeliveryTheme.dimensions.mediumSpace,
                        ),
                    iconId = R.drawable.ic_settings,
                    iconDescription = R.string.description_ic_settings,
                    labelStringId = R.string.action_profile_settings
                ) {
                    viewModel.onSettingsClicked()
                }

                if (profile.hasAddresses) {
                    NavigationIconCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = FoodDeliveryTheme.dimensions.smallSpace,
                                start = FoodDeliveryTheme.dimensions.mediumSpace,
                                end = FoodDeliveryTheme.dimensions.mediumSpace,
                            ),
                        iconId = R.drawable.ic_address,
                        iconDescription = R.string.description_ic_settings,
                        labelStringId = R.string.action_profile_your_addresses
                    ) {
                        viewModel.onYourAddressesClicked()
                    }
                } else {
                    NavigationIconCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = FoodDeliveryTheme.dimensions.smallSpace,
                                start = FoodDeliveryTheme.dimensions.mediumSpace,
                                end = FoodDeliveryTheme.dimensions.mediumSpace,
                            ),
                        iconId = R.drawable.ic_add,
                        iconDescription = R.string.description_ic_create_address,
                        labelStringId = R.string.action_profile_create_address
                    ) {
                        viewModel.onAddAddressClicked()
                    }
                }

                NavigationIconCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = FoodDeliveryTheme.dimensions.smallSpace,
                            start = FoodDeliveryTheme.dimensions.mediumSpace,
                            end = FoodDeliveryTheme.dimensions.mediumSpace,
                        ),
                    iconId = R.drawable.ic_history,
                    iconDescription = R.string.description_ic_order_history,
                    labelStringId = R.string.action_profile_order_history
                ) {
                    viewModel.onOrderHistoryClicked(profile.userUuid)
                }
                NavigationIconCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = FoodDeliveryTheme.dimensions.smallSpace,
                            start = FoodDeliveryTheme.dimensions.mediumSpace,
                            end = FoodDeliveryTheme.dimensions.mediumSpace,
                        ),
                    iconId = R.drawable.ic_payment,
                    iconDescription = R.string.description_ic_payment,
                    labelStringId = R.string.action_profile_payment
                ) {
                    viewModel.onPaymentClicked()
                }
                ProfileInfoCards(true)
            }
        }
    }

    @Composable
    private fun EmptyProfileScreen() {
        Box(modifier = Modifier.fillMaxSize()) {
            ProfileInfoCards(false)
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
            MainButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(
                        start = FoodDeliveryTheme.dimensions.mediumSpace,
                        end = FoodDeliveryTheme.dimensions.mediumSpace,
                        bottom = FoodDeliveryTheme.dimensions.mediumSpace
                    ),
                textStringId = R.string.action_profile_login
            ) {
                viewModel.onLoginClicked()
            }
        }
    }

    @Composable
    private fun LoadingProfileScreen() {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressBar(modifier = Modifier.align(Alignment.Center))
        }
    }

    @Composable
    private fun ProfileInfoCards(isSuccess: Boolean) {
        Column {
            val topSpace = if (isSuccess) {
                FoodDeliveryTheme.dimensions.smallSpace
            } else {
                FoodDeliveryTheme.dimensions.mediumSpace
            }
            NavigationIconCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = topSpace,
                        start = FoodDeliveryTheme.dimensions.mediumSpace,
                        end = FoodDeliveryTheme.dimensions.mediumSpace,
                    ),
                iconId = R.drawable.ic_feedback,
                iconDescription = R.string.description_ic_feedback,
                labelStringId = R.string.title_feedback
            ) {
                viewModel.onFeedbackClicked()
            }
            NavigationIconCard(
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
                labelStringId = R.string.title_about_app
            ) {
                viewModel.onAboutAppClicked()
            }
        }
    }

    @Preview
    @Composable
    private fun ProfileCardsWithAddressesAndLastOrderPreview() {
        ProfileScreen(
            State.Success(
                ProfileUI(
                    userUuid = "",
                    hasAddresses = true,
                    lastOrderItem = OrderItemModel(
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
    private fun EmptyProfileCardsPreview() {
        ProfileScreen(State.Empty())
    }

    @Preview
    @Composable
    private fun LoadingProfileCardsPreview() {
        ProfileScreen(State.Loading())
    }
}