package com.bunbeauty.papakarlo.feature.profile.screen.profile

import android.os.Bundle
import android.view.View
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseComposeFragment
import com.bunbeauty.papakarlo.common.extension.navigateSafe
import com.bunbeauty.papakarlo.common.ui.element.FoodDeliveryHorizontalDivider
import com.bunbeauty.papakarlo.common.ui.element.FoodDeliveryScaffold
import com.bunbeauty.papakarlo.common.ui.element.button.MainButton
import com.bunbeauty.papakarlo.common.ui.element.card.FoodDeliveryCard
import com.bunbeauty.papakarlo.common.ui.element.card.NavigationIconCardWithDivider
import com.bunbeauty.papakarlo.common.ui.screen.ErrorScreen
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.bold
import com.bunbeauty.papakarlo.databinding.LayoutComposeBinding
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import com.bunbeauty.papakarlo.feature.cafe.screen.cafelist.CafeListViewState
import com.bunbeauty.papakarlo.feature.order.model.OrderItem
import com.bunbeauty.papakarlo.feature.order.ui.OrderStatusChip
import com.bunbeauty.papakarlo.feature.profile.screen.feedback.FeedbackBottomSheet
import com.bunbeauty.papakarlo.feature.profile.screen.feedback.model.FeedbackArgument
import com.bunbeauty.papakarlo.feature.profile.screen.payment.PaymentBottomSheet
import com.bunbeauty.papakarlo.feature.profile.screen.payment.PaymentMethodsArgument
import com.bunbeauty.shared.domain.model.SuccessLoginDirection
import com.bunbeauty.shared.domain.model.order.OrderStatus
import com.bunbeauty.shared.presentation.cafe_list.CafeList
import com.bunbeauty.shared.presentation.profile.ProfileState
import com.bunbeauty.shared.presentation.profile.ProfileViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment :
    BaseComposeFragment<ProfileState.DataState, ProfileViewState, ProfileState.Action, ProfileState.Event>() {

    override val viewModel: ProfileViewModel by viewModel()




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onAction(ProfileState.Action.Init)
    }


    @Composable
    override fun ProfileState.DataState.mapState(): ProfileViewState {
        TODO("Not yet implemented")
    }

    @Composable
    override fun Screen(
        viewState: ProfileViewState,
        onAction: (ProfileState.Action) -> Unit
    ) {
        ProfileScreen(
            profileState = viewState,
            onAction = onAction
        )
    }


    @Composable
    private fun ProfileScreen(
        profileState: ProfileViewState,
        onAction: (ProfileState.Action) -> Unit
    ) {
        FoodDeliveryScaffold(
            title = stringResource(R.string.title_profile),
            backActionClick = {
                findNavController().popBackStack()
            },
            backgroundColor = FoodDeliveryTheme.colors.mainColors.surface,
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
            Crossfade(targetState = profileUi.state, label = "ProfileScreen") { state ->
                when (state) {
                    ProfileState.State.AUTHORIZED -> AuthorizedProfileScreen(
                        profileUi,
                        onLastOrderClicked = onLastOrderClicked,
                        onSettingsClick = onSettingsClicked,
                        onYourAddressesClicked = onYourAddressesClicked,
                        onOrderHistoryClicked = onOrderHistoryClicked
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
    }

    override fun handleEvent(event: ProfileState.Event) {
        when (event) {
            ProfileState.Event.OpenAddressList -> {
                findNavController().navigateSafe(
                    ProfileFragmentDirections.toNavAddress(
                        false
                    )
                )
            }

            ProfileState.Event.OpenLogin -> {
                findNavController().navigateSafe(
                    ProfileFragmentDirections.toLoginFragment(
                        SuccessLoginDirection.BACK_TO_PROFILE
                    )
                )
            }

            is ProfileState.Event.OpenOrderDetails -> {
                findNavController().navigateSafe(
                    ProfileFragmentDirections.toOrderDetailsFragment(
                        event.orderUuid,
                        event.orderCode
                    )
                )
            }

            ProfileState.Event.OpenOrderList -> {
                findNavController().navigateSafe(ProfileFragmentDirections.toOrdersFragment())
            }

            ProfileState.Event.OpenSettings -> {
                findNavController().navigateSafe(ProfileFragmentDirections.toSettingsFragment())
            }

            ProfileState.Event.ShowAboutApp -> {
                findNavController().navigateSafe(ProfileFragmentDirections.toAboutAppBottomSheet())
            }

            ProfileState.Event.ShowCafeList -> {
                findNavController().navigateSafe(
                    ProfileFragmentDirections.actionProfileFragmentToCafeListFragment()
                )
            }

            is ProfileState.Event.ShowFeedback -> {
                FeedbackBottomSheet.show(
                    fragmentManager = parentFragmentManager,
                    feedbackArgument = FeedbackArgument(
                        linkList = linkUiStateMapper.map(event.linkList)
                    )
                )
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
        }
    }


    @Composable
    private fun AuthorizedProfileScreen(
        profile: ProfileUi,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            profile.orderItem?.let { orderItem ->
                OrderProfile(
                    orderItem = orderItem,
                    onClick = {
                        onLastOrderClicked(orderItem.uuid, orderItem.code)
                    },
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                FoodDeliveryHorizontalDivider(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                )
            }
            NavigationIconCardWithDivider(
                modifier = Modifier.fillMaxWidth(),
                iconId = R.drawable.ic_settings,
                iconDescriptionStringId = R.string.description_ic_settings,
                labelStringId = R.string.action_profile_settings,
                onClick = onSettingsClick
            )
            NavigationIconCardWithDivider(
                modifier = Modifier
                    .fillMaxWidth(),
                iconId = R.drawable.ic_address,
                iconDescriptionStringId = R.string.description_ic_my_addresses,
                labelStringId = R.string.action_profile_my_addresses,
                onClick = onYourAddressesClicked
            )
            NavigationIconCardWithDivider(
                modifier = Modifier
                    .fillMaxWidth(),
                iconId = R.drawable.ic_history,
                iconDescriptionStringId = R.string.description_ic_my_orders,
                labelStringId = R.string.action_profile_my_orders,
                onClick = onOrderHistoryClicked
            )
            ProfileInfoCards()
        }
    }

    @Composable
    private fun UnauthorizedProfileScreen() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
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
                        .background(
                            FoodDeliveryTheme.colors.mainColors.primary.copy(
                                alpha = 0.6f
                            )
                        ),
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
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .padding(horizontal = 16.dp),
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
            NavigationIconCardWithDivider(
                modifier = Modifier.fillMaxWidth(),
                iconId = R.drawable.ic_cafes,
                iconDescriptionStringId = R.string.title_bottom_navigation_menu_cafe_list,
                labelStringId = R.string.title_bottom_navigation_menu_cafe_list,
                onClick = viewModel::onCafeListClicked
            )
            NavigationIconCardWithDivider(
                modifier = Modifier.fillMaxWidth(),
                iconId = R.drawable.ic_payment,
                iconDescriptionStringId = R.string.description_ic_payment,
                labelStringId = R.string.action_profile_payment,
                onClick = viewModel::onPaymentClicked
            )
            NavigationIconCardWithDivider(
                modifier = Modifier
                    .fillMaxWidth(),
                iconId = R.drawable.ic_star,
                iconDescriptionStringId = R.string.description_ic_feedback,
                labelStringId = R.string.title_feedback,
                onClick = viewModel::onFeedbackClicked
            )
            NavigationIconCardWithDivider(
                modifier = Modifier
                    .fillMaxWidth(),
                iconId = R.drawable.ic_info,
                iconDescriptionStringId = R.string.description_ic_about,
                labelStringId = R.string.title_about_app,
                onClick = viewModel::onAboutAppClicked
            )
        }
    }

    @Composable
    private fun OrderProfile(
        modifier: Modifier = Modifier,
        orderItem: OrderItem,
        onClick: () -> Unit
    ) {
        FoodDeliveryCard(
            modifier = modifier,
            onClick = onClick,
            elevated = false,
            shape = RoundedCornerShape(0.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = orderItem.code,
                    modifier = Modifier
                        .requiredWidthIn(min = FoodDeliveryTheme.dimensions.codeWidth)
                        .padding(end = FoodDeliveryTheme.dimensions.smallSpace),
                    style = FoodDeliveryTheme.typography.titleMedium.bold,
                    color = FoodDeliveryTheme.colors.mainColors.onSurface
                )
                OrderStatusChip(
                    orderStatus = orderItem.status,
                    statusName = orderItem.statusName
                )
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentWidth(Alignment.End),
                    text = orderItem.dateTime,
                    style = FoodDeliveryTheme.typography.bodySmall,
                    color = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                    textAlign = TextAlign.End
                )
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
                        dateTime = "10-10-10 20:20"
                    ),
                    state = ProfileState.State.AUTHORIZED
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
                    state = ProfileState.State.AUTHORIZED
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
                    state = ProfileState.State.UNAUTHORIZED
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
                    state = ProfileState.State.LOADING
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
    private fun ErrorProfileScreenPreview() {
        FoodDeliveryTheme {
            ProfileScreen(
                ProfileUi(
                    orderItem = null,
                    state = ProfileState.State.ERROR
                ),
                onLastOrderClicked = { _, _ -> },
                onSettingsClicked = {},
                onYourAddressesClicked = {},
                onOrderHistoryClicked = {}
            )
        }
    }
}
