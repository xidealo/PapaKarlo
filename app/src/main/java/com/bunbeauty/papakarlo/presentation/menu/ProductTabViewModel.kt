package com.bunbeauty.papakarlo.presentation.menu

import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.State
import com.bunbeauty.common.extensions.toStateSuccess
import com.bunbeauty.domain.enums.ProductCode
import com.bunbeauty.domain.model.ui.product.MenuProduct
import com.bunbeauty.domain.repo.MenuProductRepo
import com.bunbeauty.domain.util.product.IProductHelper
import com.bunbeauty.papakarlo.presentation.base.CartViewModel
import com.bunbeauty.papakarlo.ui.MenuFragmentDirections.toProductFragment
import com.bunbeauty.presentation.util.string.IStringUtil
import com.bunbeauty.presentation.view_model.base.adapter.MenuProductItem
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ProductTabViewModel @Inject constructor(
    private val stringUtil: IStringUtil,
    private val productHelper: IProductHelper,
    private val menuProductRepo: MenuProductRepo
) : CartViewModel() {

    private val mutableProductListState: MutableStateFlow<State<List<MenuProductItem>>> =
        MutableStateFlow(State.Loading())
    val productListState: StateFlow<State<List<MenuProductItem>>> =
        mutableProductListState.asStateFlow()

    fun getMenuProductList(productCode: ProductCode) {
        menuProductRepo.observeMenuProductList().onEach { menuProductList ->
            if (menuProductList.isEmpty()) {
                mutableProductListState.value = State.Loading()
            } else {
                if (productCode == ProductCode.ALL) {
                    mutableProductListState.value = menuProductList.map { menuProduct ->
                        toItemModel(menuProduct)
                    }.toStateSuccess()
                } else {
                    val filteredMenuProductList = menuProductList.filter { menuProduct ->
                        menuProduct.productCode == productCode.name
                    }
                    if (filteredMenuProductList.isEmpty()) {
                        mutableProductListState.value = State.Empty()
                    } else {
                        mutableProductListState.value = filteredMenuProductList.map { menuProduct ->
                            toItemModel(menuProduct)
                        }.toStateSuccess()
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

    private fun toItemModel(menuProduct: MenuProduct): MenuProductItem {
        val oldPrice = productHelper.getMenuProductOldPrice(menuProduct)
        val newPrice = productHelper.getMenuProductNewPrice(menuProduct)
        return MenuProductItem(
            uuid = menuProduct.uuid,
            name = menuProduct.name,
            cost = stringUtil.getCostString(oldPrice),
            discountCost = stringUtil.getCostString(newPrice),
            photoLink = menuProduct.photoLink,
        )
    }
}