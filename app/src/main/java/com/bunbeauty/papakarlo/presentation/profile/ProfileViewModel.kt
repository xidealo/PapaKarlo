package com.bunbeauty.papakarlo.presentation.profile

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.State
import com.bunbeauty.common.extensions.toStateSuccess
import com.bunbeauty.domain.auth.IAuthUtil
import com.bunbeauty.domain.model.Order
import com.bunbeauty.domain.model.User
import com.bunbeauty.domain.repo.OrderRepo
import com.bunbeauty.domain.repo.UserAddressRepo
import com.bunbeauty.domain.repo.UserRepo
import com.bunbeauty.domain.util.order.IOrderUtil
import com.bunbeauty.presentation.util.resources.IResourcesProvider
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.di.annotation.Api
import com.bunbeauty.papakarlo.presentation.base.CartViewModel
import com.bunbeauty.papakarlo.ui.profile.ProfileFragmentDirections.*
import com.bunbeauty.presentation.util.string.IStringUtil
import com.bunbeauty.presentation.item.OrderItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class ProfileViewModel : CartViewModel() {
    abstract val userState: StateFlow<State<User>>
    abstract val hasAddressState: StateFlow<State<Boolean>>
    abstract val lastOrderState: StateFlow<State<OrderItem>>

    abstract fun getBonusesString(bonusList: MutableList<Int>): String
    abstract fun onOrderListClicked()
    abstract fun onAddressClicked()
    abstract fun onCreateAddressClicked()
    abstract fun goToLogin()
    abstract fun goToSettings()
    abstract fun goToOrder(orderItem: OrderItem)
}

class ProfileViewModelImpl @Inject constructor(
    @Api private val userAddressRepo: UserAddressRepo,
    @Api private val userRepo: UserRepo,
    @Api private val orderRepo: OrderRepo,
    private val stringHelper: IStringUtil,
    private val authUtil: IAuthUtil,
    private val orderUtil: IOrderUtil,
    private val resourcesProvider: IResourcesProvider
) : ProfileViewModel() {

    private var user: User? = null
    override val userState: MutableStateFlow<State<User>> = MutableStateFlow(State.Loading())

    override val hasAddressState: MutableStateFlow<State<Boolean>> =
        MutableStateFlow(State.Loading())

    override val lastOrderState: MutableStateFlow<State<OrderItem>> =
        MutableStateFlow(State.Empty())

    init {
        subscribeOnUser()
        getAddress(authUtil.userUuid)
        subscribeOnLastOrder()
    }
    
    private fun subscribeOnUser() {
        val userUuid = authUtil.userUuid
        if (userUuid == null) {
            userState.value = State.Empty()
        } else {
            viewModelScope.launch {
                userRepo.observeUserByUuid(userUuid).onEach { observedUser ->
                    user = observedUser
                    userState.value = observedUser?.toStateSuccess() ?: State.Empty()
                }.launchIn(viewModelScope)
            }
        }
    }

    private fun getAddress(userId: String?) {
        if (userId != null) {
            userAddressRepo.observeUserAddressListByUserUuid(userId).onEach {
                hasAddressState.emit(it.isNotEmpty().toStateSuccess())
            }.launchIn(viewModelScope)
        }
    }

    private fun subscribeOnLastOrder() {
        orderRepo.observeLastOrder().onEach { order ->
            if (order != null) {
                lastOrderState.value = toItemModel(order).toStateSuccess()
            }
        }.launchIn(viewModelScope)
    }

    override fun getBonusesString(bonusList: MutableList<Int>): String {
        return stringHelper.getCostString(bonusList.sum())
    }

    override fun onOrderListClicked() {
        authUtil.userUuid?.let { userUuid ->
            router.navigate(toOrdersFragment(userUuid))
        }
    }

    override fun onAddressClicked() {
        router.navigate(toAddressesBottomSheet(true))
    }

    override fun onCreateAddressClicked() {
        router.navigate(toCreationAddressFragment())
    }

    override fun goToLogin() {
        router.navigate(toLoginFragment())
    }

    override fun goToOrder(orderItem: OrderItem) {
        router.navigate(toOrderDerailsFragment(orderItem.uuid, orderItem.code))
    }

    override fun goToSettings() {
        user?.uuid?.let { userUuid ->
            router.navigate(toSettingsFragment(userUuid))
        }
    }

    private fun toItemModel(order: Order): OrderItem {
        return OrderItem(
            uuid = order.uuid,
            orderStatus = stringHelper.toStringOrderStatus(order.orderStatus),
            orderColor = orderUtil.getBackgroundColor(order.orderStatus),
            code = order.code,
            time = stringHelper.toStringTime(order.time),
            deferredTime = if (order.deferredTime?.isNotEmpty() == true)
                "${resourcesProvider.getString(R.string.action_profile_to_time)} ${order.deferredTime}"
            else
                ""
        )
    }
}