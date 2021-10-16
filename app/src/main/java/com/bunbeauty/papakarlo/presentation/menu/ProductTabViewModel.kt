package com.bunbeauty.papakarlo.presentation.menu

import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.State
import com.bunbeauty.common.extensions.toStateSuccess
import com.bunbeauty.domain.enums.ProductCode
import com.bunbeauty.domain.model.product.MenuProduct
import com.bunbeauty.domain.repo.MenuProductRepo
import com.bunbeauty.domain.util.product.IProductHelper
import com.bunbeauty.papakarlo.presentation.base.CartViewModel
import com.bunbeauty.papakarlo.ui.fragment.menu.MenuFragmentDirections.toProductFragment
import com.bunbeauty.presentation.item.MenuProductItem
import com.bunbeauty.presentation.util.string.IStringUtil
import com.example.data_api.Api
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ProductTabViewModel @Inject constructor(
    @Api private val menuProductRepo: MenuProductRepo,
    private val stringUtil: IStringUtil,
    private val productHelper: IProductHelper,
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
                        menuProduct.toItem()
                    }.toStateSuccess()
                } else {
                    val filteredMenuProductList = menuProductList.filter { menuProduct ->
                        menuProduct.productCode == productCode.name
                    }
                    if (filteredMenuProductList.isEmpty()) {
                        mutableProductListState.value = State.Empty()
                    } else {
                        mutableProductListState.value = filteredMenuProductList.map { menuProduct ->
                            menuProduct.toItem()
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

    private fun MenuProduct.toItem(): MenuProductItem {
        val oldPrice = discountCost?.let {
            productHelper.getProductOldPrice(this)
        }
        val newPrice = productHelper.getProductNewPrice(this)
        return MenuProductItem(
            uuid = uuid,
            name = name,
            newPrice = stringUtil.getCostString(newPrice),
            oldPrice = stringUtil.getCostString(oldPrice),
            photoLink = photoLink,
        )
    }
}