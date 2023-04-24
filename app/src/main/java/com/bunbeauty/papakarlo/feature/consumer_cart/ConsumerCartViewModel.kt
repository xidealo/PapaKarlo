package com.bunbeauty.papakarlo.feature.consumer_cart

import androidx.lifecycle.viewModelScope
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.model.SuccessLoginDirection.TO_CREATE_ORDER
import com.bunbeauty.papakarlo.common.state.State
import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import com.bunbeauty.papakarlo.feature.consumer_cart.model.CartProductItem
import com.bunbeauty.papakarlo.feature.consumer_cart.model.ConsumerCartUI
import com.bunbeauty.papakarlo.util.string.IStringUtil
import com.bunbeauty.shared.domain.feature.cart.AddCartProductUseCase
import com.bunbeauty.shared.domain.feature.cart.RemoveCartProductUseCase
import com.bunbeauty.shared.domain.interactor.cart.ICartProductInteractor
import com.bunbeauty.shared.domain.interactor.user.IUserInteractor
import com.bunbeauty.shared.domain.model.cart.ConsumerCart
import com.bunbeauty.shared.domain.model.cart.LightCartProduct
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ConsumerCartViewModel(
    private val stringUtil: IStringUtil,
    private val userInteractor: IUserInteractor,
    private val cartProductInteractor: ICartProductInteractor,
    private val addCartProductUseCase: AddCartProductUseCase,
    private val removeCartProductUseCase: RemoveCartProductUseCase,
) : BaseViewModel() {

    private val mutableConsumerCartState: MutableStateFlow<State<ConsumerCartUI>> =
        MutableStateFlow(State.Loading())
    val consumerCartState: StateFlow<State<ConsumerCartUI>> = mutableConsumerCartState.asStateFlow()

    private var observeConsumerCartJob: Job? = null

    fun getConsumerCart() {
        viewModelScope.launch {
            observeConsumerCartJob?.cancel()
            mutableConsumerCartState.value = cartProductInteractor.getConsumerCart().toState()
            if (mutableConsumerCartState.value is State.Success) {
                observeConsumerCartJob =
                    cartProductInteractor.observeConsumerCart().launchOnEach { consumerCart ->
                        mutableConsumerCartState.value = consumerCart.toState()
                    }
            }
        }
    }

    fun onMenuClicked() {
        router.navigate(ConsumerCartFragmentDirections.toMenuFragment())
    }

    fun onCreateOrderClicked() {
        viewModelScope.launch {
            if (userInteractor.isUserAuthorize()) {
                router.navigate(ConsumerCartFragmentDirections.toCreateOrderFragment())
            } else {
                router.navigate(ConsumerCartFragmentDirections.toLoginFragment(TO_CREATE_ORDER))
            }
        }
    }

    fun onProductClicked(cartProductItem: CartProductItem) {
        router.navigate(ConsumerCartFragmentDirections.toProductFragment(cartProductItem.menuProductUuid, cartProductItem.name))
    }

    fun onAddCardProductClicked(menuProductUuid: String) {
        viewModelScope.launch {
            addCartProductUseCase(menuProductUuid)
        }
    }

    fun onRemoveCardProductClicked(menuProductUuid: String) {
        viewModelScope.launch {
            removeCartProductUseCase(menuProductUuid)
        }
    }

    private fun ConsumerCart?.toState(): State<ConsumerCartUI> {
        return if (this == null) {
            State.Error(resourcesProvider.getString(R.string.error_consumer_cart_loading))
        } else {
            when (this) {
                is ConsumerCart.Empty -> State.Empty()
                is ConsumerCart.WithProducts -> ConsumerCartUI(
                    forFreeDelivery = stringUtil.getCostString(forFreeDelivery),
                    cartProductList = cartProductList.map(::toItem),
                    oldTotalCost = oldTotalCost?.let { oldTotalCost ->
                        stringUtil.getCostString(oldTotalCost)
                    },
                    newTotalCost = stringUtil.getCostString(newTotalCost),
                ).toState()
            }
        }
    }

    private fun toItem(lightCartProduct: LightCartProduct): CartProductItem {
        return CartProductItem(
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
