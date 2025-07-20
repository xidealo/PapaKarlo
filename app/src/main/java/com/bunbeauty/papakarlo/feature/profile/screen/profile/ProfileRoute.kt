package com.bunbeauty.papakarlo.feature.profile.screen.profile

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.element.FoodDeliveryHorizontalDivider
import com.bunbeauty.papakarlo.common.ui.element.FoodDeliveryScaffold
import com.bunbeauty.papakarlo.common.ui.element.button.MainButton
import com.bunbeauty.papakarlo.common.ui.element.card.FoodDeliveryCard
import com.bunbeauty.papakarlo.common.ui.element.card.NavigationIconCardWithDivider
import com.bunbeauty.papakarlo.common.ui.screen.ErrorScreen
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.bold
import com.bunbeauty.papakarlo.feature.order.ui.OrderStatusChip
import com.bunbeauty.papakarlo.feature.order.ui.getOrderStatusName
import com.bunbeauty.papakarlo.util.string.IStringUtil
import com.bunbeauty.papakarlo.util.string.getDateTimeString
import com.bunbeauty.shared.domain.model.SuccessLoginDirection
import com.bunbeauty.shared.domain.model.date_time.Date
import com.bunbeauty.shared.domain.model.date_time.DateTime
import com.bunbeauty.shared.domain.model.date_time.Time
import com.bunbeauty.shared.domain.model.order.LightOrder
import com.bunbeauty.shared.domain.model.order.OrderStatus
import com.bunbeauty.shared.presentation.profile.ProfileState
import com.bunbeauty.shared.presentation.profile.ProfileViewModel
import kotlinx.collections.immutable.persistentListOf
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun ProfileState.DataState.mapState(): ProfileViewState {
    return ProfileViewState(
        lastOrder = lastOrder,
        state = when (state) {
            ProfileState.DataState.State.AUTHORIZED -> ProfileViewState.State.Authorized
            ProfileState.DataState.State.UNAUTHORIZED -> ProfileViewState.State.Unauthorized
            ProfileState.DataState.State.ERROR -> ProfileViewState.State.Error
            ProfileState.DataState.State.LOADING -> ProfileViewState.State.Loading
        },
        paymentMethodList = paymentMethodList,
        linkList = linkList
    )
}

@Composable
fun ProfileRoute(
    viewModel: ProfileViewModel = koinViewModel(),
    back: () -> Unit,
    goToUserAddress: (Boolean) -> Unit,
    goToLogin: (SuccessLoginDirection) -> Unit,
    goToOrderDetailsFragment: (String) -> Unit,
    goToOrdersFragment: () -> Unit,
    goToSettingsFragment: () -> Unit,
    goToAboutAppBottomSheet: () -> Unit,
    goToCafeListFragment: () -> Unit,
) {

    val linkUiStateMapper = koinInject<LinkUiStateMapper>()
    val paymentMethodUiStateMapper = koinInject<PaymentMethodUiStateMapper>()
    val stringUtil = koinInject<IStringUtil>()

    LaunchedEffect(Unit) {
        viewModel.onAction(ProfileState.Action.Init)
    }

    val viewState by viewModel.dataState.collectAsStateWithLifecycle()

    val onAction = remember {
        { event: ProfileState.Action ->
            viewModel.onAction(event)
        }
    }

    val effects by viewModel.events.collectAsStateWithLifecycle()
    val consumeEffects = remember {
        {
            viewModel.consumeEvents(effects)
        }
    }

    ProfileEffect(
        effects = effects,
        back = back,
        goToUserAddress = goToUserAddress,
        goToLogin = goToLogin,
        goToOrderDetailsFragment = goToOrderDetailsFragment,
        goToOrdersFragment = goToOrdersFragment,
        goToSettingsFragment = goToSettingsFragment,
        goToAboutAppBottomSheet = goToAboutAppBottomSheet,
        goToCafeListFragment = goToCafeListFragment,
        consumeEffects = consumeEffects
    )
    ProfileScreen(
        viewState = viewState.mapState(),
        onAction = onAction,
    )
}

@Composable
private fun ProfileScreen(
    viewState: ProfileViewState,
    onAction: (ProfileState.Action) -> Unit,
) {
    FoodDeliveryScaffold(
        title = stringResource(R.string.title_profile),
        backActionClick = {
            onAction(ProfileState.Action.BackClicked)
        },
        backgroundColor = FoodDeliveryTheme.colors.mainColors.surface,
        actionButton = {
            if (viewState.state == ProfileViewState.State.Unauthorized) {
                LoginButton(onAction)
            }
        }

    ) {
        when (viewState.state) {
            ProfileViewState.State.Error -> ErrorScreen(
                mainTextId = R.string.error_cafe_list_loading,
                onClick = {
                    onAction(ProfileState.Action.OnRefreshClicked)
                }
            )

            ProfileViewState.State.Loading -> LoadingScreen()

            ProfileViewState.State.Authorized -> AuthorizedProfileScreen(
                state = viewState,
                onAction = onAction,
            )

            ProfileViewState.State.Unauthorized -> UnauthorizedProfileScreen(
                onAction = onAction,
                state = viewState
            )
        }
    }
}

@Composable
fun ProfileEffect(
    effects: List<ProfileState.Event>,
    back: () -> Unit,
    goToUserAddress: (Boolean) -> Unit,
    goToLogin: (SuccessLoginDirection) -> Unit,
    goToOrderDetailsFragment: (String) -> Unit,
    goToOrdersFragment: () -> Unit,
    goToSettingsFragment: () -> Unit,
    goToAboutAppBottomSheet: () -> Unit,
    goToCafeListFragment: () -> Unit,
    consumeEffects: () -> Unit,
) {
    LaunchedEffect(effects) {
        effects.forEach { effect ->
            when (effect) {
                ProfileState.Event.OpenAddressList -> {
                    goToUserAddress(false)
                }

                ProfileState.Event.OpenLogin -> {
                    goToLogin(SuccessLoginDirection.BACK_TO_PROFILE)
                }

                is ProfileState.Event.OpenOrderDetails -> {
                    goToOrderDetailsFragment(effect.orderUuid)
                }

                ProfileState.Event.OpenOrderList -> {
                    goToOrdersFragment()
                }

                ProfileState.Event.OpenSettings -> {
                    goToSettingsFragment()
                }

                ProfileState.Event.ShowAboutApp -> {
                    goToAboutAppBottomSheet()
                }

                ProfileState.Event.ShowCafeList -> {
                    goToCafeListFragment()
                }

                is ProfileState.Event.ShowFeedback -> {
                    //TODO BOTTOM SHEET
//                    FeedbackBottomSheet.show(
//                        fragmentManager = parentFragmentManager,
//                        feedbackArgument = FeedbackArgument(
//                            linkList = linkUiStateMapper.map(event.linkList)
//                        )
//                    )
                }

                is ProfileState.Event.ShowPayment -> {
                    //TODO BOTTOM SHEET

//                    PaymentBottomSheet.show(
//                        fragmentManager = parentFragmentManager,
//                        paymentMethodsArgument = PaymentMethodsArgument(
//                            paymentMethodList = paymentMethodUiStateMapper.map(
//                                event.paymentMethodList
//                            )
//                        )
//                    )
                }

                ProfileState.Event.GoBackEvent -> back()
            }
        }
        consumeEffects()
    }
}

@Composable
private fun LoginButton(
    onAction: (ProfileState.Action) -> Unit,
) {
    MainButton(
        modifier = Modifier
            .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace),
        textStringId = R.string.action_profile_login,
        onClick = { onAction(ProfileState.Action.onLoginClicked) }
    )
}

@Composable
private fun AuthorizedProfileScreen(
    state: ProfileViewState,
    onAction: (ProfileState.Action) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        state.lastOrder?.let { lastOrder ->
            OrderProfile(
                onClick = {
                    onAction(
                        ProfileState.Action.onLastOrderClicked(
                            uuid = state.lastOrder.uuid,
                            code = state.lastOrder.code
                        )
                    )
                },
                modifier = Modifier.padding(bottom = 16.dp),
                state = state,
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
            onClick = { onAction(ProfileState.Action.onSettingsClick) }
        )
        NavigationIconCardWithDivider(
            modifier = Modifier
                .fillMaxWidth(),
            iconId = R.drawable.ic_address,
            iconDescriptionStringId = R.string.description_ic_my_addresses,
            labelStringId = R.string.action_profile_my_addresses,
            onClick = { onAction(ProfileState.Action.onYourAddressesClicked) }
        )
        NavigationIconCardWithDivider(
            modifier = Modifier
                .fillMaxWidth(),
            iconId = R.drawable.ic_history,
            iconDescriptionStringId = R.string.description_ic_my_orders,
            labelStringId = R.string.action_profile_my_orders,
            onClick = { onAction(ProfileState.Action.onOrderHistoryClicked) }
        )
        ProfileInfoCards(
            modifier = Modifier,
            onAction = onAction,
            state = state
        )
    }
}

@Composable
private fun UnauthorizedProfileScreen(
    onAction: (ProfileState.Action) -> Unit,
    state: ProfileViewState,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        ProfileInfoCards(
            modifier = Modifier,
            onAction = onAction,
            state = state
        )
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
private fun ProfileInfoCards(
    state: ProfileViewState,
    modifier: Modifier = Modifier,
    onAction: (ProfileState.Action) -> Unit,
) {
    Column(modifier = modifier) {
        NavigationIconCardWithDivider(
            modifier = Modifier.fillMaxWidth(),
            iconId = R.drawable.ic_cafes,
            iconDescriptionStringId = R.string.title_bottom_navigation_menu_cafe_list,
            labelStringId = R.string.title_bottom_navigation_menu_cafe_list,
            onClick = { onAction(ProfileState.Action.onCafeListClicked) }
        )
        NavigationIconCardWithDivider(
            modifier = Modifier.fillMaxWidth(),
            iconId = R.drawable.ic_payment,
            iconDescriptionStringId = R.string.description_ic_payment,
            labelStringId = R.string.action_profile_payment,
            onClick = {
                onAction(
                    ProfileState.Action.onPaymentClicked(
                        paymentMethodList = state.paymentMethodList
                    )
                )
            }
        )
        NavigationIconCardWithDivider(
            modifier = Modifier
                .fillMaxWidth(),
            iconId = R.drawable.ic_star,
            iconDescriptionStringId = R.string.description_ic_feedback,
            labelStringId = R.string.title_feedback,
            onClick = {
                onAction(
                    ProfileState.Action.onFeedbackClicked(
                        linkList = state.linkList
                    )
                )
            }
        )
        NavigationIconCardWithDivider(
            modifier = Modifier
                .fillMaxWidth(),
            iconId = R.drawable.ic_info,
            iconDescriptionStringId = R.string.description_ic_about,
            labelStringId = R.string.title_about_app,
            onClick = { onAction(ProfileState.Action.onAboutAppClicked) }
        )
    }
}

@Composable
private fun OrderProfile(
    modifier: Modifier = Modifier,
    state: ProfileViewState,
    onClick: () -> Unit,
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
                text = state.lastOrder?.code.orEmpty(),
                modifier = Modifier
                    .requiredWidthIn(min = FoodDeliveryTheme.dimensions.codeWidth)
                    .padding(end = FoodDeliveryTheme.dimensions.smallSpace),
                style = FoodDeliveryTheme.typography.titleMedium.bold,
                color = FoodDeliveryTheme.colors.mainColors.onSurface
            )

            OrderStatusChip(
                orderStatus = state.lastOrder?.status ?: OrderStatus.ACCEPTED,
                statusName = (state.lastOrder?.status ?: OrderStatus.ACCEPTED).getOrderStatusName()
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.End),
                text = state.lastOrder?.dateTime?.getDateTimeString() ?: "—",
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
            viewState = ProfileViewState(
                state = ProfileViewState.State.Authorized,
                lastOrder = LightOrder(
                    uuid = "uuid",
                    status = OrderStatus.DONE,
                    code = "code",
                    dateTime = DateTime(
                        date = Date(
                            dayOfMonth = 5474,
                            monthNumber = 7337,
                            year = 1992
                        ),
                        time = Time(hours = 3796, minutes = 8009)
                    )
                ),
                paymentMethodList = persistentListOf(),
                linkList = listOf()
            ),
            onAction = {}
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun AuthorizedProfileScreenWithoutLastOrderPreview() {
    FoodDeliveryTheme {
        ProfileScreen(
            viewState = ProfileViewState(
                state = ProfileViewState.State.Authorized,
                lastOrder = null,
                paymentMethodList = persistentListOf(),
                linkList = listOf()
            ),
            onAction = {}
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun UnauthorizedProfileScreenPreview() {
    FoodDeliveryTheme {
        ProfileScreen(
            viewState = ProfileViewState(
                state = ProfileViewState.State.Unauthorized,
                lastOrder = null,
                paymentMethodList = persistentListOf(),
                linkList = listOf()
            ),
            onAction = {}
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun LoadingProfileScreenPreview() {
    FoodDeliveryTheme {
        ProfileScreen(
            viewState = ProfileViewState(
                state = ProfileViewState.State.Loading,
                lastOrder = null,
                paymentMethodList = persistentListOf(),
                linkList = listOf()
            ),
            onAction = {}
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ErrorProfileScreenPreview() {
    FoodDeliveryTheme {
        ProfileScreen(
            viewState = ProfileViewState(
                state = ProfileViewState.State.Error,
                lastOrder = null,
                paymentMethodList = persistentListOf(),
                linkList = listOf()
            ),
            onAction = {}
        )
    }
}
