package com.bunbeauty.papakarlo.feature.profile

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.interactor.user.IUserInteractor
import com.bunbeauty.papakarlo.common.state.State
import com.bunbeauty.papakarlo.common.view_model.CartViewModel
import com.bunbeauty.papakarlo.enums.SuccessLoginDirection.BACK_TO_PROFILE
import com.bunbeauty.papakarlo.extensions.toSuccessOrEmpty
import com.bunbeauty.papakarlo.feature.profile.ProfileFragmentDirections.*
import com.bunbeauty.papakarlo.feature.profile.order.order_list.OrderItem
import com.bunbeauty.papakarlo.mapper.order.IOrderUIMapper
import kotlinx.coroutines.flow.*

class ProfileViewModel  constructor(
    private val userInteractor: IUserInteractor,
    private val orderUIMapper: IOrderUIMapper,
) : CartViewModel() {

    private val mutableProfileUIState: MutableStateFlow<State<ProfileUI>> =
        MutableStateFlow(State.Loading())
    val profileUIState: StateFlow<State<ProfileUI>> = mutableProfileUIState.asStateFlow()

    init {
        subscribeOnProfile()
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

    private fun subscribeOnProfile() {
        userInteractor.observeLightProfile().onEach { nullableProfile ->
            mutableProfileUIState.value = nullableProfile?.let { profile ->
                ProfileUI(
                    userUuid = profile.userUuid,
                    hasAddresses = profile.hasAddresses,
                    lastOrderItem = profile.lastOrder?.let { lightOrder ->
                        orderUIMapper.toItem(lightOrder)
                    }
                )
            }.toSuccessOrEmpty()
        }.launchIn(viewModelScope)
    }
}