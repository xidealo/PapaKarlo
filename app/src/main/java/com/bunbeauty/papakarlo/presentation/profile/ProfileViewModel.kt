package com.bunbeauty.papakarlo.presentation.profile

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.State
import com.bunbeauty.common.extensions.toStateSuccess
import com.bunbeauty.domain.auth.IAuthUtil
import com.bunbeauty.domain.model.Profile
import com.bunbeauty.domain.repo.UserRepo
import com.bunbeauty.papakarlo.di.annotation.Api
import com.bunbeauty.papakarlo.presentation.base.CartViewModel
import com.bunbeauty.papakarlo.ui.fragment.profile.ProfileFragmentDirections.*
import com.bunbeauty.presentation.item.OrderItem
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    @Api private val userRepo: UserRepo,
    private val authUtil: IAuthUtil
) : CartViewModel() {

    private var profile: Profile? = null
    private val mutableProfileState: MutableStateFlow<State<Profile>> =
        MutableStateFlow(State.Loading())
    val profileState: StateFlow<State<Profile>> = mutableProfileState.asStateFlow()

    init {
        subscribeOnUser()
    }

    fun onLastOrderClicked(orderItem: OrderItem) {
        router.navigate(toOrderDerailsFragment(orderItem.uuid, orderItem.code))
    }

    fun onSettingsClicked() {
        profile?.uuid?.let { userUuid ->
            router.navigate(toSettingsFragment(userUuid))
        }
    }

    fun onAddressClicked() {
        if (profile?.addressList.isNullOrEmpty()) {
            router.navigate(toCreationAddressFragment())
        } else {
            router.navigate(toAddressesBottomSheet(true))
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

    private fun subscribeOnUser() {
        val userUuid = authUtil.userUuid
        if (userUuid == null) {
            mutableProfileState.value = State.Empty()
        } else {
            userRepo.observeUserByUuid(userUuid).onEach { observedUser ->
                profile = observedUser
                mutableProfileState.value = observedUser?.toStateSuccess() ?: State.Empty()
            }.launchIn(viewModelScope)
        }
    }
}