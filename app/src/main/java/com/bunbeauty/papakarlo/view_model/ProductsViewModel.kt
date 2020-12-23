package com.bunbeauty.papakarlo.view_model

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.bunbeauty.papakarlo.data.model.MenuProduct
import com.bunbeauty.papakarlo.enums.ProductCode
import com.bunbeauty.papakarlo.ui.products.ProductsNavigator
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import kotlinx.coroutines.delay
import java.lang.ref.WeakReference
import javax.inject.Inject

class ProductsViewModel @Inject constructor() : BaseViewModel<ProductsNavigator>() {

    override var navigator: WeakReference<ProductsNavigator>? = null

    val isLoading = ObservableField(true)
    private val productList = arrayListOf(
        MenuProduct(
            name = "Пицца маргарита",
            productCode = ProductCode.Pizza,
            cost = 10,
            description = "Много сыра не бывает",
            gram = 210,
            photoLink = "https://www.delonghi.com/Global/recipes/multifry/3.jpg"
        ),
        MenuProduct(
            name = "Пицца бекон",
            productCode = ProductCode.Pizza,
            cost = 23,
            photoLink = "https://papakarlokimry.ru/images/products/pizza/saliamy.jpg"
        ),
        MenuProduct(
            name = "Hamburger",
            productCode = ProductCode.Hamburger,
            cost = 11,
            photoLink = "https://mcdonalds.ru/resize/-x1020/upload/iblock/f90/0000_BigMac_BB_1500x1500_min.png"
        ),
        MenuProduct(
            name = "Hamburger 2",
            productCode = ProductCode.Hamburger,
            cost = 65,
            photoLink = "https://mcdonalds.ru/resize/-x1020/upload/iblock/b0b/CHizburger-_1_.png"
        ),
    )

    lateinit var productCode: ProductCode

    val productListLiveData: LiveData<List<MenuProduct>> by lazy {
        Transformations.map(MutableLiveData(productList)) { productList ->
            isLoading.set(false)
            if (productCode == ProductCode.All) {
                productList
            } else {
                productList.filter { it.productCode == productCode }
            }
        }
    }

    fun goToProduct(menuProduct: MenuProduct) {
        navigator?.get()?.goToProduct(menuProduct)
    }
}