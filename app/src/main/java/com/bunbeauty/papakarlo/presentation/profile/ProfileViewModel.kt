package com.bunbeauty.papakarlo.presentation.profile

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.State
import com.bunbeauty.common.extensions.toSuccessOrEmpty
import com.bunbeauty.domain.auth.IAuthUtil
import com.bunbeauty.domain.model.Profile
import com.bunbeauty.domain.repo.UserRepo
import com.bunbeauty.papakarlo.di.annotation.Api
import com.bunbeauty.papakarlo.presentation.base.CartViewModel
import com.bunbeauty.papakarlo.ui.fragment.profile.ProfileFragmentDirections.*
import com.bunbeauty.presentation.item.OrderItem
import com.bunbeauty.presentation.mapper.order.IOrderUIMapper
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    @Api private val userRepo: UserRepo,
    private val authUtil: IAuthUtil,
    private val orderUIMapper: IOrderUIMapper,
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
        router.navigate(toLoginFragment())
    }

    private fun subscribeOnProfile() {
        authUtil.observeUserUuid().flatMapLatest { userUuid ->
            userRepo.observeUserByUuid(userUuid ?: "").onEach { observedUser ->
                profileUuid = observedUser?.uuid
                mutableProfileState.value = observedUser.toSuccessOrEmpty()

                mutableLastOrder.value = observedUser?.orderList?.maxByOrNull { order ->
                    order.time
                }?.let(orderUIMapper::toItem)

                mutableHasAddresses.value = !observedUser?.addressList.isNullOrEmpty()
            }
        }.launchIn(viewModelScope)
    }
}