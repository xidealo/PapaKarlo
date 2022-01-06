package com.bunbeauty.papakarlo.presentation.cart

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.interactor.user.IUserInteractor
import com.bunbeauty.domain.model.product.CartProduct
import com.bunbeauty.domain.repo.Api
import com.bunbeauty.domain.repo.CartProductRepo
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.util.product.IProductHelper
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.presentation.base.CartViewModel
import com.bunbeauty.papakarlo.presentation.state.State
import com.bunbeauty.papakarlo.presentation.state.toStateSuccess
import com.bunbeauty.papakarlo.ui.fragment.cart.ConsumerCartFragmentDirections.*
import com.bunbeauty.presentation.enums.SuccessLoginDirection.TO_CREATE_ORDER
import com.bunbeauty.presentation.item.CartProductItem
import com.bunbeauty.presentation.util.resources.IResourcesProvider
import com.bunbeauty.presentation.util.string.IStringUtil
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class ConsumerCartViewModel @Inject constructor(
    @Api private val cartProductRepo: CartProductRepo,
    private val resourcesProvider: IResourcesProvider,
    private val dataStoreRepo: DataStoreRepo,
    private val stringUtil: IStringUtil,
    private val productHelper: IProductHelper,
    private val userInteractor: IUserInteractor,
) : CartViewModel() {

    private val mutableOrderProductListState: MutableStateFlow<State<List<CartProductItem>>> =
        MutableStateFlow(State.Loading())
    val orderProductListState: StateFlow<State<List<CartProductItem>>> =
        mutableOrderProductListState.asStateFlow()

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
        cartProductRepo.observeCartProductList().onEach { productList ->
            mutableOrderProductListState.value = if (productList.isEmpty()) {
                State.Empty()
            } else {
                productList.map(::toItem).toStateSuccess()
            }
            val oldTotalCost = productHelper.getOldTotalCost(productList)
            mutableOldTotalCost.value = stringUtil.getCostString(oldTotalCost)
            val newTotalCost = productHelper.getNewTotalCost(productList)
            mutableNewTotalCost.value = stringUtil.getCostString(newTotalCost)
        }.launchIn(viewModelScope)
    }

    private fun observeDelivery() {
        dataStoreRepo.delivery.onEach { delivery ->
            mutableDeliveryInfo.value =
                resourcesProvider.getString(R.string.msg_consumer_cart_free_delivery_from) +
                        stringUtil.getCostString(delivery.forFree)
        }.launchIn(viewModelScope)
    }

    private fun toItem(cartProduct: CartProduct): CartProductItem {
        val newCost = productHelper.getProductPositionNewCost(cartProduct)
        val oldCost = productHelper.getProductPositionOldCost(cartProduct)
        val newCostString = stringUtil.getCostString(newCost)
        val oldCostString = stringUtil.getCostString(oldCost)

        return CartProductItem(
            uuid = cartProduct.uuid,
            name = cartProduct.product.name,
            newCost = newCostString,
            oldCost = oldCostString,
            photoLink = cartProduct.product.photoLink,
            count = cartProduct.count,
            menuProductUuid = cartProduct.product.uuid
        )
    }
}