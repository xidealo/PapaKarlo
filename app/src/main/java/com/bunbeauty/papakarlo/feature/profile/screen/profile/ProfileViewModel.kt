package com.bunbeauty.papakarlo.feature.profile.screen.profile

import androidx.lifecycle.viewModelScope
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.model.SuccessLoginDirection.BACK_TO_PROFILE
import com.bunbeauty.papakarlo.common.state.State
import com.bunbeauty.papakarlo.common.view_model.CartViewModel
import com.bunbeauty.papakarlo.feature.order.model.OrderItem
import com.bunbeauty.papakarlo.feature.profile.model.ProfileUI
import com.bunbeauty.papakarlo.feature.profile.screen.profile.ProfileFragmentDirections.*
import com.bunbeauty.papakarlo.mapper.order.IOrderUIMapper
import com.bunbeauty.shared.domain.interactor.order.IOrderInteractor
import com.bunbeauty.shared.domain.interactor.user.IUserInteractor
import com.bunbeauty.shared.domain.model.profile.Profile
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userInteractor: IUserInteractor,
    private val orderInteractor: IOrderInteractor,
    private val orderUIMapper: IOrderUIMapper,
) : CartViewModel() {

    private val mutableProfileUIState: MutableStateFlow<State<ProfileUI>> =
        MutableStateFlow(State.Loading())
    val profileUIState: StateFlow<State<ProfileUI>> = mutableProfileUIState.asStateFlow()

    init {
        observeLastOrder()
    }

    fun getProfile() {
        mutableProfileUIState.value = State.Loading()
        viewModelScope.launch {
            mutableProfileUIState.value = userInteractor.getProfile().toState()
        }
    }

    fun onLastOrderClicked(orderItem: OrderItem) {
        router.navigate(toOrderDetailsFragment(orderItem.uuid, orderItem.code))
    }

    fun onSettingsClicked() {
        router.navigate(toSettingsFragment())
    }

    fun onYourAddressesClicked() {
        router.navigate(toNavAddress(false))
    }

    fun onOrderHistoryClicked(userUuid: String) {
        router.navigate(toOrdersFragment(userUuid))
    }

    fun onPaymentClicked() {
        router.navigate(toPaymentBottomSheet())
    }

    fun onFeedbackClicked() {
        router.navigate(toFeedbackBottomSheet())
    }

    fun onAboutAppClicked() {
        router.navigate(toAboutAppBottomSheet())
    }

    fun onLoginClicked() {
        router.navigate(toLoginFragment(BACK_TO_PROFILE))
    }

    private fun observeLastOrder() {
        userInteractor.observeIsUserAuthorize().flatMapLatest { isUserAuthorize ->
            if (isUserAuthorize) {
                orderInteractor.observeLastOrder()
            } else {
                flow {
                    emit(null)
                }
            }
        }.onEach { lightOrder ->
            mutableProfileUIState.update { state ->
                if (state is State.Success) {
                    state.copy(
                        data = state.data.copy(
                            lastOrderItem = lightOrder?.let {
                                orderUIMapper.toItem(lightOrder)
                            }
                        )
                    )
                } else {
                    state
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun Profile?.toState(): State<ProfileUI> {
        return if (this == null) {
            State.Error(resourcesProvider.getString(R.string.error_profile_loading))
        } else {
            when (this) {
                is Profile.Authorized -> {
                    ProfileUI(
                        userUuid = userUuid,
                        hasAddresses = hasAddresses,
                        lastOrderItem = lastOrder?.let { lightOrder ->
                            orderUIMapper.toItem(lightOrder)
                        }
                    ).toState()
                }
                is Profile.Unauthorized -> {
                    State.Empty()
                }
            }
        }
    }
}