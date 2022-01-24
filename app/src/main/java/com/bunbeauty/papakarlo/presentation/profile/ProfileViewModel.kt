package com.bunbeauty.papakarlo.presentation.profile

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.interactor.user.IUserInteractor
import com.bunbeauty.domain.model.profile.LightProfile
import com.bunbeauty.papakarlo.presentation.base.CartViewModel
import com.bunbeauty.papakarlo.presentation.state.State
import com.bunbeauty.papakarlo.presentation.state.toSuccessOrEmpty
import com.bunbeauty.papakarlo.ui.fragment.profile.ProfileFragmentDirections.*
import com.bunbeauty.presentation.enums.SuccessLoginDirection.BACK_TO_PROFILE
import com.bunbeauty.presentation.item.OrderItem
import com.bunbeauty.presentation.mapper.order.IOrderUIMapper
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val userInteractor: IUserInteractor,
    private val orderUIMapper: IOrderUIMapper,
) : CartViewModel() {

    private var userUuid: String? = null
    private val mutableProfileState: MutableStateFlow<State<LightProfile>> =
        MutableStateFlow(State.Loading())
    val profileState: StateFlow<State<LightProfile>> = mutableProfileState.asStateFlow()

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