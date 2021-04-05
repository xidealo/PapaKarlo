package com.bunbeauty.papakarlo.presentation

import androidx.lifecycle.viewModelScope
import com.bunbeauty.data.enums.ProductCode
import com.bunbeauty.data.model.MenuProduct
import com.bunbeauty.domain.repository.menu_product.MenuProductRepo
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.ui.MenuFragmentDirections.toProductFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductsViewModel @Inject constructor(private val menuProductRepo: MenuProductRepo) :
    BaseViewModel() {

    lateinit var productCode: ProductCode

    val productListSharedFlow = MutableSharedFlow<List<MenuProduct>>()

    fun getProducts() {
        viewModelScope.launch {
            menuProductRepo.getMenuProductList()
                .map { list ->
                    list.sortedBy { it.name }.filter { it.visible }
                }.collect { menuProductList ->
                    if (menuProductList.isNotEmpty()) {
                        if (productCode == ProductCode.ALL)
                            productListSharedFlow.emit(menuProductList)
                        else
                            productListSharedFlow.emit(menuProductList.filter { it.productCode == productCode.name })
                    }
                }
        }
    }

    fun onProductClicked(menuProduct: MenuProduct) {
        router.navigate(toProductFragment(menuProduct, menuProduct.name))
    }
}