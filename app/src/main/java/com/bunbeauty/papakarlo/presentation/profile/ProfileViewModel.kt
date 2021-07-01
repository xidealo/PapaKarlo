package com.bunbeauty.papakarlo.presentation.profile

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.State
import com.bunbeauty.common.extensions.toStateNullableSuccess
import com.bunbeauty.common.extensions.toStateSuccess
import com.bunbeauty.domain.model.local.user.User
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.UserAddressRepo
import com.bunbeauty.domain.repo.UserRepo
import com.bunbeauty.papakarlo.presentation.base.ToolbarViewModel
import com.bunbeauty.papakarlo.ui.profile.ProfileFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class ProfileViewModel : ToolbarViewModel() {
    abstract val userState: StateFlow<State<User?>>
    abstract val hasAddressState: StateFlow<State<Boolean>>

    abstract fun getUser()
    abstract fun getAddress(userId: String)
    abstract fun onOrderListClicked()
    abstract fun onAddressClicked()
    abstract fun onCreateAddressClicked()
    abstract fun goToLogin()
    abstract fun goToSettings()
}

class ProfileViewModelImpl @Inject constructor(
    private val dataStoreRepo: DataStoreRepo,
    private val userAddressRepo: UserAddressRepo,
    private val userRepo: UserRepo
) : ProfileViewModel() {

    init {
        getUser()
    }

    override val userState: MutableStateFlow<State<User?>> =
        MutableStateFlow(State.Loading())

    override val hasAddressState: MutableStateFlow<State<Boolean>> =
        MutableStateFlow(State.Loading())

    override fun getUser() {
        viewModelScope.launch(Dispatchers.Default) {
            userRepo.getUserWithBonuses(dataStoreRepo.userId.first()).onEach {
                userState.value = it.toStateNullableSuccess()
            }.launchIn(viewModelScope)
        }
    }

    override fun getAddress(userId: String) {
        viewModelScope.launch(Dispatchers.Default) {
            userAddressRepo.getUserAddressByUserId(userId).onEach {
                hasAddressState.emit(it.isNotEmpty().toStateSuccess())
            }.launchIn(viewModelScope)
        }
    }

    override fun onOrderListClicked() {
        router.navigate(ProfileFragmentDirections.toOrdersFragment("a"))
    }

    override fun onAddressClicked() {
        router.navigate(ProfileFragmentDirections.toAddressesBottomSheet(true))
    }

    override fun onCreateAddressClicked() {
        router.navigate(ProfileFragmentDirections.toCreationAddressFragment())
    }

    override fun goToLogin() {
        router.navigate(ProfileFragmentDirections.toLoginFragment())
    }

    override fun goToSettings() {
        if (userState.value is State.Success) {
            router.navigate(ProfileFragmentDirections.toSettingsFragment((userState.value as State.Success<User?>).data!!))
        }
    }
}