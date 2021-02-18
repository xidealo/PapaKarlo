package com.bunbeauty.papakarlo.presentation.profile

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.auth.IAuthUtil
import com.bunbeauty.domain.interactor.user.IUserInteractor
import com.bunbeauty.domain.model.profile.Profile
import com.bunbeauty.domain.repo.Api
import com.bunbeauty.domain.repo.UserRepo
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
    @Api private val userRepo: UserRepo,
    private val authUtil: IAuthUtil,
    private val orderUIMapper: IOrderUIMapper,
    private val userInteractor: IUserInteractor
) : CartViewModel() {

    private var profileUuid: String? = null
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
            router.navigate(toOrderDerailsFragment(orderItem.uuid, orderItem.code))
        }
    }

    fun onSettingsClicked() {
        router.navigate(toSettingsFragment())
    }

    fun onAddressClicked() {
        if (mutableHasAddresses.value) {
            router.navigate(toUserAddressesBottomSheet(false))
        } else {
            router.navigate(toCreationAddressFragment())
        }
    }

    fun onOrderListClicked() {
        authUtil.userUuid?.let { userUuid ->
            router.navigate(toOrdersFragment(userUuid))
        }
    }

    fun onPaymentClicked() {
        router.navigate(toPaymentBottomSheet())
    }

    fun onFeedbackClicked() {
        router.navigate(toFeedbackBottomSheet())
    }

    fun onLoginClicked() {
        router.navigate(toLoginFragment(BACK_TO_PROFILE))
    }

    private fun subscribeOnProfile() {
        userInteractor.observeProfile().onEach { profile ->
            profileUuid = profile?.user?.uuid
            mutableProfileState.value = profile.toSuccessOrEmpty()

            mutableLastOrder.value = profile?.orderList?.maxByOrNull { order ->
                order.time
            }?.let(orderUIMapper::toItem)

            mutableHasAddresses.value = !profile?.addressList.isNullOrEmpty()
        }.launchIn(viewModelScope)
    }
}