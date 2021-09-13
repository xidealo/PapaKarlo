package com.bunbeauty.papakarlo.presentation.profile

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.State
import com.bunbeauty.common.extensions.toStateSuccess
import com.bunbeauty.domain.auth.IAuthUtil
import com.bunbeauty.domain.model.User
import com.bunbeauty.domain.repo.UserRepo
import com.bunbeauty.papakarlo.di.annotation.Api
import com.bunbeauty.papakarlo.presentation.base.CartViewModel
import com.bunbeauty.papakarlo.ui.profile.ProfileFragmentDirections.*
import com.bunbeauty.presentation.item.OrderItem
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    @Api private val userRepo: UserRepo,
    private val authUtil: IAuthUtil
) : CartViewModel() {

    private var user: User? = null
    private val mutableUserState: MutableStateFlow<State<User>> = MutableStateFlow(State.Loading())
    val userState: StateFlow<State<User>> = mutableUserState.asStateFlow()

    init {
        subscribeOnUser()
    }

    fun onOrderListClicked() {
        authUtil.userUuid?.let { userUuid ->
            router.navigate(toOrdersFragment(userUuid))
        }
    }

    fun onAddressClicked() {
        if (user?.addressList.isNullOrEmpty()) {
            router.navigate(toCreationAddressFragment())
        } else {
            router.navigate(toAddressesBottomSheet(true))
        }
    }

    fun goToLogin() {
        router.navigate(toLoginFragment())
    }

    fun goToOrder(orderItem: OrderItem) {
        router.navigate(toOrderDerailsFragment(orderItem.uuid, orderItem.code))
    }

    fun goToSettings() {
        user?.uuid?.let { userUuid ->
            router.navigate(toSettingsFragment(userUuid))
        }
    }

    private fun subscribeOnUser() {
        val userUuid = authUtil.userUuid
        if (userUuid == null) {
            mutableUserState.value = State.Empty()
        } else {
            userRepo.observeUserByUuid(userUuid).onEach { observedUser ->
                user = observedUser
                mutableUserState.value = observedUser?.toStateSuccess() ?: State.Empty()
            }.launchIn(viewModelScope)
        }
    }
}