package com.bunbeauty.papakarlo.feature.profile.screen.profile

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.bunbeauty.papakarlo.common.extension.navigateSafe
import com.bunbeauty.papakarlo.common.model.SuccessLoginDirection
import com.bunbeauty.papakarlo.common.ui.element.FoodDeliveryScaffold
import com.bunbeauty.papakarlo.common.ui.element.button.MainButton
import com.bunbeauty.papakarlo.common.ui.element.card.NavigationIconCard
import com.bunbeauty.papakarlo.common.ui.element.top_bar.FoodDeliveryCartAction
import com.bunbeauty.papakarlo.common.ui.screen.ErrorScreen
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.bold
import com.bunbeauty.papakarlo.databinding.LayoutComposeBinding
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import com.bunbeauty.papakarlo.feature.order.model.OrderItem
import com.bunbeauty.papakarlo.feature.order.ui.OrderItem
import com.bunbeauty.papakarlo.feature.product_details.ProductDetailsFragmentDirections
import com.bunbeauty.papakarlo.feature.profile.screen.payment.PaymentBottomSheet
import com.bunbeauty.papakarlo.feature.profile.screen.payment.PaymentMethodsArgument
import com.bunbeauty.papakarlo.feature.top_cart.TopCartUi
import com.bunbeauty.shared.domain.model.order.OrderStatus
import com.bunbeauty.shared.presentation.profile.ProfileState
import com.bunbeauty.shared.presentation.profile.ProfileViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragmentWithSharedViewModel(R.layout.layout_compose) {

    override val viewBinding by viewBinding(LayoutComposeBinding::bind)
    private val viewModel: ProfileViewModel by viewModel()

    private val profileUiStateMapper: ProfileUiStateMapper by inject()
    private val paymentMethodUiStateMapper: PaymentMethodUiStateMapper by inject()

    @OptIn(ExperimentalLifecycleComposeApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        overrideBackPressedCallback()
        super.onViewCreated(view, savedInstanceState)

        viewModel.update()
        viewBinding.root.setContentWithTheme {
            val profileState by viewModel.profileState.collectAsStateWithLifecycle()
            ProfileScreen(
                profileUi = profileUiStateMapper.map(profileState),
                onLastOrderClicked = viewModel::onLastOrderClicked,
                onSettingsClicked = viewModel::onSettingsClicked,
                onYourAddressesClicked = viewModel::onYourAddressesClicked,
                onOrderHistoryClicked = viewModel::onOrderHistoryClicked,
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
    ) {
        FoodDeliveryScaffold(
            title = stringResource(R.string.title_profile),
            topActions = listOf(
                FoodDeliveryCartAction(
                    topCartUi = profileUi.topCartUi,
                ) {
                    findNavController().navigateSafe(ProductDetailsFragmentDirections.globalConsumerCartFragment())
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
            },
        ) {
            when (profileUi.state) {
                ProfileState.State.AUTHORIZED -> AuthorizedProfileScreen(
                    profileUi,
                    onLastOrderClicked = onLastOrderClicked,
                    onSettingsClick = onSettingsClicked,
                    onYourAddressesClicked = onYourAddressesClicked,
                    onOrderHistoryClicked = onOrderHistoryClicked,
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
                    findNavController().navigateSafe(
                        ProfileFragmentDirections.toOrderDetailsFragment(
                            event.orderUuid,
                            event.orderCode
                        )
                    )
                }
                ProfileState.Event.OpenSettings -> {
                    findNavController().navigateSafe(ProfileFragmentDirections.toSettingsFragment())
                }
                ProfileState.Event.OpenAddressList -> {
                    findNavController().navigateSafe(
                        ProfileFragmentDirections.toNavAddress(
                            false
                        )
                    )
                }
                ProfileState.Event.OpenOrderList -> {
                    findNavController().navigateSafe(ProfileFragmentDirections.toOrdersFragment())
                }
                is ProfileState.Event.ShowPayment -> {
                    PaymentBottomSheet.show(
                        fragmentManager = parentFragmentManager,
                        paymentMethodsArgument = PaymentMethodsArgument(
                            paymentMethodList = paymentMethodUiStateMapper.map(
                                event.paymentMethodList
                            )
                        )
                    )
                }
                ProfileState.Event.ShowFeedback -> {
                    findNavController().navigateSafe(ProfileFragmentDirections.toFeedbackBottomSheet())
                }
                ProfileState.Event.ShowAboutApp -> {
                    findNavController().navigateSafe(ProfileFragmentDirections.toAboutAppBottomSheet())
                }
                ProfileState.Event.OpenLogin -> {
                    findNavController().navigateSafe(
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
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(FoodDeliveryTheme.dimensions.mediumSpace)
        ) {
            profile.orderItem?.let { orderItem ->
                OrderItem(
                    modifier = Modifier.padding(bottom = 16.dp),
                    orderItem = orderItem,
                    onClick = {
                        onLastOrderClicked(orderItem.uuid, orderItem.code)
                    }
                )
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
                    .padding(top = 8.dp),
                iconId = R.drawable.ic_address,
                iconDescription = R.string.description_ic_my_addresses,
                labelStringId = R.string.action_profile_my_addresses,
                onClick = onYourAddressesClicked
            )
            NavigationIconCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                iconId = R.drawable.ic_history,
                iconDescription = R.string.description_ic_my_orders,
                labelStringId = R.string.action_profile_my_orders,
                onClick = onOrderHistoryClicked
            )
            ProfileInfoCards(modifier = Modifier.padding(top = 8.dp))
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
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(FoodDeliveryTheme.colors.statusColors.info),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(64.dp),
                        painter = painterResource(R.drawable.ic_profile),
                        tint = FoodDeliveryTheme.colors.statusColors.onStatus,
                        contentDescription = stringResource(R.string.description_empty_profile)
                    )
                }
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp),
                    text = stringResource(R.string.title_profile_no_profile),
                    style = FoodDeliveryTheme.typography.titleMedium.bold,
                    color = FoodDeliveryTheme.colors.mainColors.onBackground,
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = stringResource(R.string.msg_profile_no_profile),
                    style = FoodDeliveryTheme.typography.bodyLarge,
                    color = FoodDeliveryTheme.colors.mainColors.onBackground,
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
                iconId = R.drawable.ic_payment,
                iconDescription = R.string.description_ic_payment,
                labelStringId = R.string.action_profile_payment,
                onClick = viewModel::onPaymentClicked
            )
            NavigationIconCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                iconId = R.drawable.ic_star,
                iconDescription = R.string.description_ic_feedback,
                labelStringId = R.string.title_feedback,
                onClick = viewModel::onFeedbackClicked
            )
            NavigationIconCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                iconId = R.drawable.ic_info,
                iconDescription = R.string.description_ic_about,
                labelStringId = R.string.title_about_app,
                onClick = viewModel::onAboutAppClicked
            )
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
                onLastOrderClicked = { _, _ -> },
                onSettingsClicked = {},
                onYourAddressesClicked = {},
                onOrderHistoryClicked = {}
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
                onLastOrderClicked = { _, _ -> },
                onSettingsClicked = {},
                onYourAddressesClicked = {},
                onOrderHistoryClicked = {}
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
                onLastOrderClicked = { _, _ -> },
                onSettingsClicked = {},
                onYourAddressesClicked = {},
                onOrderHistoryClicked = {}
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
                onLastOrderClicked = { _, _ -> },
                onSettingsClicked = {},
                onYourAddressesClicked = {},
                onOrderHistoryClicked = {},
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
                onLastOrderClicked = { _, _ -> },
                onSettingsClicked = {},
                onYourAddressesClicked = {},
                onOrderHistoryClicked = {},
            )
        }
    }
}
