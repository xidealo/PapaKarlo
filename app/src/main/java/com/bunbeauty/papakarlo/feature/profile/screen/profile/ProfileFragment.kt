package com.bunbeauty.papakarlo.feature.profile.screen.profile

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragmentWithSharedViewModel
import com.bunbeauty.papakarlo.common.model.SuccessLoginDirection
import com.bunbeauty.papakarlo.common.ui.element.button.MainButton
import com.bunbeauty.papakarlo.common.ui.element.card.NavigationIconCard
import com.bunbeauty.papakarlo.common.ui.screen.ErrorScreen
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.bold
import com.bunbeauty.papakarlo.common.ui.element.toolbar.FoodDeliveryCartAction
import com.bunbeauty.papakarlo.common.ui.element.toolbar.FoodDeliveryToolbarScreen
import com.bunbeauty.papakarlo.databinding.FragmentProfileBinding
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import com.bunbeauty.papakarlo.feature.order.model.OrderItem
import com.bunbeauty.papakarlo.feature.order.ui.OrderItem
import com.bunbeauty.papakarlo.feature.product_details.ProductDetailsFragmentDirections
import com.bunbeauty.papakarlo.feature.top_cart.TopCartUi
import com.bunbeauty.shared.domain.model.order.OrderStatus
import com.bunbeauty.shared.presentation.profile.ProfileState
import com.bunbeauty.shared.presentation.profile.ProfileViewModel
import com.google.android.material.transition.MaterialFadeThrough
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragmentWithSharedViewModel(R.layout.fragment_profile) {

    override val viewBinding by viewBinding(FragmentProfileBinding::bind)
    private val viewModel: ProfileViewModel by viewModel()

    private val profileUiStateMapper: ProfileUiStateMapper by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough()
    }

    @OptIn(ExperimentalLifecycleComposeApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        overrideBackPressedCallback()
        super.onViewCreated(view, savedInstanceState)

        viewModel.update()
        viewBinding.fragmentProfileCvMain.setContentWithTheme {
            val profileState by viewModel.profileState.collectAsStateWithLifecycle()
            ProfileScreen(
                profileUi = profileUiStateMapper.map(profileState),
                onLastOrderClicked = viewModel::onLastOrderClicked,
                onSettingsClicked = viewModel::onSettingsClicked,
                onYourAddressesClicked = viewModel::onYourAddressesClicked,
                onOrderHistoryClicked = viewModel::onOrderHistoryClicked,
                onPaymentClicked = viewModel::onPaymentClicked,
            )
            LaunchedEffect(profileState.eventList) {
                handleEventList(profileState.eventList)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.observeLastOrder()
    }

    override fun onStop() {
        viewModel.stopLastOrderObservation()
        super.onStop()
    }

    @Composable
    private fun ProfileScreen(
        profileUi: ProfileUi,
        onLastOrderClicked: (String, String) -> Unit,
        onSettingsClicked: () -> Unit,
        onYourAddressesClicked: () -> Unit,
        onOrderHistoryClicked: () -> Unit,
        onPaymentClicked: () -> Unit,
    ) {
        FoodDeliveryToolbarScreen(
            title = stringResource(R.string.title_profile),
            topActions = listOf(
                FoodDeliveryCartAction(
                    topCartUi = profileUi.topCartUi,
                ) {
                    findNavController().navigate(ProductDetailsFragmentDirections.globalConsumerCartFragment())
                }
            ),
            actionButton = {
                if (profileUi.state == ProfileState.State.UNAUTHORIZED) {
                    MainButton(
                        modifier = Modifier
                            .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace),
                        textStringId = R.string.action_profile_login
                    ) {
                        viewModel.onLoginClicked()
                    }
                }
            }
        ) {
            when (profileUi.state) {
                ProfileState.State.AUTHORIZED -> AuthorizedProfileScreen(
                    profileUi,
                    onLastOrderClicked = onLastOrderClicked,
                    onSettingsClick = onSettingsClicked,
                    onYourAddressesClicked = onYourAddressesClicked,
                    onOrderHistoryClicked = onOrderHistoryClicked,
                    onPaymentClicked = onPaymentClicked,
                )
                ProfileState.State.UNAUTHORIZED -> UnauthorizedProfileScreen()
                ProfileState.State.LOADING -> LoadingScreen()
                ProfileState.State.ERROR -> {
                    ErrorScreen(
                        mainTextId = R.string.error_profile_loading
                    ) {
                        viewModel.update()
                    }
                }
            }
        }
    }

    private fun handleEventList(eventList: List<ProfileState.Event>) {
        eventList.forEach { event ->
            when (event) {
                is ProfileState.Event.OpenOrderDetails -> {
                    findNavController().navigate(
                        ProfileFragmentDirections.toOrderDetailsFragment(
                            event.orderUuid,
                            event.orderCode
                        )
                    )
                }
                ProfileState.Event.OpenSettings -> {
                    findNavController().navigate(ProfileFragmentDirections.toSettingsFragment())
                }
                ProfileState.Event.OpenAddressList -> {
                    findNavController().navigate(
                        ProfileFragmentDirections.toNavAddress(
                            false
                        )
                    )
                }
                ProfileState.Event.OpenOrderList -> {
                    findNavController().navigate(ProfileFragmentDirections.toOrdersFragment())
                }
                ProfileState.Event.ShowPayment -> {
                    findNavController().navigate(ProfileFragmentDirections.toPaymentBottomSheet())
                }
                ProfileState.Event.ShowFeedback -> {
                    findNavController().navigate(ProfileFragmentDirections.toFeedbackBottomSheet())
                }
                ProfileState.Event.ShowAboutApp -> {
                    findNavController().navigate(ProfileFragmentDirections.toAboutAppBottomSheet())
                }
                ProfileState.Event.OpenLogin -> {
                    findNavController().navigate(
                        ProfileFragmentDirections.toLoginFragment(
                            SuccessLoginDirection.BACK_TO_PROFILE
                        )
                    )
                }
            }
        }
        viewModel.consumeEventList(eventList)
    }

    @Composable
    private fun AuthorizedProfileScreen(
        profile: ProfileUi,
        onLastOrderClicked: (String, String) -> Unit,
        onSettingsClick: () -> Unit,
        onYourAddressesClicked: () -> Unit,
        onOrderHistoryClicked: () -> Unit,
        onPaymentClicked: () -> Unit,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(FoodDeliveryTheme.dimensions.mediumSpace)
        ) {
            profile.orderItem?.let { orderItem ->
                OrderItem(orderItem = orderItem) {
                    onLastOrderClicked(orderItem.uuid, orderItem.code)
                }
                Spacer(modifier = Modifier.height(FoodDeliveryTheme.dimensions.mediumSpace))
            }

            NavigationIconCard(
                modifier = Modifier.fillMaxWidth(),
                iconId = R.drawable.ic_settings,
                iconDescription = R.string.description_ic_settings,
                labelStringId = R.string.action_profile_settings,
                onClick = onSettingsClick
            )

            NavigationIconCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                iconId = R.drawable.ic_address,
                iconDescription = R.string.description_ic_my_addresses,
                labelStringId = R.string.action_profile_my_addresses,
                onClick = onYourAddressesClicked
            )

            NavigationIconCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                iconId = R.drawable.ic_history,
                iconDescription = R.string.description_ic_my_orders,
                labelStringId = R.string.action_profile_my_orders,
                onClick = onOrderHistoryClicked
            )

            NavigationIconCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                iconId = R.drawable.ic_payment,
                iconDescription = R.string.description_ic_payment,
                labelStringId = R.string.action_profile_payment,
                onClick = onPaymentClicked
            )

            ProfileInfoCards(
                modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.smallSpace)
            )

            // Spacer(modifier = Modifier.height())
        }
    }

    @Composable
    private fun UnauthorizedProfileScreen() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(FoodDeliveryTheme.dimensions.mediumSpace),
        ) {
            ProfileInfoCards()

            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier.padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.size(120.dp),
                    painter = painterResource(R.drawable.empty_profile),
                    contentDescription = stringResource(R.string.description_empty_profile)
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp),
                    text = stringResource(R.string.title_profile_no_profile),
                    style = FoodDeliveryTheme.typography.titleMedium.bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = stringResource(R.string.msg_profile_no_profile),
                    style = FoodDeliveryTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.weight(1f))

            Spacer(modifier = Modifier.height(FoodDeliveryTheme.dimensions.scrollScreenBottomSpace))
        }
    }

    @Composable
    private fun ProfileInfoCards(modifier: Modifier = Modifier) {
        Column(modifier = modifier) {
            NavigationIconCard(
                modifier = Modifier.fillMaxWidth(),
                iconId = R.drawable.ic_star,
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

    @Preview(showSystemUi = true)
    @Composable
    private fun AuthorizedProfileScreenWithLastOrderPreview() {
        FoodDeliveryTheme {
            ProfileScreen(
                ProfileUi(
                    orderItem = OrderItem(
                        uuid = "",
                        status = OrderStatus.NOT_ACCEPTED,
                        statusName = OrderStatus.NOT_ACCEPTED.name,
                        code = "А-12",
                        dateTime = "10-10-10 20:20",
                    ),
                    state = ProfileState.State.AUTHORIZED,
                    topCartUi = TopCartUi(
                        cost = "100",
                        count = "2",
                    ),
                ),
                onLastOrderClicked = { s, s1 -> },
                onSettingsClicked = {},
                onYourAddressesClicked = {},
                onOrderHistoryClicked = {},
                onPaymentClicked = {},
            )
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun AuthorizedProfileScreenWithoutLastOrderPreview() {
        FoodDeliveryTheme {
            ProfileScreen(
                ProfileUi(
                    orderItem = null,
                    state = ProfileState.State.AUTHORIZED,
                    topCartUi = TopCartUi(
                        cost = "100",
                        count = "2",
                    ),
                ),
                onLastOrderClicked = { s, s1 -> },
                onSettingsClicked = {},
                onYourAddressesClicked = {},
                onOrderHistoryClicked = {},
                onPaymentClicked = {},
            )
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun UnauthorizedProfileScreenPreview() {
        FoodDeliveryTheme {
            ProfileScreen(
                ProfileUi(
                    orderItem = null,
                    state = ProfileState.State.UNAUTHORIZED,
                    topCartUi = TopCartUi(
                        cost = "100",
                        count = "2",
                    ),
                ),
                onLastOrderClicked = { s, s1 -> },
                onSettingsClicked = {},
                onYourAddressesClicked = {},
                onOrderHistoryClicked = {},
                onPaymentClicked = {},
            )
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun LoadingProfileScreenPreview() {
        FoodDeliveryTheme {
            ProfileScreen(
                ProfileUi(
                    orderItem = null,
                    state = ProfileState.State.LOADING,
                    topCartUi = TopCartUi(
                        cost = "100",
                        count = "2",
                    ),
                ),
                onLastOrderClicked = { s, s1 -> },
                onSettingsClicked = {},
                onYourAddressesClicked = {},
                onOrderHistoryClicked = {},
                onPaymentClicked = {},
            )
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun ErrorProfileScreenPreview() {
        FoodDeliveryTheme {
            ProfileScreen(
                ProfileUi(
                    orderItem = null,
                    state = ProfileState.State.ERROR,
                    topCartUi = TopCartUi(
                        cost = "100",
                        count = "2",
                    ),
                ),
                onLastOrderClicked = { s, s1 -> },
                onSettingsClicked = {},
                onYourAddressesClicked = {},
                onOrderHistoryClicked = {},
                onPaymentClicked = {},
            )
        }
    }
}
