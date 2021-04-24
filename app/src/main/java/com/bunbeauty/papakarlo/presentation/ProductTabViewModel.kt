package com.bunbeauty.papakarlo.presentation

import androidx.lifecycle.viewModelScope
import com.bunbeauty.data.enums.ProductCode
import com.bunbeauty.data.model.MenuProduct
import com.bunbeauty.domain.repository.menu_product.MenuProductRepo
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.ui.MenuFragmentDirections.toProductFragment
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ProductTabViewModel @Inject constructor(private val menuProductRepo: MenuProductRepo) : BaseViewModel() {

    private val _productListSharedFlow = MutableSharedFlow<List<MenuProduct>>()
    val productListSharedFlow: SharedFlow<List<MenuProduct>> = _productListSharedFlow.asSharedFlow()

    fun getMenuProductList(productCode: ProductCode) {
        menuProductRepo.getMenuProductListAsFlow(productCode)
            .onEach { menuProductList ->
                _productListSharedFlow.emit(menuProductList)
            }
            .launchIn(viewModelScope)
    }

    fun onProductClicked(menuProduct: MenuProduct) {
        router.navigate(toProductFragment(menuProduct, menuProduct.name))
    }
}