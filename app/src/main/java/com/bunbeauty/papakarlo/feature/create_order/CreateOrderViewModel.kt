package com.bunbeauty.papakarlo.feature.create_order

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.Constants.COMMENT_REQUEST_KEY
import com.bunbeauty.common.Constants.RESULT_COMMENT_KEY
import com.bunbeauty.domain.interactor.address.IAddressInteractor
import com.bunbeauty.domain.interactor.cafe.ICafeInteractor
import com.bunbeauty.domain.interactor.cart.ICartProductInteractor
import com.bunbeauty.domain.interactor.deferred_time.IDeferredTimeInteractor
import com.bunbeauty.domain.interactor.order.IOrderInteractor
import com.bunbeauty.domain.interactor.user.IUserInteractor
import com.bunbeauty.domain.model.datee_time.Time
import com.bunbeauty.domain.repo.*
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import com.bunbeauty.papakarlo.feature.create_order.CreateOrderFragmentDirections.*
import com.bunbeauty.papakarlo.feature.edit_text.EditTextSettings
import com.bunbeauty.papakarlo.feature.edit_text.EditTextType
import com.bunbeauty.papakarlo.util.resources.IResourcesProvider
import com.bunbeauty.papakarlo.util.string.IStringUtil
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreateOrderViewModel @Inject constructor(
    private val addressInteractor: IAddressInteractor,
    private val cartProductInteractor: ICartProductInteractor,
    private val orderInteractor: IOrderInteractor,
    private val cafeInteractor: ICafeInteractor,
    private val userInteractor: IUserInteractor,
    private val deferredTimeInteractor: IDeferredTimeInteractor,
    private val stringUtil: IStringUtil,
    private val resourcesProvider: IResourcesProvider,
) : BaseViewModel() {

    private val mutableIsDelivery: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isDelivery: StateFlow<Boolean> = mutableIsDelivery.asStateFlow()

    private var selectedUserAddressUuid: String? = null
    private var selectedCafeUuid: String? = null
    private val mutableAddress: MutableStateFlow<String?> = MutableStateFlow(null)
    val address: StateFlow<String?> = mutableAddress.asStateFlow()

    private val mutableComment: MutableStateFlow<String?> = MutableStateFlow(null)
    val comment: StateFlow<String?> = mutableComment.asStateFlow()

    private val deliveryTimeHint =
        resourcesProvider.getString(R.string.hint_create_order_delivery_time)
    private val pickupTimeHint =
        resourcesProvider.getString(R.string.hint_create_order_pickup_time)
    private val mutableDeferredTimeHint: MutableStateFlow<String> =
        MutableStateFlow(deliveryTimeHint)
    val deferredTimeHint: StateFlow<String> = mutableDeferredTimeHint.asStateFlow()

    private val deliveryAddressHint =
        resourcesProvider.getString(R.string.hint_create_order_delivery_address)
    private val cafeAddressHint =
        resourcesProvider.getString(R.string.hint_create_order_cafe_address)
    private val mutableAddressHint: MutableStateFlow<String> = MutableStateFlow(deliveryAddressHint)
    val addressHint: StateFlow<String> = mutableAddressHint.asStateFlow()

    private var selectedDeferredTime: Time? = null
    private var selectedDeferredTimeMillis: Long? = null
    private val asap = resourcesProvider.getString(R.string.msg_deferred_time_asap)
    private val mutableDeferredTime: MutableStateFlow<String> = MutableStateFlow(asap)
    val deferredTime: StateFlow<String> = mutableDeferredTime.asStateFlow()

    private val mutableTotalCost: MutableStateFlow<String> = MutableStateFlow("")
    val totalCost: StateFlow<String> = mutableTotalCost.asStateFlow()

    private val mutableDeliveryCost: MutableStateFlow<String> = MutableStateFlow("")
    val deliveryCost: StateFlow<String> = mutableDeliveryCost.asStateFlow()

    private val mutableAmountToPay: MutableStateFlow<String> = MutableStateFlow("")
    val amountToPay: StateFlow<String> = mutableAmountToPay.asStateFlow()

    private val mutableIsLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = mutableIsLoading.asStateFlow()

    init {
        observeAddress()
        observeCartProducts()
    }

    fun onIsDeliveryChanged(isDelivery: Boolean) {
        mutableIsDelivery.value = isDelivery
        if (isDelivery) {
            mutableDeferredTimeHint.value = deliveryTimeHint
            mutableAddressHint.value = deliveryAddressHint
        } else {
            mutableDeferredTimeHint.value = pickupTimeHint
            mutableAddressHint.value = cafeAddressHint
        }
    }

    fun onDeferredTimeSelected(deferredTimeMillis: Long) {
        if (deferredTimeMillis == -1L) {
            selectedDeferredTimeMillis = null
            selectedDeferredTime = null
            mutableDeferredTime.value = asap
        } else {
            selectedDeferredTimeMillis = deferredTimeMillis
            viewModelScope.launch {
                selectedDeferredTime = deferredTimeInteractor.getDeferredTime(deferredTimeMillis)
                mutableDeferredTime.value = stringUtil.getTimeString(
                    deferredTimeInteractor.getDeferredTime(deferredTimeMillis)
                )
            }
        }
    }

    fun onCommentChanged(newComment: String) {
        if (newComment.isEmpty()) {
            mutableComment.value = null
        } else {
            mutableComment.value = newComment
        }
    }

    fun onUserAddressChanged(userAddressUuid: String) {
        viewModelScope.launch {
            addressInteractor.saveSelectedUserAddress(userAddressUuid)
        }
    }

    fun onCafeAddressChanged(cafeUuid: String) {
        viewModelScope.launch {
            cafeInteractor.selectCafe(cafeUuid)
        }
    }

    fun onCreateOrderClicked() {
        mutableIsLoading.value = true

        val isDelivery = mutableIsDelivery.value
        val selectedAddress = address.value
        if ((isDelivery && (selectedUserAddressUuid == null))
            || (!isDelivery && (selectedCafeUuid == null))
            || selectedAddress == null
        ) {
            showError(resourcesProvider.getString(R.string.error_create_order_address), false)
            mutableIsLoading.value = false
            return
        }

        viewModelScope.launch {
            if (!userInteractor.isUserAuthorize()) {
                showError(resourcesProvider.getString(R.string.error_create_order_user), false)
                goBack()
                return@launch
            }

            val orderCode = orderInteractor.createOrder(
                isDelivery = isDelivery,
                userAddressUuid = selectedUserAddressUuid,
                cafeUuid = selectedCafeUuid,
                addressDescription = selectedAddress,
                comment = comment.value,
                deferredTime = selectedDeferredTimeMillis,
            )

            if (orderCode == null) {
                showError(
                    resourcesProvider.getString(R.string.error_create_order_something_went_wrong),
                    false
                )
                mutableIsLoading.value = false
            } else {
                cartProductInteractor.removeAllProductsFromCart()
                showMessage(
                    resourcesProvider.getString(R.string.msg_create_order_order_code) + orderCode.code,
                    false
                )
                router.navigate(toProfileFragment())
            }
        }
    }

    private fun observeAddress() {
        mutableIsDelivery.flatMapLatest { isDelivery ->
            if (isDelivery) {
                addressInteractor.observeAddress().onEach { userAddress ->
                    selectedUserAddressUuid = userAddress?.uuid
                    mutableAddress.value = stringUtil.getUserAddressString(userAddress)
                }
            } else {
                cafeInteractor.observeSelectedCafeAddress().onEach { cafeAddress ->
                    mutableAddress.value = cafeAddress.address
                    selectedCafeUuid = cafeAddress.cafeUuid
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun observeCartProducts() {
        cartProductInteractor.observeNewTotalCartCost().launchOnEach { totalCost ->
            mutableTotalCost.value = stringUtil.getCostString(totalCost)
        }
        cartProductInteractor.observeDeliveryCost().launchOnEach { deliveryCost ->
            mutableDeliveryCost.value = stringUtil.getCostString(deliveryCost)
        }
        cartProductInteractor.observeAmountToPay(isDelivery).launchOnEach { amountToPay ->
            mutableAmountToPay.value = stringUtil.getCostString(amountToPay)
        }
    }

    fun onAddressClicked() {
        if (isDelivery.value) {
            router.navigate(toNavAddress(true))
        } else {
            router.navigate(toCafeAddressesBottomSheet())
        }
    }

    fun onAddAddressClicked() {
        router.navigate(toCreateAddressFragment())
    }

    fun onDeferredTimeClicked() {
        router.navigate(
            toDeferredTimeBottomSheet(
                deferredTimeHint.value,
                selectedDeferredTime?.hourOfDay ?: -1,
                selectedDeferredTime?.minuteOfHour ?: -1
            )
        )
    }

    fun onAddCommentClicked() {
        val oneLineActionModel = EditTextSettings(
            title = resourcesProvider.getString(R.string.title_create_order_addition_comment),
            infoText = null,
            hint = resourcesProvider.getString(R.string.hint_create_order_comment),
            type = EditTextType.TEXT,
            inputText = "",
            buttonText = resourcesProvider.getString(R.string.action_create_order_save_comment),
            requestKey = COMMENT_REQUEST_KEY,
            resultKey = RESULT_COMMENT_KEY
        )
        router.navigate(toOneLineActionBottomSheet(oneLineActionModel))
    }

    fun onEditCommentClicked() {
        val oneLineActionModel = EditTextSettings(
            title = resourcesProvider.getString(R.string.title_create_order_editing_comment),
            infoText = null,
            hint = resourcesProvider.getString(R.string.hint_create_order_comment),
            type = EditTextType.TEXT,
            inputText = comment.value ?: "",
            buttonText = resourcesProvider.getString(R.string.action_create_order_save_comment),
            requestKey = COMMENT_REQUEST_KEY,
            resultKey = RESULT_COMMENT_KEY
        )
        router.navigate(toOneLineActionBottomSheet(oneLineActionModel))
    }
}