package com.bunbeauty.papakarlo.presentation

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.State
import com.bunbeauty.common.extensions.toStateNullableSuccess
import com.bunbeauty.common.extensions.toStateSuccess
import com.bunbeauty.data.model.address.CafeAddress
import com.bunbeauty.data.model.address.UserAddress
import com.bunbeauty.data.model.user.User
import com.bunbeauty.data.utils.IDataStoreHelper
import com.bunbeauty.domain.repository.address.CafeAddressRepo
import com.bunbeauty.domain.repository.address.UserAddressRepo
import com.bunbeauty.domain.repository.user.UserRepo
import com.bunbeauty.papakarlo.presentation.base.ToolbarViewModel
import com.bunbeauty.papakarlo.ui.ProfileFragmentDirections
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
    abstract fun logout()
}

class ProfileViewModelImpl @Inject constructor(
    private val dataStoreHelper: IDataStoreHelper,
    private val userAddressRepo: UserAddressRepo,
    private val userRepo: UserRepo
) : ProfileViewModel() {

    override val userState: MutableStateFlow<State<User?>> =
        MutableStateFlow(State.Loading())

    override val hasAddressState: MutableStateFlow<State<Boolean>> =
        MutableStateFlow(State.Loading())

    override fun getUser() {
        viewModelScope.launch(Dispatchers.Default) {
            val useId = dataStoreHelper.userId.first()
            if (useId.isEmpty()) {
                userState.value = State.Success(null)
            } else {
                userRepo.getUserAsFlow(useId).onEach {
                    userState.value = it.toStateNullableSuccess()
                }.launchIn(viewModelScope)
            }
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

    override fun logout() {
        viewModelScope.launch {
            dataStoreHelper.saveUserId("")
        }
    }
}