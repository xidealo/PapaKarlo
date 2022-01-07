package com.bunbeauty.papakarlo.presentation.cart

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.interactor.cart.ICartProductInteractor
import com.bunbeauty.domain.interactor.user.IUserInteractor
import com.bunbeauty.domain.model.product.LightCartProduct
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.presentation.base.CartViewModel
import com.bunbeauty.papakarlo.presentation.state.State
import com.bunbeauty.papakarlo.presentation.state.toSuccessOrEmpty
import com.bunbeauty.papakarlo.ui.fragment.cart.ConsumerCartFragmentDirections.*
import com.bunbeauty.presentation.enums.SuccessLoginDirection.TO_CREATE_ORDER
import com.bunbeauty.presentation.item.CartProductItem
import com.bunbeauty.presentation.util.resources.IResourcesProvider
import com.bunbeauty.presentation.util.string.IStringUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ConsumerCartViewModel @Inject constructor(
    private val resourcesProvider: IResourcesProvider,
    private val stringUtil: IStringUtil,
    private val userInteractor: IUserInteractor,
    private val cartProductInteractor: ICartProductInteractor,
) : CartViewModel() {

    private val mutableCartProductListState: MutableStateFlow<State<List<CartProductItem>>> =
        MutableStateFlow(State.Loading())
    val orderProductListState: StateFlow<State<List<CartProductItem>>> =
        mutableCartProductListState.asStateFlow()

    private val mutableDeliveryInfo: MutableStateFlow<String> = MutableStateFlow("")
    val deliveryInfo: StateFlow<String> = mutableDeliveryInfo.asStateFlow()

    private val mutableOldTotalCost: MutableStateFlow<String> = MutableStateFlow("")
    val oldTotalCost: StateFlow<String> = mutableOldTotalCost.asStateFlow()

    private val mutableNewTotalCost: MutableStateFlow<String> = MutableStateFlow("")
    val newTotalCost: StateFlow<String> = mutableNewTotalCost.asStateFlow()

    init {
        observeCartProducts()
        observeDelivery()
    }

    fun onMenuClicked() {
        router.navigateUp()
    }

    fun onCreateOrderClicked() {
        viewModelScope.launch {
            if (userInteractor.isUserAuthorize()) {
                router.navigate(toCreateOrderFragment())
            } else {
                router.navigate(toLoginFragment(TO_CREATE_ORDER))
            }
        }
    }

    fun onProductClicked(cartProductItem: CartProductItem) {
        router.navigate(
            toProductFragment(
                cartProductItem.menuProductUuid,
                cartProductItem.name,
                cartProductItem.photoLink
            )
        )
    }

    private fun observeCartProducts() {
        cartProductInteractor.observeCartProductList().launchOnEach { cartProductList ->
            mutableCartProductListState.value = cartProductList.map(::toItem).toSuccessOrEmpty()
        }
        cartProductInteractor.observeOldTotalCartCost().launchOnEach { oldTotalCost ->
            mutableOldTotalCost.value = stringUtil.getCostString(oldTotalCost)
        }
        cartProductInteractor.observeNewTotalCartCost().launchOnEach { newTotalCost ->
            mutableNewTotalCost.value = stringUtil.getCostString(newTotalCost)
        }
    }

    private fun observeDelivery() {
        cartProductInteractor.observeDelivery().launchOnEach { delivery ->
            mutableDeliveryInfo.value =
                resourcesProvider.getString(R.string.msg_consumer_cart_free_delivery_from) +
                        stringUtil.getCostString(delivery.forFree)
        }
    }

    private fun toItem(lightCartProduct: LightCartProduct): CartProductItem {
        return CartProductItem(
            uuid = lightCartProduct.uuid,
            name = lightCartProduct.name,
            newCost = stringUtil.getCostString(lightCartProduct.newCost),
            oldCost = stringUtil.getCostString(lightCartProduct.oldCost),
            photoLink = lightCartProduct.photoLink,
            count = lightCartProduct.count,
            menuProductUuid = lightCartProduct.menuProductUuid
        )
    }
}