package com.bunbeauty.papakarlo.feature.profile.screen.profile

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.bunbeauty.papakarlo.common.ui.element.MainButton
import com.bunbeauty.papakarlo.common.ui.element.card.NavigationIconCard
import com.bunbeauty.papakarlo.common.ui.screen.ErrorScreen
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.databinding.FragmentProfileBinding
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import com.bunbeauty.papakarlo.feature.order.ui.OrderItem
import com.bunbeauty.papakarlo.mapper.OrderItemMapper
import com.bunbeauty.shared.domain.model.date_time.Date
import com.bunbeauty.shared.domain.model.date_time.DateTime
import com.bunbeauty.shared.domain.model.date_time.Time
import com.bunbeauty.shared.domain.model.order.LightOrder
import com.bunbeauty.shared.domain.model.order.OrderStatus
import com.bunbeauty.shared.presentation.profile.ProfileState
import com.bunbeauty.shared.presentation.profile.ProfileViewModel
import com.google.android.material.transition.MaterialFadeThrough
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragmentWithSharedViewModel(R.layout.fragment_profile) {

    override val viewBinding by viewBinding(FragmentProfileBinding::bind)
    private val viewModel: ProfileViewModel by viewModel()

    private val orderItemMapper: OrderItemMapper by inject()

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
            ProfileScreen(profileState)
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
    private fun ProfileScreen(profileState: ProfileState) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(FoodDeliveryTheme.colors.background)
        ) {
            when (profileState.state) {
                ProfileState.State.AUTHORIZED -> AuthorizedProfileScreen(profileState)
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
    private fun AuthorizedProfileScreen(profile: ProfileState) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(contentPadding = PaddingValues(FoodDeliveryTheme.dimensions.mediumSpace)) {
                profile.lastOrder?.let { lastOrder ->
                    item {
                        OrderItem(orderItem = orderItemMapper.toItem(lastOrder)) {
                            viewModel.onLastOrderClicked(lastOrder)
                        }
                        Spacer(modifier = Modifier.height(FoodDeliveryTheme.dimensions.mediumSpace))
                    }
                }
                item {
                    NavigationIconCard(
                        modifier = Modifier.fillMaxWidth(),
                        iconId = R.drawable.ic_settings,
                        iconDescription = R.string.description_ic_settings,
                        labelStringId = R.string.action_profile_settings
                    ) {
                        viewModel.onSettingsClicked()
                    }
                }
                item {
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
                }
                item {
                    NavigationIconCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                        iconId = R.drawable.ic_history,
                        iconDescription = R.string.description_ic_my_orders,
                        labelStringId = R.string.action_profile_my_orders
                    ) {
                        viewModel.onOrderHistoryClicked()
                    }
                }
                item {
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
                }
                item {
                    ProfileInfoCards(
                        modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.smallSpace)
                    )
                }
            }
        }
    }

    @Composable
    private fun UnauthorizedProfileScreen() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(FoodDeliveryTheme.dimensions.mediumSpace),
        ) {
            ProfileInfoCards()
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = FoodDeliveryTheme.dimensions.mediumSpace)
            ) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BoxWithConstraints {
                        if (maxHeight > 280.dp) {
                            Image(
                                painter = painterResource(R.drawable.empty_profile),
                                contentDescription = stringResource(R.string.description_empty_profile)
                            )
                        }
                    }
                    Text(
                        modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.mediumSpace),
                        text = stringResource(R.string.msg_profile_no_profile),
                        style = FoodDeliveryTheme.typography.body1,
                        textAlign = TextAlign.Center
                    )
                }
            }
            MainButton(
                modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.mediumSpace),
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
    private fun AuthorizedProfileScreenWithLastOrderPreview() {
        ProfileScreen(
            ProfileState(
                lastOrder = LightOrder(
                    uuid = "",
                    status = OrderStatus.NOT_ACCEPTED,
                    code = "А-12",
                    dateTime = DateTime(
                        Date(
                            dayOfMonth = 1,
                            monthNumber = 0,
                            year = 2020
                        ),
                        Time(
                            hours = 10,
                            minutes = 30,
                        )
                    ),
                ),
                state = ProfileState.State.AUTHORIZED
            )
        )
    }

    @Preview
    @Composable
    private fun AuthorizedProfileScreenWithoutLastOrderPreview() {
        ProfileScreen(
            ProfileState(
                lastOrder = null,
                state = ProfileState.State.AUTHORIZED
            )
        )
    }

    @Preview
    @Composable
    private fun UnauthorizedProfileScreenPreview() {
        ProfileScreen(
            ProfileState(
                lastOrder = null,
                state = ProfileState.State.UNAUTHORIZED
            )
        )
    }

    @Preview
    @Composable
    private fun LoadingProfileScreenPreview() {
        ProfileScreen(
            ProfileState(
                lastOrder = null,
                state = ProfileState.State.LOADING
            )
        )
    }

    @Preview
    @Composable
    private fun ErrorProfileScreenPreview() {
        ProfileScreen(
            ProfileState(
                lastOrder = null,
                state = ProfileState.State.ERROR
            )
        )
    }
}
