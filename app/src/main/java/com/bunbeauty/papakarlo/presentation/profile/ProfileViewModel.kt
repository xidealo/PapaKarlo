package com.bunbeauty.papakarlo.presentation.profile

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.State
import com.bunbeauty.common.extensions.toStateNullableSuccess
import com.bunbeauty.common.extensions.toStateSuccess
import com.bunbeauty.data.mapper.adapter.OrderAdapterMapper
import com.bunbeauty.domain.model.adapter.OrderAdapterModel
import com.bunbeauty.domain.model.local.user.User
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.OrderRepo
import com.bunbeauty.domain.repo.UserAddressRepo
import com.bunbeauty.domain.repo.UserRepo
import com.bunbeauty.domain.util.string_helper.IStringHelper
import com.bunbeauty.papakarlo.presentation.base.ToolbarViewModel
import com.bunbeauty.papakarlo.ui.profile.ProfileFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

abstract class ProfileViewModel : ToolbarViewModel() {
    abstract val userState: StateFlow<State<User?>>
    abstract val hasAddressState: StateFlow<State<Boolean>>
    abstract val lastOrderState: StateFlow<State<OrderAdapterModel>>

    abstract fun getBonusesString(bonusList: MutableList<Int>): String
    abstract fun onOrderListClicked()
    abstract fun onAddressClicked()
    abstract fun onCreateAddressClicked()
    abstract fun goToLogin()
    abstract fun goToSettings()
    abstract fun goToOrder(orderUuid: String)
}

class ProfileViewModelImpl @Inject constructor(
    private val dataStoreRepo: DataStoreRepo,
    private val userAddressRepo: UserAddressRepo,
    private val userRepo: UserRepo,
    private val iStringHelper: IStringHelper,
    private val orderRepo: OrderRepo,
    private val orderMapper: OrderAdapterMapper
) : ProfileViewModel() {

    init {
        getUser(getUserId())
        getAddress(getUserId())
        getLastOrder(getUserId())
    }

    override val userState: MutableStateFlow<State<User?>> =
        MutableStateFlow(State.Loading())

    override val lastOrderState: MutableStateFlow<State<OrderAdapterModel>> =
        MutableStateFlow(State.Empty())

    override val hasAddressState: MutableStateFlow<State<Boolean>> =
        MutableStateFlow(State.Loading())

    private fun getUser(userId: String) {
        viewModelScope.launch(Dispatchers.Default) {
            userRepo.getUserWithBonuses(userId).onEach {
                userState.value = it.toStateNullableSuccess()
            }.launchIn(viewModelScope)
        }
    }

    private fun getUserId() = runBlocking { dataStoreRepo.userId.first() }

    private fun getAddress(userId: String) {
        userAddressRepo.getUserAddressByUserId(userId).onEach {
            hasAddressState.emit(it.isNotEmpty().toStateSuccess())
        }.launchIn(viewModelScope)
    }

    private fun getLastOrder(userId: String) {
        orderRepo.getOrdersWithCartProductsByUserId(userId).onEach { orderList ->
            if (orderList.isNotEmpty()) {
                lastOrderState.value =
                    orderMapper.from(orderList.maxByOrNull { it.orderEntity.time }!!)
                        .toStateSuccess()
            }
        }.launchIn(viewModelScope)
    }

    override fun getBonusesString(bonusList: MutableList<Int>): String {
        return iStringHelper.getCostString(bonusList.sum())
    }

    override fun onOrderListClicked() {
        router.navigate(ProfileFragmentDirections.toOrdersFragment(getUserId()))
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

    override fun goToOrder(orderUuid: String) {
        router.navigate(ProfileFragmentDirections.toOrderDerailsFragment(orderUuid))
    }

    override fun goToSettings() {
        if (userState.value is State.Success) {
            router.navigate(
                ProfileFragmentDirections
                    .toSettingsFragment((userState.value as State.Success<User?>).data!!.userId)
            )
        }
    }
}