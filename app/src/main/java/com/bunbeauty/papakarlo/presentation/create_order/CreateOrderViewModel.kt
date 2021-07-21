package com.bunbeauty.papakarlo.presentation.create_order

import android.util.Log
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.Constants.ASAP
import com.bunbeauty.common.Constants.BONUSES_PERCENT
import com.bunbeauty.common.Constants.COMMENT_REQUEST_KEY
import com.bunbeauty.common.Constants.RESULT_COMMENT_KEY
import com.bunbeauty.common.State
import com.bunbeauty.domain.enums.OneLineActionType
import com.bunbeauty.domain.model.OneLineActionModel
import com.bunbeauty.domain.model.entity.UserEntity
import com.bunbeauty.domain.model.local.address.Address
import com.bunbeauty.domain.model.local.order.Order
import com.bunbeauty.domain.model.local.order.OrderEntity
import com.bunbeauty.domain.repo.*
import com.bunbeauty.domain.util.network.INetworkHelper
import com.bunbeauty.domain.util.product.IProductHelper
import com.bunbeauty.domain.util.resources.IResourcesProvider
import com.bunbeauty.domain.util.string_helper.IStringUtil
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.ui.fragment.create_order.CreateOrderFragmentDirections.*
import com.instacart.library.truetime.TrueTime
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.*
import org.joda.time.DateTime
import javax.inject.Inject
import kotlin.math.roundToInt

class CreateOrderViewModel @Inject constructor(
    private val dataStoreRepo: DataStoreRepo,
    private val networkHelper: INetworkHelper,
    private val stringUtil: IStringUtil,
    private val productHelper: IProductHelper,
    private val resourcesProvider: IResourcesProvider,
    private val cartProductRepo: CartProductRepo,
    private val orderRepo: OrderRepo,
    private val cafeAddressRepo: CafeAddressRepo,
    private val userAddressRepo: UserAddressRepo,
    private val cafeRepo: CafeRepo,
    private val userRepo: UserRepo
) : BaseViewModel() {

    private val mutableIsDelivery: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isDelivery: StateFlow<Boolean> = mutableIsDelivery.asStateFlow()

    private val mutablePhone: MutableStateFlow<String?> = MutableStateFlow(null)
    val phone: StateFlow<String?> = mutablePhone.asStateFlow()

    private val mutableAddress: MutableStateFlow<String?> = MutableStateFlow(null)
    val address: StateFlow<String?> = mutableAddress.asStateFlow()

    private val actionDeliveryTime =
        resourcesProvider.getString(R.string.action_create_order_delivery_time)
    private val actionPickupTime =
        resourcesProvider.getString(R.string.action_create_order_pickup_time)
    private val mutableActionDeferredTime: MutableStateFlow<String> =
        MutableStateFlow(actionDeliveryTime)
    val actionDeferredTime: StateFlow<String> = mutableActionDeferredTime.asStateFlow()

    private val hintDeliveryTime =
        resourcesProvider.getString(R.string.hint_create_order_delivery_time)
    private val hintPickupTime =
        resourcesProvider.getString(R.string.hint_create_order_pickup_time)
    private val mutableHintDeferredTime: MutableStateFlow<String> =
        MutableStateFlow(hintDeliveryTime)
    val hintDeferredTime: StateFlow<String> = mutableHintDeferredTime.asStateFlow()

    private val mutableDeferredTime: MutableStateFlow<String?> = MutableStateFlow(null)
    val deferredTime: StateFlow<String?> = mutableDeferredTime.asStateFlow()

    private val mutableComment: MutableStateFlow<String?> = MutableStateFlow(null)
    val comment: StateFlow<String?> = mutableComment.asStateFlow()

    init {
        subscribeOnUser()
        subscribeOnAddress()
        subscribeOnDeferredTime()
    }

    private fun subscribeOnUser() {
        dataStoreRepo.userUuid.flatMapLatest { userUuid ->
            userRepo.getUserByUuid(userUuid).onEach { user ->
                mutablePhone.value = user?.phone
            }
        }.launchIn(viewModelScope)
    }

    private fun subscribeOnAddress() {
        isDelivery.flatMapLatest { isDelivery ->
            if (isDelivery) {
                dataStoreRepo.userUuid.flatMapLatest { userUuid ->
                    if (userUuid == null) {
                        userAddressRepo.unassignedUserAddressList.onEach { addressList ->
                            if (addressList.isNotEmpty()) {
                                mutableAddress.value = stringUtil.toString(addressList.first())
                            }
                        }
                    } else {
                        dataStoreRepo.userAddressUuid.flatMapLatest { userAddressUuid ->
                            if (userAddressUuid == null) {
                                userAddressRepo.getUserAddressListByUserUuid(userUuid)
                                    .onEach { addressList ->
                                        if (addressList.isNotEmpty()) {
                                            mutableAddress.value =
                                                stringUtil.toString(addressList.first())
                                        }
                                    }
                            } else {
                                userAddressRepo.getUserAddressByUuid(userAddressUuid)
                                    .onEach { userAddress ->
                                        if (userAddress != null) {
                                            mutableAddress.value = stringUtil.toString(userAddress)
                                        }
                                    }
                            }
                        }
                    }
                }
            } else {
                dataStoreRepo.cafeAddressUuid.flatMapLatest { cafeAddressUuid ->
                    if (cafeAddressUuid == null) {
                        cafeAddressRepo.getCafeAddresses().onEach { addressList ->
                            if (addressList.isNotEmpty()) {
                                mutableAddress.value = stringUtil.toString(addressList.first())
                            } else {
                                mutableAddress.value = ""
                            }
                        }
                    } else {
                        cafeAddressRepo.getCafeAddressByUuid(cafeAddressUuid)
                            .onEach { cafeAddress ->
                                mutableAddress.value = stringUtil.toString(cafeAddress)
                            }
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun subscribeOnDeferredTime() {
        dataStoreRepo.deferredTime.onEach { deferredTime ->
            mutableDeferredTime.value = mapDeferredTime(deferredTime)
        }.launchIn(viewModelScope)
    }

    fun onIsDeliveryChanged(isDelivery: Boolean) {
        mutableIsDelivery.value = isDelivery
        if (isDelivery) {
            mutableActionDeferredTime.value = actionDeliveryTime
            mutableHintDeferredTime.value = hintDeliveryTime
        } else {
            mutableActionDeferredTime.value = actionPickupTime
            mutableHintDeferredTime.value = hintPickupTime
        }
    }

    fun onDeferredTimeSelected(deferredTime: String) {
        mutableDeferredTime.value = mapDeferredTime(deferredTime)
        viewModelScope.launch {
            dataStoreRepo.saveDeferredTime(deferredTime)
        }
    }

    fun onCommentChanged(newComment: String) {
        if (newComment.isEmpty()) {
            mutableComment.value = null
        } else {
            mutableComment.value = newComment
        }
    }

    private fun mapDeferredTime(deferredTime: String?): String? {
        return if (deferredTime == ASAP) {
            resourcesProvider.getString(R.string.msg_deferred_time_asap)
        } else {
            deferredTime
        }
    }






    fun createOrder(
        comment: String,
        phone: String,
        email: String,
        deferredHours: Int?,
        deferredMinutes: Int?,
        spentBonusesString: String
    ) {
//        if (selectedAddressState.value == null) {
//            showError(resourcesProvider.getString(R.string.error_create_order_address))
//            return
//        }
//        val orderEntity = OrderEntity(
//            comment = comment,
//            phone = phone,
//            email = email,
//            deferredTime = stringUtil.toStringTime(deferredHours, deferredMinutes),
//            isDelivery = isDelivery.value,
//            code = generateCode(),
//            address = selectedAddressState.value!!,
//        )
//        //set try to get time?
//        val timestamp = try {
//            TrueTime.now().time
//        } catch (exception: Exception) {
//            DateTime.now().millis
//        }
//        viewModelScope.launch {
//            val order = Order(
//                orderEntity,
//                cartProductRepo.getCartProductList(),
//                cafeRepo.getCafeEntityByDistrict(
//                    orderEntity.address.street?.districtId ?: "ERROR CAFE"
//                ).id,
//                timestamp = timestamp
//            )
//            if (userEntityState.value is State.Success) {
//                val user = (userEntityState.value as State.Success<UserEntity?>).data
//                //with login
//                if (user != null) {
//                    val spentBonuses = if (spentBonusesString.isEmpty()) {
//                        0
//                    } else {
//                        spentBonusesString.toInt()
//                    }
//                    if (user.bonusList.sum() - spentBonuses < 0) {
//                        showError("Недостаточно бонусов")
//                        return@launch
//                    } else {
//                        if (spentBonuses != 0)
//                            user.bonusList.add(-spentBonuses)
//                    }
//                    user.bonusList.add((productHelper.getNewTotalCost(order.cartProducts) * BONUSES_PERCENT).roundToInt())
//                    order.orderEntity.bonus = spentBonuses
//                    order.orderEntity.userId = user.uuid
//                    userRepo.insertToBonusList(user)
//                } else {
//                    dataStoreRepo.savePhone(phone)
//                    dataStoreRepo.saveEmail(email)
//                }
//                orderRepo.insert(order)
//            }
//            withContext(Main) {
//                showMessage(resourcesProvider.getString(R.string.msg_create_order_order_code) + orderEntity.code)
//                router.navigate(backToMenuFragment())
//            }
//        }
    }

    private fun generateCode(): String {
        val letters = resourcesProvider.getString(R.string.code_letters)

        val number = (DateTime.now().secondOfDay % (letters.length * CODE_NUMBER_COUNT))
        val codeLetter = letters[number % letters.length].toString()
        val codeNumber = (number / letters.length).toString()

        return codeLetter + codeNumber
    }

    fun onAddressClicked() {
        router.navigate(toAddressesBottomSheet(isDelivery.value))
    }

    fun onAddAddressClicked() {
        router.navigate(toCreationAddressFragment())
    }

    fun onPhoneClicked() {
        router.navigate(toLoginFragment())
    }

    fun onDeferredTimeClicked() {
        router.navigate(toDeferredTimeBottomSheet(actionDeferredTime.value))
    }

    fun onAddCommentClicked() {
        val oneLineActionModel = OneLineActionModel(
            title = resourcesProvider.getString(R.string.title_create_order_addition_comment),
            infoText = null,
            hint = resourcesProvider.getString(R.string.hint_create_order_comment),
            type = OneLineActionType.COMMENT,
            inputText = "",
            buttonText = resourcesProvider.getString(R.string.action_create_order_save_comment),
            requestKey = COMMENT_REQUEST_KEY,
            resultKey = RESULT_COMMENT_KEY
        )
        router.navigate(toOneLineActionBottomSheet(oneLineActionModel))
    }

    fun onEditCommentClicked() {
        val oneLineActionModel = OneLineActionModel(
            title = resourcesProvider.getString(R.string.title_create_order_editing_comment),
            infoText = null,
            hint = resourcesProvider.getString(R.string.hint_create_order_comment),
            type = OneLineActionType.COMMENT,
            inputText = comment.value ?: "",
            buttonText = resourcesProvider.getString(R.string.action_create_order_save_comment),
            requestKey = COMMENT_REQUEST_KEY,
            resultKey = RESULT_COMMENT_KEY
        )
        router.navigate(toOneLineActionBottomSheet(oneLineActionModel))
    }

    companion object {
        private const val CODE_NUMBER_COUNT = 100 // 0 - 99
    }
}