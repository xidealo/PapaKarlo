package com.bunbeauty.papakarlo.common.view_model

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.interactor.cart.ICartProductInteractor
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.util.resources.IResourcesProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Base class for each viewModel which has top bar cart or can add product to cart
 */
open class CartViewModel : BaseViewModel() {

    @Inject
    lateinit var baseCartProductInteractor: ICartProductInteractor

    @Inject
    lateinit var baseResourcesProvider: IResourcesProvider

    fun addProductToCart(menuProductUuid: String) {
        viewModelScope.launch {
            val cartProduct = baseCartProductInteractor.addProductToCart(menuProductUuid)
            if (cartProduct == null) {
                showError(baseResourcesProvider.getString(R.string.error_consumer_cart_full), true)
            }
        }
    }

    fun removeProductFromCart(menuProductUuid: String) {
        viewModelScope.launch {
            baseCartProductInteractor.removeProductFromCart(menuProductUuid)
        }
    }
}