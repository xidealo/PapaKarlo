package com.bunbeauty.papakarlo.feature.profile

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.interactor.user.IUserInteractor
import com.bunbeauty.domain.model.profile.Profile
import com.bunbeauty.papakarlo.common.state.State
import com.bunbeauty.papakarlo.common.view_model.CartViewModel
import com.bunbeauty.papakarlo.enums.SuccessLoginDirection.BACK_TO_PROFILE
import com.bunbeauty.papakarlo.extensions.toSuccessOrEmpty
import com.bunbeauty.papakarlo.feature.profile.ProfileFragmentDirections.*
import com.bunbeauty.papakarlo.feature.profile.order.order_list.OrderItem
import com.bunbeauty.papakarlo.mapper.order.IOrderUIMapper
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val userInteractor: IUserInteractor,
    private val orderUIMapper: IOrderUIMapper,
) : CartViewModel() {

    private var userUuid: String? = null
    private val mutableProfileState: MutableStateFlow<State<Profile>> =
        MutableStateFlow(State.Loading())
    val profileState: StateFlow<State<Profile>> = mutableProfileState.asStateFlow()

    private val mutableLastOrder: MutableStateFlow<OrderItem?> = MutableStateFlow(null)
    val lastOrder: StateFlow<OrderItem?> = mutableLastOrder.asStateFlow()

    private val mutableHasAddresses: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val hasAddresses: StateFlow<Boolean> = mutableHasAddresses.asStateFlow()

    init {
        subscribeOnProfile()
    }

    fun onLastOrderClicked() {
        mutableLastOrder.value?.let { orderItem ->
            router.navigate(toOrderDetailsFragment(orderItem.uuid, orderItem.code))
        }
    }

    fun onSettingsClicked() {
        router.navigate(toSettingsFragment())
    }

    fun onAddressClicked() {
        if (mutableHasAddresses.value) {
            router.navigate(toNavAddress(false))
        } else {
            router.navigate(toCreateAddressFragment())
        }
    }

    fun onOrderListClicked() {
        userUuid?.let { uuid ->
            router.navigate(toOrdersFragment(uuid))
        }
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
        userInteractor.observeLightProfile().onEach { lightProfile ->
            userUuid = lightProfile?.userUuid
            mutableProfileState.value = lightProfile.toSuccessOrEmpty()
            mutableLastOrder.value = lightProfile?.lastOrder?.let { lightOrder ->
                orderUIMapper.toItem(lightOrder)
            }
            mutableHasAddresses.value = lightProfile?.hasAddresses ?: false
        }.launchIn(viewModelScope)
    }
}