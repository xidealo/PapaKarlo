package com.bunbeauty.papakarlo.presentation.menu

import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.State
import com.bunbeauty.common.extensions.toStateSuccess
import com.bunbeauty.domain.enums.ProductCode
import com.bunbeauty.domain.model.local.MenuProduct
import com.bunbeauty.domain.repo.CartProductRepo
import com.bunbeauty.domain.repo.MenuProductRepo
import com.bunbeauty.domain.util.product.IProductHelper
import com.bunbeauty.domain.util.string_helper.IStringUtil
import com.bunbeauty.papakarlo.presentation.base.CartViewModel
import com.bunbeauty.papakarlo.ui.MenuFragmentDirections.toProductFragment
import com.bunbeauty.presentation.view_model.base.adapter.MenuProductItem
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ProductTabViewModel @Inject constructor(
    cartProductRepo: CartProductRepo,
    stringUtil: IStringUtil,
    productHelper: IProductHelper,
    private val menuProductRepo: MenuProductRepo
) :  CartViewModel(cartProductRepo, stringUtil, productHelper) {

    private val mutableProductListState: MutableStateFlow<State<List<MenuProductItem>>> = MutableStateFlow(State.Loading())
    val productListState: StateFlow<State<List<MenuProductItem>>>
        get() = mutableProductListState.asStateFlow()

    fun getMenuProductList(productCode: ProductCode) {
        menuProductRepo.getMenuProductListAsFlow().onEach { menuProductList ->
            if (menuProductList.isEmpty()) {
                mutableProductListState.value = State.Loading()
            } else {
                if (productCode == ProductCode.ALL) {
                    mutableProductListState.value =
                        menuProductList.map {
                            toItemModel(it)
                        }.toStateSuccess()
                } else {
                    val filteredMenuProductList = menuProductList.filter { menuProduct ->
                        menuProduct.productCode == productCode.name
                    }
                    if (filteredMenuProductList.isEmpty()) {
                        mutableProductListState.value = State.Empty()
                    } else {
                        mutableProductListState.value =
                            filteredMenuProductList.map { toItemModel(it) }.toStateSuccess()
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onProductClicked(menuProductItem: MenuProductItem) {
        router.navigate(
            toProductFragment(
                menuProductItem.uuid,
                menuProductItem.name,
                menuProductItem.photoNotWeak.get()?.toBitmap()
            )
        )
    }

    fun addProductToCart(menuProductUuid: String) {
        addProductToCart(menuProductUuid, menuProductRepo)
    }

    private fun toItemModel(menuProduct: MenuProduct): MenuProductItem {
        return MenuProductItem(
            uuid = menuProduct.uuid,
            name = menuProduct.name,
            cost = productHelper.getMenuProductOldPriceString(menuProduct),
            discountCost = productHelper.getMenuProductPriceString(menuProduct),
            photoLink = menuProduct.photoLink,
        )
    }
}