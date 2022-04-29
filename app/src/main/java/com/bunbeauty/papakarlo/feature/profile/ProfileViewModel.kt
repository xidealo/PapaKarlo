package com.bunbeauty.papakarlo.feature.profile

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.interactor.user.IUserInteractor
import com.bunbeauty.domain.model.profile.Profile
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.state.State
import com.bunbeauty.papakarlo.common.view_model.CartViewModel
import com.bunbeauty.papakarlo.enums.SuccessLoginDirection.BACK_TO_PROFILE
import com.bunbeauty.papakarlo.feature.profile.ProfileFragmentDirections.*
import com.bunbeauty.papakarlo.feature.profile.order.order_list.OrderItem
import com.bunbeauty.papakarlo.mapper.order.IOrderUIMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userInteractor: IUserInteractor,
    private val orderUIMapper: IOrderUIMapper,
) : CartViewModel() {

    private val mutableProfileUIState: MutableStateFlow<State<ProfileUI>> =
        MutableStateFlow(State.Loading())
    val profileUIState: StateFlow<State<ProfileUI>> = mutableProfileUIState.asStateFlow()

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