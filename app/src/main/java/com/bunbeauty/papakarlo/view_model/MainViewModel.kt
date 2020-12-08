package com.bunbeauty.papakarlo.view_model

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.bunbeauty.papakarlo.data.local.db.CartDao
import com.bunbeauty.papakarlo.data.model.CartProduct
import com.bunbeauty.papakarlo.data.model.MenuProduct
import com.bunbeauty.papakarlo.enums.ProductCode
import com.bunbeauty.papakarlo.ui.main.MainNavigator
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MainViewModel @Inject constructor(private val cartDao: CartDao) : BaseViewModel(),
    CoroutineScope {

    override val coroutineContext: CoroutineContext = Job()

    lateinit var mainNavigator: WeakReference<MainNavigator>

    val cartProductListLiveData by lazy {
        cartDao.getCart()
    }
    val cartLiveData by lazy {
        Transformations.map(cartProductListLiveData) { productList ->
            "${productList.sumBy { it.getFullPrice() }} ₽\n${productList.sumBy { it.count }} шт."
        }
    }
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
        MenuProduct(name = "Hamburger 2", productCode = ProductCode.Hamburger, cost = 65),
    )
    val productsLiveData: LiveData<ArrayList<MenuProduct>> by lazy {
        MutableLiveData(productList)
    }

    var cartProductList = mutableSetOf<CartProduct>()

    fun addCartProduct(menuProduct: MenuProduct) {
        val cartProduct = cartProductListLiveData.value?.find { cartProduct ->
            cartProduct.menuProduct == menuProduct
        }
        if (cartProduct == null) {
            launch(Dispatchers.IO) {
                cartDao.insert(CartProduct(menuProduct = menuProduct))
            }
        } else {
            cartProduct.count++
            launch(Dispatchers.IO) {
                cartDao.update(cartProduct)
            }
        }
    }

    fun goToConsumerCartClick() {
        mainNavigator.get()?.goToConsumerCart(cartProductList)
    }
}