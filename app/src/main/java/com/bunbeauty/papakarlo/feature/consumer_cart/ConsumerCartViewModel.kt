package com.bunbeauty.papakarlo.feature.consumer_cart

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.interactor.cart.ICartProductInteractor
import com.bunbeauty.domain.interactor.user.IUserInteractor
import com.bunbeauty.domain.model.cart.ConsumerCart
import com.bunbeauty.domain.model.cart.LightCartProduct
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.state.StateWithError
import com.bunbeauty.papakarlo.common.view_model.CartViewModel
import com.bunbeauty.papakarlo.enums.SuccessLoginDirection.TO_CREATE_ORDER
import com.bunbeauty.papakarlo.extensions.toStateWithErrorSuccess
import com.bunbeauty.papakarlo.feature.consumer_cart.ConsumerCartFragmentDirections.*
import com.bunbeauty.papakarlo.util.resources.IResourcesProvider
import com.bunbeauty.papakarlo.util.string.IStringUtil
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ConsumerCartViewModel(
    private val stringUtil: IStringUtil,
    private val userInteractor: IUserInteractor,
    private val cartProductInteractor: ICartProductInteractor,
    private val resourcesProvider: IResourcesProvider,
) : CartViewModel() {

    private val mutableConsumerCartState: MutableStateFlow<StateWithError<ConsumerCartUI>> =
        MutableStateFlow(StateWithError.Loading())
    val consumerCartState: StateFlow<StateWithError<ConsumerCartUI>> =
        mutableConsumerCartState.asStateFlow()

    private var observeConsumerCartJob: Job? = null

    fun getConsumerCart() {
        viewModelScope.launch {
            observeConsumerCartJob?.cancel()
            mutableConsumerCartState.value = cartProductInteractor.getConsumerCart().toState()
            if (mutableConsumerCartState.value is StateWithError.Success) {
                observeConsumerCartJob =
                    cartProductInteractor.observeConsumerCart().launchOnEach { consumerCart ->
                        mutableConsumerCartState.value = consumerCart.toState()
                    }
            }
        }
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
        router.navigate(toProductFragment(cartProductItem.menuProductUuid, cartProductItem.name))
    }

    private fun ConsumerCart?.toState(): StateWithError<ConsumerCartUI> {
        return if (this == null) {
            StateWithError.Error(resourcesProvider.getString(R.string.error_consumer_cart_loading))
        } else {
            when (this) {
                is ConsumerCart.Empty -> StateWithError.Empty()
                is ConsumerCart.WithProducts -> ConsumerCartUI(
                    forFreeDelivery = stringUtil.getCostString(forFreeDelivery),
                    cartProductList = cartProductList.map(::toItem),
                    oldTotalCost = oldTotalCost?.let { oldTotalCost ->
                        stringUtil.getCostString(oldTotalCost)
                    },
                    newTotalCost = stringUtil.getCostString(newTotalCost),
                ).toStateWithErrorSuccess()
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