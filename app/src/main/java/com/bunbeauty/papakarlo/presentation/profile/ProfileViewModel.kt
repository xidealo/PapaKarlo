package com.bunbeauty.papakarlo.presentation.profile

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.State
import com.bunbeauty.common.extensions.toStateNullableSuccess
import com.bunbeauty.common.extensions.toStateSuccess
import com.bunbeauty.domain.model.local.order.Order
import com.bunbeauty.domain.model.local.user.User
import com.bunbeauty.domain.repo.*
import com.bunbeauty.domain.util.order.IOrderUtil
import com.bunbeauty.domain.util.product.IProductHelper
import com.bunbeauty.domain.util.resources.IResourcesProvider
import com.bunbeauty.domain.util.string_helper.IStringUtil
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.presentation.base.CartViewModel
import com.bunbeauty.papakarlo.ui.profile.ProfileFragmentDirections
import com.bunbeauty.presentation.view_model.base.adapter.OrderItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

abstract class ProfileViewModel(
    cartProductRepo: CartProductRepo,
    stringUtil: IStringUtil,
    productHelper: IProductHelper,
) : CartViewModel(cartProductRepo, stringUtil, productHelper) {
    abstract val userState: StateFlow<State<User?>>
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
    private val dataStoreRepo: DataStoreRepo,
    private val userAddressRepo: UserAddressRepo,
    private val userRepo: UserRepo,
    val stringHelper: IStringUtil,
    cartProductRepo: CartProductRepo,
    productHelper: IProductHelper,
    private val orderRepo: OrderRepo,
    private val orderUtil: IOrderUtil,
    private val resourcesProvider: IResourcesProvider
) : ProfileViewModel(cartProductRepo, stringHelper, productHelper) {

    init {
        getUser(getUserId())
        getAddress(getUserId())
        getLastOrder(getUserId())
    }

    override val userState: MutableStateFlow<State<User?>> =
        MutableStateFlow(State.Loading())

    override val lastOrderState: MutableStateFlow<State<OrderItem>> =
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
                    toItemModel(orderList.maxByOrNull { it.orderEntity.time }!!)
                        .toStateSuccess()
            }
        }.launchIn(viewModelScope)
    }

    override fun getBonusesString(bonusList: MutableList<Int>): String {
        return stringHelper.getCostString(bonusList.sum())
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

    override fun goToOrder(orderItem: OrderItem) {
        router.navigate(
            ProfileFragmentDirections.toOrderDerailsFragment(
                orderItem.uuid,
                orderItem.code
            )
        )
    }

    override fun goToSettings() {
        if (userState.value is State.Success) {
            router.navigate(
                ProfileFragmentDirections
                    .toSettingsFragment((userState.value as State.Success<User?>).data!!.userId)
            )
        }
    }

    private fun toItemModel(order: Order): OrderItem {
        return OrderItem(
            uuid = order.orderEntity.uuid,
            orderStatus = stringHelper.toStringOrderStatus(order.orderEntity.orderStatus),
            orderColor = orderUtil.getBackgroundColor(order.orderEntity.orderStatus),
            code = order.orderEntity.code,
            time = stringHelper.toStringTime(order.orderEntity),
            deferredTime = if (order.orderEntity.deferredTime.isNotEmpty())
                "${resourcesProvider.getString(R.string.action_profile_to_time)} ${order.orderEntity.deferredTime}"
            else
                ""
        )
    }
}