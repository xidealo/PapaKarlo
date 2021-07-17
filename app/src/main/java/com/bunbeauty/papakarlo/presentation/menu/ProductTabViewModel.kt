package com.bunbeauty.papakarlo.presentation.menu

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.State
import com.bunbeauty.common.extensions.toStateSuccess
import com.bunbeauty.domain.enums.ProductCode
import com.bunbeauty.domain.model.local.MenuProduct
import com.bunbeauty.presentation.view_model.base.adapter.MenuProductItem
import com.bunbeauty.domain.repo.CartProductRepo
import com.bunbeauty.domain.util.product.IProductHelper
import com.bunbeauty.domain.util.string_helper.IStringUtil
import com.bunbeauty.papakarlo.presentation.base.CartViewModel
import com.bunbeauty.papakarlo.ui.MenuFragmentDirections.toProductFragment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

abstract class ProductTabViewModel(
    cartProductRepo: CartProductRepo,
    stringUtil: IStringUtil,
    productHelper: IProductHelper,
) : CartViewModel(cartProductRepo, stringUtil, productHelper) {

    abstract val productListState: StateFlow<State<List<MenuProductItem>>>

    abstract fun getMenuProductList(productCode: ProductCode)
    abstract fun onProductClicked(menuProductItem: MenuProductItem)
}

class ProductTabViewModelImpl @Inject constructor(
    cartProductRepo: CartProductRepo,
    stringUtil: IStringUtil,
    productHelper: IProductHelper,
) : ProductTabViewModel(cartProductRepo, stringUtil, productHelper) {

    override val productListState: MutableStateFlow<State<List<MenuProductItem>>> =
        MutableStateFlow(State.Loading())

    override fun getMenuProductList(productCode: ProductCode) {
        menuProductRepo.getMenuProductListAsFlow().onEach { menuProductList ->
            if (menuProductList.isEmpty()) {
                productListState.value = State.Loading()
            } else {
                if (productCode == ProductCode.ALL) {
                    productListState.value =
                        menuProductList.map(::toItemModel).toStateSuccess()
                } else {
                    val filteredMenuProductList = menuProductList.filter { menuProduct ->
                        menuProduct.productCode == productCode.name
                    }
                    if (filteredMenuProductList.isEmpty()) {
                        productListState.value = State.Empty()
                    } else {
                        productListState.value =
                            filteredMenuProductList.map(::toItemModel).toStateSuccess()
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    override fun onProductClicked(menuProductItem: MenuProductItem) {
        router.navigate(
            toProductFragment(
                menuProductItem.uuid,
                menuProductItem.name,
                menuProductItem.photo.get()
            )
        )
    }

    private fun toItemModel(menuProduct: MenuProduct): MenuProductItem {
        return MenuProductItem(
            uuid = menuProduct.uuid,
            name = menuProduct.name,
            cost = productHelper.getMenuProductOldPriceString(menuProduct),
            discountCost = productHelper.getMenuProductPriceString(menuProduct),
            photoLink = menuProduct.photoLink
        )
    }
}