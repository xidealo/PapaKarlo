package com.bunbeauty.papakarlo.feature.profile

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.interactor.user.IUserInteractor
import com.bunbeauty.domain.model.profile.Profile
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.state.StateWithError
import com.bunbeauty.papakarlo.common.view_model.CartViewModel
import com.bunbeauty.papakarlo.enums.SuccessLoginDirection.BACK_TO_PROFILE
import com.bunbeauty.papakarlo.extensions.toStateWithErrorSuccess
import com.bunbeauty.papakarlo.feature.profile.ProfileFragmentDirections.*
import com.bunbeauty.papakarlo.feature.profile.order.order_list.OrderItemModel
import com.bunbeauty.papakarlo.mapper.order.IOrderUIMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userInteractor: IUserInteractor,
    private val orderUIMapper: IOrderUIMapper,
) : CartViewModel() {

    private val mutableProfileUIState: MutableStateFlow<StateWithError<ProfileUI>> =
        MutableStateFlow(StateWithError.Loading())
    val profileUIState: StateFlow<StateWithError<ProfileUI>> = mutableProfileUIState.asStateFlow()

    fun getProfile() {
        mutableProfileUIState.value = StateWithError.Loading()
        viewModelScope.launch {
            mutableProfileUIState.value = userInteractor.getProfile()?.let { profile ->
                when (profile) {
                    is Profile.Authorized -> {
                        ProfileUI(
                            userUuid = profile.userUuid,
                            hasAddresses = profile.hasAddresses,
                            lastOrderItem = profile.lastOrder?.let { lightOrder ->
                                orderUIMapper.toItem(lightOrder)
                            }
                        ).toStateWithErrorSuccess()
                    }
                    is Profile.Unauthorized -> {
                        StateWithError.Empty()
                    }
                }
            } ?: StateWithError.Error(
                baseResourcesProvider.getString(R.string.error_profile_not_loaded)
            )
        }
    }

    fun onLastOrderClicked(orderItem: OrderItemModel) {
        router.navigate(toOrderDetailsFragment(orderItem.uuid, orderItem.code))
    }

    fun onSettingsClicked() {
        router.navigate(toSettingsFragment())
    }

    fun onYourAddressesClicked() {
        router.navigate(toNavAddress(false))
    }

    fun onAddAddressClicked() {
        router.navigate(toCreateAddressFragment())
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
}