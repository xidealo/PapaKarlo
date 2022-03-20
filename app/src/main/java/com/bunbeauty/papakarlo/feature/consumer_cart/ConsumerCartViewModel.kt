package com.bunbeauty.papakarlo.feature.consumer_cart

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.interactor.cart.ICartProductInteractor
import com.bunbeauty.domain.interactor.user.IUserInteractor
import com.bunbeauty.domain.model.cart.LightCartProduct
import com.bunbeauty.papakarlo.common.state.State
import com.bunbeauty.papakarlo.common.view_model.CartViewModel
import com.bunbeauty.papakarlo.enums.SuccessLoginDirection.TO_CREATE_ORDER
import com.bunbeauty.papakarlo.extensions.toStateSuccess
import com.bunbeauty.papakarlo.feature.consumer_cart.ConsumerCartFragmentDirections.*
import com.bunbeauty.papakarlo.util.string.IStringUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ConsumerCartViewModel(
    private val stringUtil: IStringUtil,
    private val userInteractor: IUserInteractor,
    private val cartProductInteractor: ICartProductInteractor,
) : CartViewModel() {

    private val mutableConsumerCartState: MutableStateFlow<State<ConsumerCartUI>> =
        MutableStateFlow(State.Loading())
    val consumerCartState: StateFlow<State<ConsumerCartUI>> = mutableConsumerCartState.asStateFlow()

    init {
        observeConsumerCart()
    }

    fun onMenuClicked() {
        router.navigate(toMenuFragment())
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

    fun onProductClicked(cartProductItem: CartProductItemModel) {
        router.navigate(
            toProductFragment(
                cartProductItem.menuProductUuid,
                cartProductItem.name,
                cartProductItem.photoLink
            )
        )
    }

    private fun observeConsumerCart() {
        cartProductInteractor.observeConsumerCart().launchOnEach { consumerCart ->
            mutableConsumerCartState.value = if (consumerCart.cartProductList.isEmpty()) {
                State.Empty()
            } else {
                ConsumerCartUI(
                    forFreeDelivery = stringUtil.getCostString(consumerCart.forFreeDelivery),
                    cartProductList = consumerCart.cartProductList.map(::toItem),
                    oldTotalCost = consumerCart.oldTotalCost?.let { oldTotalCost ->
                        stringUtil.getCostString(oldTotalCost)
                    },
                    newTotalCost = stringUtil.getCostString(consumerCart.newTotalCost),
                ).toStateSuccess()
            }
        }
    }

    private fun toItem(lightCartProduct: LightCartProduct): CartProductItemModel {
        return CartProductItemModel(
            uuid = lightCartProduct.uuid,
            name = lightCartProduct.name,
            newCost = stringUtil.getCostString(lightCartProduct.newCost),
            oldCost = lightCartProduct.oldCost?.let { oldCost ->
                stringUtil.getCostString(oldCost)
            },
            photoLink = lightCartProduct.photoLink,
            count = lightCartProduct.count,
            menuProductUuid = lightCartProduct.menuProductUuid
        )
    }
}