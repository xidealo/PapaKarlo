package com.bunbeauty.papakarlo.presentation

import androidx.lifecycle.viewModelScope
import com.bunbeauty.data.enums.ProductCode
import com.bunbeauty.data.model.MenuProduct
import com.bunbeauty.domain.repository.menu_product.MenuProductRepo
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.ui.MenuFragmentDirections.toProductFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductsViewModel @Inject constructor(private val menuProductRepo: MenuProductRepo) :
    BaseViewModel() {

    lateinit var productCode: ProductCode
    private val productListStateFlow = MutableStateFlow<List<MenuProduct>?>(null)

    fun getProducts(): MutableStateFlow<List<MenuProduct>?> {
        if (productListStateFlow.value != null) return productListStateFlow

        viewModelScope.launch(Dispatchers.IO) {
            menuProductRepo.getMenuProductList()
                .map { list ->
                    list.sortedBy { it.name }.filter { it.visible }
                }.collect { menuProductList ->
                    if (menuProductList.isNotEmpty()) {
                        if (productCode == ProductCode.ALL)
                            productListStateFlow.emit(menuProductList)
                        else
                            productListStateFlow.emit(menuProductList.filter { it.productCode == productCode.name })
                    }
                }
        }
        return productListStateFlow
    }

    fun onProductClicked(menuProduct: MenuProduct) {
        router.navigate(toProductFragment(menuProduct, menuProduct.name))
    }
}